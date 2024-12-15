package Lumberj3ck.indicators;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleMovingAverageIndicator {
    private static final Logger logger = LogManager.getRootLogger();

    public static Double calculate(List<Double> prices, int index) {
        Double priceSum = 0.0;
        int startIndex = prices.size() - index;
        if (startIndex < 0) {
            startIndex = 0;
        }
        List<Double> pricesStartingFrom = prices.subList(startIndex, prices.size());
        for (double cp : pricesStartingFrom) {
            priceSum += cp;
        }

        Double sma = priceSum / prices.size();
        logger.info("Calculated SMA: {}", sma);
        return sma;
    }

}
