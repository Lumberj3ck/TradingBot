package Lumberj3ck;

import java.util.ArrayList;
import java.util.Arrays;

// import java.util.ArrayList;

// import java.util.Arrays;

public class Runner {

    public static void run(Strategy strategy, TradeExecutor executor) {
        ArrayList<String> symbols = new ArrayList<>(Arrays.asList("AAPL", "GOOGL",
                "MSFT"));

        while (true) {
            for (String symbol : symbols) {
                String amount = "1";

                System.out.println(symbol);
                if (!strategy.isPositionOpen(symbol) && strategy.shouldEnterMarket(symbol)) {
                    executor.buy(symbol, amount);
                } else if (strategy.isPositionOpen(symbol) && strategy.shouldExitMarket(symbol)) {
                    executor.sell(symbol, amount);
                } else {
                    System.out.println(String.format("We are already bought %s, but don't wanna sell !!", symbol) );
                }
            }

            // for now just sleep for hour
            int second = 1000;
            // int hour = second * 3600;
            try {
                Thread.sleep(second * 5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
