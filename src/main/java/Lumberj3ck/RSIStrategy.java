package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;

import Lumberj3ck.indicators.RelativeStrengthIndexIndicator;

public class RSIStrategy extends Strategy {
    private MarketDataProvider mdp;

    RSIStrategy() {
        this.mdp = new MarketDataProvider();
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        LocalDate startRSI200 = mdp.getStartingDateForDays(200);
        ArrayList<Double> bars = mdp.getClosingPrices(symbol, startRSI200, "1D");
        Double rsi = RelativeStrengthIndexIndicator.calculate(bars);

        return rsi > 30 && rsi < 50;
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        return !shouldEnterMarket(symbol);
    }
}
