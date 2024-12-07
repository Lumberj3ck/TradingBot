package Lumberj3ck;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UserDataProvider {

    private final String api_key;
    private final String api_secret_key;
    private final String url;

    UserDataProvider() {
        Dotenv de = Dotenv.load();

        this.api_key = de.get("api_key");
        this.api_secret_key = de.get("api_secret_key");
        this.url = "https://paper-api.alpaca.markets/v2/account";
    }

    private JSONObject getUserInfo() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("APCA-API-KEY-ID", api_key)
                .addHeader("APCA-API-SECRET-KEY", api_secret_key)
                .addHeader("accept", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                // Line below prints out whole information about the user like a BIG ASS line
                // System.out.println(response.body().string());

                return new JSONObject(response.body().string());
            } else {
                System.out.println("HTTP Error: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public Double getCash() {
        JSONObject responseBody = this.getUserInfo();
        return Double.parseDouble(responseBody.getString("cash"));
    }

    public Double getNonMarginableBuyingPower() {
        JSONObject responseBody = this.getUserInfo();
        return Double.parseDouble(responseBody.getString("non_marginable_buying_power"));
    }

    public void getBalance() {
        JSONObject responseBody = this.getUserInfo();
        String cash = responseBody.getString("cash");
        String nonMarginableBuyingPower = responseBody.getString("non_marginable_buying_power");
        String equity = responseBody.getString("equity");
        System.out.println(
                "User's current non marginable buying power: " + responseBody.get("currency")
                        + " "
                        + nonMarginableBuyingPower);
        System.out.println("User's current balance: " + responseBody.get("currency")
                + " " + cash);
        System.out.println("User's current equity: " + responseBody.get("currency") +
                " " + equity);
    }
}