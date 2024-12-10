package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
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

    public ArrayList<Double> getClosingPrices(String symbol, LocalDate start, String timeframe) {
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
            return buildData(Jobject, symbol);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    public ArrayList<Double> buildData(JSONObject jsonData, String symbol) {
        JSONObject bars = jsonData.getJSONObject("bars");
        JSONArray prices = bars.getJSONArray(symbol);
        ArrayList<Double> finalPrices = new ArrayList<>();
        for (Object priceObj : prices) {
            JSONObject price = (JSONObject) priceObj;
            finalPrices.add(price.getDouble("c"));
        }
        return finalPrices;
    }

    public void closeClient() {
        this.manager.client.connectionPool().evictAll();
        this.manager.client.dispatcher().executorService().shutdown();
    }
}
