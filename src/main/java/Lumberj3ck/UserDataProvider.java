package Lumberj3ck;

import java.io.IOException;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

public class UserDataProvider {
    private AlpacaKeysManager manager;
    private final String url;

    UserDataProvider() {
        this.manager = new AlpacaKeysManager();

        this.url = "https://paper-api.alpaca.markets/v2/account";
    }

    private JSONObject getUserInfo() {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = this.manager.client.newCall(request).execute();
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