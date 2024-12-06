package Lumberj3ck;

import java.util.HashMap;
import java.util.Map;

public class TestStrategy extends Strategy {
    // Cyled buying and selling for test

    private Map<String, Boolean> openPositions = new HashMap<>();

    @Override
    public boolean shouldEnterMarket(String symbol) {
        openPositions.put(symbol, true);
        return true;
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        openPositions.put(symbol, false);
        return true;
    }

    @Override
    public boolean isPositionOpen(String symbol) {
        // for test
        if (openPositions.containsKey(symbol)) {
            return openPositions.get(symbol);
        } else {
            return false;
        }
    }
}
