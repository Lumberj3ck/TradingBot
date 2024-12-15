package Lumberj3ck;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestExecutor extends TradeExecutor {
    private Map<String, Boolean> openPositions = new HashMap<>();
    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public void buy(String symbol, String amount) {
        // super.buy(symbol, amount);
        logger.info("Buying {} shares of {}", amount, symbol);
        openPositions.put(symbol, true);
    }

    @Override
    public void sell(String symbol, String amount) {
        // super.sell(symbol, amount);
        logger.info("Selling {} shares of {}", amount, symbol);
        openPositions.put(symbol, false);
    }

    @Override
    protected boolean isEnoughFunds(Double stockPrice) {
        // TODO Auto-generated method stub
        logger.error("isEnoughFunds method not implemented");
        throw new UnsupportedOperationException("Unimplemented method 'isEnoughFunds'");
    }

    @Override
    public boolean isPositionOpen(String symbol) {
        // for test
        if (openPositions.containsKey(symbol)) {
            boolean isOpen = openPositions.get(symbol);
            logger.debug("Position for {} is {}", symbol, isOpen ? "open" : "closed");
            return isOpen;
        } else {
            logger.debug("No position found for {}", symbol);
            return false;
        }
    }
}
