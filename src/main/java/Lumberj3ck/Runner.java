package Lumberj3ck;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class Runner {
    public MarketDataProvider mp; 

    Runner(){
        this.mp = new MarketDataProvider();
    }

    private void stockCheckCycle(Strategy strategy, TradeExecutor executor) {
        // ArrayList<String> symbols = new ArrayList<>(Arrays.asList("AAPL", "GOOGL",
        //         "MSFT"));

        List<String> symbols = this.mp.getAssetsList().subList(0, 50);

        for (String symbol : symbols) {
            String amount = "1";

            System.out.println(symbol);
            if (!executor.isPositionOpen(symbol) && strategy.shouldEnterMarket(symbol)) {
                executor.buy(symbol, amount);
            } else if (executor.isPositionOpen(symbol) && strategy.shouldExitMarket(symbol)) {
                executor.sell(symbol, amount);
            } else {
                System.out.println(String.format("We are already bought %s, but don't wanna sell  !!", symbol));
                System.out.println(LocalDateTime.now());
            }
        }
    }

    public void run(Strategy strategy, TradeExecutor executor) {
        while (true) {
            Map<String, Object> result = mp.isMarketOpen();
            Boolean isOpen = (Boolean) result.get("is_open");
            Duration duration = (Duration) result.get("leftToNext");

            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            String openClosed = isOpen ? "open" : "closed";

            int second = 1000;
            // int hour = second * 3600;
            long sleep_time = second * 5;

            System.out.println("Market is" + openClosed);
            if (isOpen) {
                stockCheckCycle(strategy, executor);
            } else {
                System.out.println("Market will be open in " + duration.toDays() + " days " + hours + " hours and "
                        + minutes + " minutes.");
                sleep_time = duration.toMillis();
            }

            try {
                Thread.sleep(sleep_time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
