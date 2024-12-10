package Lumberj3ck;

import java.util.HashMap;
import java.util.Map;

public class TestExecutor extends TradeExecutor {
    private Map<String, Boolean> openPositions = new HashMap<>();

    @Override
    public void buy(String symbol, String amount) {
        // super.buy(symbol, amount);
        System.out.println(String.format("Heyyy you just bought %s of amount %s",
                symbol, amount));
        openPositions.put(symbol, true);
    }

    @Override
    public void sell(String symbol, String amount) {
        // super.sell(symbol, amount);
        System.out.println(String.format("Heyyy you just sold %s of amount %s",
                symbol, amount));
        openPositions.put(symbol, false);
    }

    @Override
    protected boolean isEnoughFunds(Double stockPrice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEnoughFunds'");
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
