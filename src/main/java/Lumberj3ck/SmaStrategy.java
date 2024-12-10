package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONObject;

import Lumberj3ck.indicators.SimpleMovingAverageIndicator;
import okhttp3.Request;
import okhttp3.Response;

public class SmaStrategy extends Strategy{
    private AlpacaKeysManager manager;
    private MarketDataProvider market_data_provider;

    SmaStrategy(){
        this.manager = new AlpacaKeysManager();
        this.market_data_provider  = new MarketDataProvider();
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        int long_period = 200;
        LocalDate startSma200 = market_data_provider.getStartingDateForDays(long_period);
        ArrayList<Double> bars = market_data_provider.getClosingPrices(symbol,startSma200, "1D");
        Double smaShort = SimpleMovingAverageIndicator.calculate(bars, 90);
        Double smaLong = SimpleMovingAverageIndicator.calculate(bars, 200);

        String logString = String.format("Short SMA: %s; Long SMA: %s", smaShort, smaLong);
        System.out.println(logString);
        return smaShort < smaLong;
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        return !shouldEnterMarket(symbol);
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
}
