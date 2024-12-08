package Lumberj3ck;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class AlpacaPaperExecutor extends TradeExecutor {

    private final String url;
    private AlpacaKeysManager manager;

    AlpacaPaperExecutor() {
        this.manager = new AlpacaKeysManager();
        this.url = "https://paper-api.alpaca.markets/v2/orders";
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
        json.put("time_in_force", "gtc");
        json.put("symbol", symbol);
        json.put("qty", amount);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json")))
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = this.manager.client.newCall(request).execute()) {
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
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = this.manager.client.newCall(request).execute()) {
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

}
