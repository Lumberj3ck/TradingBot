package Lumberj3ck;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DecimalNum;

import okhttp3.Request;
import okhttp3.Response;

public class MarketDataProvider {
    private AlpacaKeysManager manager;

    MarketDataProvider() {
        this.manager = new AlpacaKeysManager();
    }

    public void getDataFromMarket(String symbol) {
        String requestUrl = String.format(
                "https://data.alpaca.markets/v2/stocks/trades?symbols=%s&start=2024-09-01&limit=1000&feed=sip&sort=asc",
                symbol);
        System.out.println(requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        try {
            Response response = this.manager.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);

            System.out.println(Jobject.optString("next_page_toke"));
            // System.out.println(Jobject.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Bar> getClosingPrices(String symbol, LocalDate start, String timeframe) {
        String requestUrl = String.format(
                "https://data.alpaca.markets/v2/stocks/bars?symbols=%s&timeframe=%s&start=%s&adjustment=raw&feed=sip&sort=asc",
                symbol,
                timeframe, start);
        System.out.println(requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();
        try {
            Response response = this.manager.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);

            return createTimeSeriesFromAlpacaResponse(Jobject, symbol);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    private ArrayList<Bar> createTimeSeriesFromAlpacaResponse(JSONObject jsonData, String symbol) {
        JSONObject stock_bars = jsonData.getJSONObject("bars");
        JSONArray bars = stock_bars.getJSONArray(symbol);
        ArrayList<Bar> seriesBars = new ArrayList<>();

        for (Object barObj : bars) {
            JSONObject bar = (JSONObject) barObj;
            Bar newBar = new BaseBar(Duration.ofDays(1),
                    ZonedDateTime.ofInstant(Instant.parse("2024-04-05T04:00:00Z"), ZoneId.systemDefault()),
                    // bar.getString("t"),
                    bar.getDouble("o"),
                    bar.getDouble("h"),
                    bar.getDouble("l"),
                    bar.getDouble("c"),
                    bar.getLong("v"));
            seriesBars.add(newBar);
        }

        return seriesBars;
    }

    public ArrayList<Integer> buildData(JSONObject jsonData, String symbol) {
        JSONObject bars = jsonData.getJSONObject("bars");
        JSONArray prices = bars.getJSONArray(symbol);
        ArrayList<Integer> finalPrices = new ArrayList<>();
        for (Object priceObj : prices) {
            JSONObject price = (JSONObject) priceObj;
            finalPrices.add(price.getInt("c"));
        }
        return finalPrices;
    }

    public void closeClient() {
        this.manager.client.connectionPool().evictAll();
        this.manager.client.dispatcher().executorService().shutdown();
    }
}
