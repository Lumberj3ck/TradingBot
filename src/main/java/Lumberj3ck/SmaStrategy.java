package Lumberj3ck;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public int amountOfWeekends(int days){
        int month = days / 30;
        return month * 8;
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        int long_period = 200;
        int weekends = amountOfWeekends(long_period);
        LocalDate startSma200 = LocalDate.now().minus(long_period + weekends, ChronoUnit.DAYS);
        ArrayList<Double> bars = market_data_provider.getClosingPrices("AAPL",startSma200, "1D");
        System.out.println(SimpleMovingAverageIndicator.calculate(bars));
        // Or get SMA for a specific index
        // System.out.println("SMA at index 0: " + shortSma.getValue(0));

        // System.out.print(shortSma);
        return false;
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
