package Lumberj3ck.indicators;

import java.util.ArrayList;

public class SimpleMovingAverage {
    public static Integer calculate(ArrayList<Integer> prices) {
        int priceSum = 0;
        for (int cp : prices) {
            priceSum += cp;
        }
        return priceSum / prices.size();
    }    
}
