package Lumberj3ck.indicators;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RelativeStrengthIndexIndicator {
    private static final Logger logger = LogManager.getRootLogger();

    public static List<List<Double>> getPriceChanges(List<Double> prices) {
        List<List<Double>> priceChanges = new ArrayList<List<Double>>();

        int startIndex = prices.size() - 14;
        if (startIndex < 0) {
            startIndex = 0;
        }
        prices.subList(startIndex, prices.size());
        logger.debug("Calculating price changes from index {} to {}", startIndex, prices.size());

        List<Double> gains = new ArrayList<Double>();
        List<Double> losses = new ArrayList<Double>();

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                double gain = prices.get(i + 1) - prices.get(i);
                gains.add(gain);
                logger.debug("Price gain at index {}: {}", i, gain);
            } else if (prices.get(i) > prices.get(i + 1)) {
                double loss = prices.get(i) - prices.get(i + 1);
                losses.add(loss);
                logger.debug("Price loss at index {}: {}", i, loss);
            } else {
                logger.debug("No price change at index {}", i);
                continue;
            }
        }

        priceChanges.add(gains);
        priceChanges.add(losses);
        logger.debug("Found {} gains and {} losses", gains.size(), losses.size());

        return priceChanges;
    }

    public static Double calculate(ArrayList<Double> prices) {
        logger.info("Calculating RSI for {} prices", prices.size());
        List<List<Double>> priceChanges = getPriceChanges(prices);
        List<Double> gains = priceChanges.get(0);
        List<Double> losses = priceChanges.get(1);

        Double avgGains = gains.stream().mapToDouble(Double::doubleValue).sum() / 13;
        Double avgLosses = losses.stream().mapToDouble(Double::doubleValue).sum() / 13;
        logger.debug("Average gains: {}, Average losses: {}", avgGains, avgLosses);

        Double rs = avgGains / avgLosses;
        Double rsi = 100 - (100 / (1 + rs));
        logger.info("Calculated RSI: {}", rsi);

        return rsi;
    }
}
