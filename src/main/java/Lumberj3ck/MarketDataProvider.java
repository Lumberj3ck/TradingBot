package Lumberj3ck;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

public class MarketDataProvider {
    private AlpacaKeysManager manager;
    private static final Logger logger = LogManager.getRootLogger();

    MarketDataProvider() {
        this.manager = new AlpacaKeysManager();
    }

    public void getDataFromMarket(String symbol) {
        String requestUrl = String.format(
                "https://data.alpaca.markets/v2/stocks/trades?symbols=%s&start=2024-09-01&limit=1000&feed=sip&sort=asc",
                symbol);
        logger.debug("Making new request to", requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        try {
            Response response = this.manager.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);

            logger.debug("Next page token {}", Jobject.optString("next_page_token"));
            logger.info("Retrieved market info for {}", symbol);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public ArrayList<Double> getClosingPrices(String symbol, LocalDate start, String timeframe) {
        String requestUrl = String.format(
                "https://data.alpaca.markets/v2/stocks/bars?symbols=%s&timeframe=%s&start=%s&adjustment=raw&feed=sip&sort=asc",
                symbol,
                timeframe, start);
        logger.debug("Making new request to", requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();
        try {
            String next_page_token;
            ArrayList<Double> closing_prices = new ArrayList<>();
            do {
                Response response = this.manager.client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);

                next_page_token = Jobject.optString("next_page_token");
                closing_prices.addAll(buildData(Jobject, symbol));

                String newRequestUrl = requestUrl + "&page_token=" + next_page_token;
                request = request.newBuilder()
                        .url(newRequestUrl)
                        .build();
            } while (next_page_token.length() > 0);

            logger.info("Retrieved closing prices for {} from {} aggregated by {}", symbol, start, timeframe);

            return closing_prices;
        } catch (Exception e) {
            logger.error(e);
        }
        return new ArrayList<>();
    }

    public ArrayList<String> getAssetsList() {

        Request request = new Request.Builder()
                .url("https://paper-api.alpaca.markets/v2/assets?status=active&exchange=NASDAQ&attributes=")
                .get()
                .build();

        try {
            Response response = this.manager.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray assets = new JSONArray(jsonData);
            ArrayList<String> tradableAssets = new ArrayList<>();

            for (int i = 0; i < assets.length(); i++) {
                JSONObject asset = assets.getJSONObject(i);
                if (asset.getBoolean("tradable")) {
                    tradableAssets.add(asset.getString("symbol"));
                }
            }
            logger.info("Getting assets list");
            logger.debug(tradableAssets);
            return tradableAssets;
        } catch (Exception e) {
            logger.error(e);
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves starting date for a amount of days specified
     */
    public LocalDate getStartingDateForDays(int amount) {
        LocalDate now = LocalDate.now();
        String url = String.format("https://paper-api.alpaca.markets/v2/calendar?start=2016-12-01&end=%s", now, amount);
        logger.debug("Making new request to", url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = this.manager.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray Jobject = new JSONArray(jsonData);

            int index = Math.max(0, Jobject.length() - amount - 1);

            if (Jobject.length() - amount - 1 < 0) {
                throw new IllegalArgumentException(
                        "Requested amount of " + amount + " days exceeds available market data from starting date");
            }

            JSONObject dateObj = Jobject.getJSONObject(index);
            LocalDate date = LocalDate.parse(dateObj.getString("date"));

            logger.info("Getting starting date for amount of {} periods", amount);
            return date;
        } catch (Exception e) {
            logger.error(e);
        }
        return now;
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

    public Map<String, Object> isMarketOpen() {
        Request request = new Request.Builder()
                .url("https://paper-api.alpaca.markets/v2/clock")
                .get()
                .build();
        Map<String, Object> result = new HashMap<>();

        try {
            Response response = this.manager.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            boolean isOpen = Jobject.getBoolean("is_open");

            String startTime = Jobject.getString("timestamp");
            String endTime = Jobject.getString("next_open");

            OffsetDateTime start = OffsetDateTime.parse(startTime);
            OffsetDateTime end = OffsetDateTime.parse(endTime);

            Duration duration = Duration.between(start, end);

            result.put("is_open", isOpen);
            result.put("leftToNext", duration);
            logger.info("Retriving data if market open");
            logger.debug(jsonData);
            return result;
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public void closeClient() {
        this.manager.client.connectionPool().evictAll();
        this.manager.client.dispatcher().executorService().shutdown();
    }
}
