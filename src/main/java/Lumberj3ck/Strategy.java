package Lumberj3ck;

abstract public class Strategy {
    protected TradeExecutor executor;

    public Strategy(TradeExecutor executor) {
        this.executor = executor;
    }

    public void buy(String symbol, int amount){
        // selling logic 
        // just calling executor
        this.executor.buy(symbol, amount);
    }

    public void sell(String symbol){
        // selling logic 
        // just calling executor
        this.executor.sell(symbol);
    };


    public boolean isPositionOpen(String symbol){
        // checking logic
        return false;
    }

    abstract public boolean shouldEnterMarket(String symbol);

    abstract public boolean shouldExitMarket(String symbol);
}
