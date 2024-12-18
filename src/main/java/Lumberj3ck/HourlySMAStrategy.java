package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;

import Lumberj3ck.indicators.SimpleMovingAverageIndicator;

public class HourlySMAStrategy extends Strategy {
    private MarketDataProvider market_data_provider;

    public HourlySMAStrategy() {
        this.market_data_provider = new MarketDataProvider();
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        LocalDate startSma200 = market_data_provider.getStartingDateForDays(200);
        ArrayList<Double> bars = market_data_provider.getClosingPrices(symbol, startSma200, "1H");
        Double smaShort = SimpleMovingAverageIndicator.calculate(bars, 90);
        Double smaLong = SimpleMovingAverageIndicator.calculate(bars, 200);

        return smaShort < smaLong;
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        return !shouldEnterMarket(symbol);
    }
}
