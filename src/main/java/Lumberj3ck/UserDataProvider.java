package Lumberj3ck;

import java.io.IOException;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.Request;
import okhttp3.Response;

public class UserDataProvider {
    private AlpacaKeysManager manager;
    private final String url;
    private static final Logger logger = LogManager.getRootLogger();

    UserDataProvider() {
        this.manager = new AlpacaKeysManager();
        this.url = "https://paper-api.alpaca.markets/v2/account";
        logger.debug("UserDataProvider initialized with URL: {}", url);
    }

    private JSONObject getUserInfo() {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            logger.debug("Sending request to get user info");
            Response response = this.manager.client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                logger.debug("Successfully received user info response");
                return new JSONObject(response.body().string());
            } else {
                logger.error("HTTP Error: {} - {}", response.code(), response.message());
            }
        } catch (IOException e) {
            logger.error("Error getting user info: {}", e.getMessage());
        }
        return null;
    }

    public Double getCash() {
        JSONObject responseBody = this.getUserInfo();
        Double cash = Double.parseDouble(responseBody.getString("cash"));
        logger.info("Current cash balance: {}", cash);
        return cash;
    }

    public Double getNonMarginableBuyingPower() {
        JSONObject responseBody = this.getUserInfo();
        Double power = Double.parseDouble(responseBody.getString("non_marginable_buying_power"));
        logger.info("Current non-marginable buying power: {}", power);
        return power;
    }

    public void getBalance() {
        JSONObject responseBody = this.getUserInfo();
        String cash = responseBody.getString("cash");
        String nonMarginableBuyingPower = responseBody.getString("non_marginable_buying_power");
        String equity = responseBody.getString("equity");
        String currency = responseBody.getString("currency");
        
        logger.info("User's current non marginable buying power: {} {}", currency, nonMarginableBuyingPower);
        logger.info("User's current balance: {} {}", currency, cash);
        logger.info("User's current equity: {} {}", currency, equity);
    }
}