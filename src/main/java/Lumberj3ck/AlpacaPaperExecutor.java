package Lumberj3ck;

import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

abstract class AlpacaPaperExecutor extends TradeExecutor {

    private String api_key;
    private String api_secret_key;
    private String url;

    AlpacaPaperExecutor() {
        Dotenv de = Dotenv.load();

        this.api_key = de.get("api_key");
        this.api_secret_key = de.get("api_secret_key");
        this.url = "https://paper-api.alpaca.markets/v2/orders";
    }

    public void buy(String symbol, String amount) {
        // Create HTTP client
        OkHttpClient client = new OkHttpClient();

        // Create JSON payload
        JSONObject json = new JSONObject();
        json.put("side", "buy");
        json.put("type", "market");
        json.put("time_in_force", "gtc");
        json.put("symbol", symbol);
        json.put("qty", amount);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json")))
                .addHeader("APCA-API-KEY-ID", api_key) // Add API key
                .addHeader("APCA-API-SECRET-KEY", api_secret_key) // Add secret key
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Order placed successfully:");
                System.out.println(response.body().string());
            } else {
                System.err.println("Error placing order: " + response.code());
                System.err.println(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sell(String symbol, String amount) {

        OkHttpClient client = new OkHttpClient();

        // Create JSON payload
        JSONObject json = new JSONObject();
        json.put("side", "sell");
        json.put("type", "market");
        json.put("time_in_force", "gtc");
        json.put("symbol", symbol);
        json.put("qty", amount);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json")))
                .addHeader("APCA-API-KEY-ID", api_key) // Add API key
                .addHeader("APCA-API-SECRET-KEY", api_secret_key) // Add secret key
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Order placed successfully:");
                System.out.println(response.body().string());
            } else {
                System.err.println("Error placing order: " + response.code());
                System.err.println(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    };

}
