package Lumberj3ck;

import net.jacobpeterson.alpaca.openapi.trader.ApiException;

abstract public class Strategy {
    protected TradeExecutor executor;

    public Strategy(TradeExecutor executor) {
        this.executor = executor;
    }

    public void buy(String symbol, String amount) throws ApiException, InterruptedException {
        // selling logic
        // just calling executor
        this.executor.buy(symbol, amount);
    }

    public void sell(String symbol, String amount) {
        // selling logic
        // just calling executor
        this.executor.sell(symbol, amount);
    };

    public boolean isPositionOpen(String symbol) {
        // checking logic
        return false;
    }

    abstract public boolean shouldEnterMarket(String symbol);

    abstract public boolean shouldExitMarket(String symbol);
}
