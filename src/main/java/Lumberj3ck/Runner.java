package Lumberj3ck;

// import java.util.ArrayList;
// import java.util.Arrays;

import net.jacobpeterson.alpaca.openapi.trader.ApiException;

public class Runner {

    public static void run(TestStrategy strategy, TestExecutor executor)
            throws ApiException, InterruptedException {
        // ArrayList<String> symbols = new ArrayList<>(Arrays.asList("AAPL", "GOOGL",
        // "MSFT"));
        String symbol = "AAPL";

        while (true) {
            // for (String symbol : symbols) {
            // if (!strategy.isPositionOpen(symbol) && strategy.shouldEnterMarket(symbol)) {
            // // for now hardcoded
            // String amount = "1";
            // strategy.buy(symbol, amount);
            // } else if (strategy.isPositionOpen(symbol) &&
            // strategy.shouldExitMarket(symbol)) {
            // strategy.sell(symbol);
            // }
            // }
            String amount = "1";
            if (!strategy.isPositionOpen(symbol) && strategy.shouldEnterMarket(symbol)) {
                executor.buy(symbol, amount);
            } else if (strategy.isPositionOpen(symbol) && strategy.shouldEnterMarket(symbol)) {
                executor.sell(symbol, amount);
            }

            // for now just sleep for hour
            int second = 1000;
            int hour = second * 3600;
            try {
                Thread.sleep(second * 5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
