package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Lumberj3ck.indicators.*;

public class App {
    public static void main(String[] args) {
        MarketDataProvider provider = new MarketDataProvider();
        // ArrayList<String> s = provider.getAssetsList();
        // System.out.println(s);
        // provider.getStartingDateForDays(200);
        // provider.getDataFromMarket("AAPL");
        LocalDate startingDate = LocalDate.of(2024, 10, 1);
        ArrayList<Double> cp = provider.getClosingPrices("NVDA", startingDate, "1D");
        // System.out.println(cp);
        // // provider.calculateSMA(cp);
        // provider.closeClient();

        // // TestStrategy s = new TestStrategy();
        // TestExecutor e = new TestExecutor();
        Strategy s = new SmaStrategy();
        // // AlpacaPaperExecutor e = new AlpacaPaperExecutor();
        s.shouldEnterMarket("AAPL");
        // Runner r = new Runner();
        // r.run(s, e);

        SimpleMovingAverageIndicator.calculate(cp, 10);
        SimpleMovingAverageIndicator.calculate(cp, 50);
        // RelativeStrengthIndexIndicator.calculate(cp, 14);

    }
}