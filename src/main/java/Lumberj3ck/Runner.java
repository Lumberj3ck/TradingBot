package Lumberj3ck;

import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

    public static void run(Strategy strategy){
        ArrayList<String> symbols = new ArrayList<>(Arrays.asList("AAPL", "GOOGL", "MSFT"));

        while (true){
            for (String symbol : symbols){
                if (!strategy.isPositionOpen(symbol) && strategy.shouldEnterMarket(symbol)){
                    // for now hardcoded
                    int amount = 5;
                    strategy.buy(symbol, amount);
                } else if (strategy.isPositionOpen(symbol) && strategy.shouldExitMarket(symbol)){
                    strategy.sell(symbol);
                }
            }

            //  for now just sleep for hour
            int second = 1000;
            int hour = second * 3600;
            try {
                Thread.sleep(second);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
