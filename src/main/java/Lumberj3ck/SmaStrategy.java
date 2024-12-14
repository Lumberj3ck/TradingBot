package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;

import Lumberj3ck.indicators.SimpleMovingAverageIndicator;

public class SmaStrategy extends Strategy {
    private MarketDataProvider market_data_provider;

    SmaStrategy() {
        this.market_data_provider = new MarketDataProvider();
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        int long_period = 200;
        LocalDate startSma200 = market_data_provider.getStartingDateForDays(long_period);
        ArrayList<Double> bars = market_data_provider.getClosingPrices(symbol, startSma200, "1D");
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

}
