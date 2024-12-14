package Lumberj3ck.indicators;

import java.util.ArrayList;
import java.util.List;

public class RelativeStrengthIndexIndicator {

    public static List<List<Double>> getPriceChanges(List<Double> prices, int index) {
        List<List<Double>> priceChanges = new ArrayList<List<Double>>();

        int startIndex = prices.size() - index;
        if (startIndex < 0) {
            startIndex = 0;
        }
        prices.subList(index, prices.size());

        List<Double> gains = new ArrayList<Double>();
        List<Double> losses = new ArrayList<Double>();

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                gains.add(prices.get(i + 1) - prices.get(i));
            } else if (prices.get(i) > prices.get(i + 1)) {
                losses.add(prices.get(i) - prices.get(i + 1));
            } else {
                continue;
            }
        }

        priceChanges.add(gains);
        priceChanges.add(losses);

        return priceChanges;
    }

    public static Double calculate(List<Double> prices, int index) {
        List<List<Double>> priceChanges = getPriceChanges(prices, index);
        List<Double> gains = priceChanges.get(0);
        List<Double> losses = priceChanges.get(1);

        Double avgGains = gains.stream().mapToDouble(Double::doubleValue).sum() / (index - 1);
        Double avgLosses = losses.stream().mapToDouble(Double::doubleValue).sum() / (index - 1);

        Double rs = avgGains / avgLosses;
        Double rsi = 100 - (100 / (1 + rs));

        return rsi;
    }
}
