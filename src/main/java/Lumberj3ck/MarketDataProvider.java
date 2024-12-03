package Lumberj3ck;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarketDataProvider {
    private OkHttpClient client;
    private String api_key;
    private String api_secret_key;

    MarketDataProvider() {
        Dotenv dotenv = Dotenv.load();

        this.api_key = dotenv.get("api_key");
        this.api_secret_key = dotenv.get("api_secret_key");

        this.client = new OkHttpClient();
    }
    
    public void getDataFromMarket(String symbol) {
        String requestUrl = String.format(
                "https://data.alpaca.markets/v2/stocks/trades?symbols=%s&start=2024-09-01&limit=1000&feed=sip&sort=asc",
                symbol);
        System.out.println(requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", this.api_key)
                .addHeader("APCA-API-SECRET-KEY", this.api_secret_key)
                .build();

        try {
            Response response = this.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);

            System.out.println(Jobject.optString("next_page_toke"));
            // System.out.println(Jobject.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getClosingPrices(LocalDate start, String timeframe) {
        String requestUrl = String.format(
                "https://data.alpaca.markets/v2/stocks/bars?symbols=AAPL&timeframe=%s&start=%s&limit=1000&adjustment=raw&feed=sip&sort=asc",
                timeframe, start);
        System.out.println(requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", this.api_key)
                .addHeader("APCA-API-SECRET-KEY", this.api_secret_key)
                .build();
        try {
            Response response = this.client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);

            System.out.println(Jobject.optString("next_page_toke"));
            // symbol hardcoded for now
            System.out.println(buildData(Jobject, "AAPL"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Integer> buildData(JSONObject jsonData, String symbol){
        JSONObject bars = jsonData.getJSONObject("bars");
        JSONArray prices = bars.getJSONArray(symbol);
        ArrayList<Integer> finalPrices = new ArrayList<>();
        for (Object priceObj : prices) {
            JSONObject price = (JSONObject) priceObj;
            finalPrices.add(price.getInt("c"));
        }
        return finalPrices;
    }

    public static double calculateSMA(int[] closingPrices){
        int priceSum = 0;
        for (int cp : closingPrices){
            priceSum += cp;
        }
        return priceSum / closingPrices.length;
    }

    public void closeClient() {
        this.client.connectionPool().evictAll();
        this.client.dispatcher().executorService().shutdown();

    }
}