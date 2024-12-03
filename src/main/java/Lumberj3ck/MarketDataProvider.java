package Lumberj3ck;

import org.json.JSONObject;

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

            System.out.println(Jobject.getString("next_page_token"));
            // System.out.println(Jobject.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getClosingPrices(int start, String timeframe) {
        String requestUrl = String.format(
                "https://data.alpaca.markets/v2/stocks/bars?symbols=AAPL&timeframe=%s&start=2024-11-%s&limit=1000&adjustment=raw&feed=sip&sort=asc",
                timeframe, start);
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

            System.out.println(Jobject.getString("next_page_token"));
            // System.out.println(Jobject.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void closeClient() {
        this.client.connectionPool().evictAll();
        this.client.dispatcher().executorService().shutdown();

    }
}
