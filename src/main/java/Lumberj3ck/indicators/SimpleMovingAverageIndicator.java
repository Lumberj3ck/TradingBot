package Lumberj3ck.indicators;

import java.util.List;

public class SimpleMovingAverageIndicator {
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

        System.out.println(priceSum / prices.size());
        return priceSum / prices.size();
    }

}
