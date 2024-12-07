package Lumberj3ck;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private HashMap<String, Object> getUserInfo() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", api_key)
                .addHeader("APCA-API-SECRET-KEY", api_secret_key)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, Object> responseData = mapper.readValue(response.body().string(), HashMap.class);
                // Line below prints out whole information about the user like a BIG ASS line
                // System.out.println(responseData);

                return responseData;
            } else {
                System.out.println("HTTP Error: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public void getBalance() throws IOException {
        HashMap<String, Object> responseBody = this.getUserInfo();
        Object nonMarginableBuyingPower = responseBody.get("non_marginable_buying_power");
        Object cash = responseBody.get("cash");
        Object equity = responseBody.get("equity");
        System.out.println(
                "User's current non marginable buying power: " + responseBody.get("currency") + " "
                        + nonMarginableBuyingPower);
        System.out.println("User's current balance: " + responseBody.get("currency") + " " + cash);
        System.out.println("User's current equity: " + responseBody.get("currency") + " " + equity);
    }
}