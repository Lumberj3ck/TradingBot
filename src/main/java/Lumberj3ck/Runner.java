package Lumberj3ck;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner {
    public MarketDataProvider mp;
    private static final Logger logger = LogManager.getRootLogger();

    public Runner() {
        this.mp = new MarketDataProvider();
        logger.debug("Initialized Runner with new MarketDataProvider");
    }

    private void stockCheckCycle(Strategy strategy, TradeExecutor executor) {
        List<String> symbols = this.mp.getAssetsList().subList(15, 20);
        logger.info("Starting stock check cycle for {} symbols", symbols.size());

        for (String symbol : symbols) {
            String amount = "1";

            logger.debug("Checking symbol: {}", symbol);
            if (!executor.isPositionOpen(symbol) && strategy.shouldEnterMarket(symbol)) {
                logger.info("Entering market for symbol: {}", symbol);
                executor.buy(symbol, amount);

                while (!executor.isPositionOpen(symbol)) {
                    logger.info("Waiting for position to open...");
                }
                executor.sell(symbol, amount);
                logger.info("Opening trailing stop order");

                // } else if (executor.isPositionOpen(symbol)) {
                // logger.info("Exiting market for symbol: {}", symbol);
                // executor.sell(symbol, amount);
                // } else {
                logger.debug("No action needed for symbol: {}. Position already open at {}", symbol,
                        LocalDateTime.now());
            }
        }
    }

    public void run(Strategy strategy, TradeExecutor executor) {
        logger.info("Starting trading runner");
        while (true) {
            Map<String, Object> result = mp.isMarketOpen();
            Boolean isOpen = (Boolean) result.get("is_open");
            Duration duration = (Duration) result.get("leftToNext");

            long hours = duration.toHoursPart();
            long minutes = duration.toMinutesPart();
            String openClosed = isOpen ? "open" : "closed";

            int second = 1000;
            long sleep_time = second * 5;

            logger.info("Market is {}", openClosed);
            if (isOpen) {
                logger.debug("Starting stock check cycle");
                stockCheckCycle(strategy, executor);
            } else {
                logger.info("Market will be open in {} days {} hours and {} minutes",
                        duration.toDaysPart(), hours, minutes);
                sleep_time = duration.toMillis();
            }

            try {
                logger.debug("Sleeping for {} milliseconds", sleep_time);
                Thread.sleep(sleep_time);
            } catch (InterruptedException e) {
                logger.error("Sleep interrupted", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
