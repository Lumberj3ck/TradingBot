package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;

import Lumberj3ck.indicators.RelativeStrengthIndexIndicator;

public class HourlyRSIStrategy extends Strategy {
    private MarketDataProvider mdp;

    public HourlyRSIStrategy() {
        this.mdp = new MarketDataProvider();
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        LocalDate startRSI200 = mdp.getStartingDateForDays(200);
        ArrayList<Double> bars = mdp.getClosingPrices(symbol, startRSI200, "1H");
        Double rsi = RelativeStrengthIndexIndicator.calculate(bars);

        System.out.println(rsi);

        return rsi > 30 && rsi < 50;
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        return !shouldEnterMarket(symbol);
    }
}
