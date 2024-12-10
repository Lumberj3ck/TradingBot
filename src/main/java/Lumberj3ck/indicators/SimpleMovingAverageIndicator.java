package Lumberj3ck.indicators;

import java.util.ArrayList;

public class SimpleMovingAverageIndicator {
    public static Integer calculate(ArrayList<Double> prices) {
        int priceSum = 0;
        for (double cp : prices) {
            priceSum += cp;
        }
        return priceSum / prices.size();
    }    
}
