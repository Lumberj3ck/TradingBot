package Lumberj3ck;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

public class SmaStrategy extends Strategy{
    private AlpacaKeysManager manager;

    SmaStrategy(){
        this.manager = new AlpacaKeysManager();
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPositionOpen(String symbol){
        String request_url = String.format("https://paper-api.alpaca.markets/v2/positions/%s", symbol);

        Request request = new Request.Builder()
        .url(request_url)
        .get()
        .build();

        try {
            Response response = this.manager.client.newCall(request).execute();
            String response_data = response.body().string();
            JSONObject jo = new JSONObject(response_data);
            String asset_id = jo.optString("asset_id");
            System.out.println(asset_id);
            if (asset_id.length() > 0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No assets for given symbol");
        return false;
    }

    public void specific() {
    }
}
