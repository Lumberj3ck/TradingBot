package Lumberj3ck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlpacaPaperExecutor extends TradeExecutor {

    private final String url;
    private AlpacaKeysManager manager;
    private static final Logger logger = LogManager.getRootLogger();

    public AlpacaPaperExecutor() {
        this.manager = new AlpacaKeysManager();
        this.url = "https://paper-api.alpaca.markets/v2/orders";
    }

    @Override
    public boolean isPositionOpen(String symbol) {
        String request_url = String.format("https://paper-api.alpaca.markets/v2/positions/%s", symbol);

        logger.debug("Making request to {}", request_url);

        Request request = new Request.Builder()
                .url(request_url)
                .get()
                .build();

        try {
            Response response = this.manager.client.newCall(request).execute();
            String response_data = response.body().string();
            JSONObject jo = new JSONObject(response_data);
            String asset_id = jo.optString("asset_id");
            logger.info("Asset with folowing id {} ", asset_id);
            if (asset_id.length() > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("No assets for given symbol");
        return false;
    }

    // Complete isEnoughFunds function, implement stockPrice variable in the buy
    // function (stock price * amount to buy)
    @Override
    protected boolean isEnoughFunds(Double stockPrice) {
        UserDataProvider userInfo = new UserDataProvider();
        return userInfo.getNonMarginableBuyingPower() >= stockPrice;
    };

    public void buy(String symbol, String amount) {
        JSONObject json = new JSONObject();
        json.put("side", "buy");
        json.put("type", "market");
        json.put("symbol", symbol);
        json.put("qty", amount);
        json.put("time_in_force", "gtc");

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json")))
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = this.manager.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                logger.info("Buy order placed successfully for {} shares of {}", amount, symbol);
                logger.debug("Order response: {}", response.body().string());
            } else {
                logger.error("Error placing buy order: {} - {}", response.code(), response.body().string());
            }
        } catch (Exception e) {
            logger.error("Exception while placing buy order", e);
        }
    }

    public void sell(String symbol, String amount) {
        JSONObject json = new JSONObject();
        json.put("side", "sell");
        json.put("type", "trailing_stop");
        json.put("symbol", symbol);
        json.put("qty", amount);
        json.put("time_in_force", "gtc");
        json.put("trail_percent", "3");

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json")))
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = this.manager.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                logger.info("Sell order placed successfully for {} shares of {}", amount, symbol);
                logger.debug("Order response: {}", response.body().string());
            } else {
                logger.error("Error placing sell order: {} - {}", response.code(), response.body().string());
            }
        } catch (Exception e) {
            logger.error("Exception while placing sell order", e);
        }

    }

}
