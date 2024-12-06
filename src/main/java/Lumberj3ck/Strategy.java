package Lumberj3ck;


abstract public class Strategy {
    // protected TradeExecutor executor;

    // public Strategy(TradeExecutor executor) {
    //     this.executor = executor;
    // }

    public boolean isPositionOpen(String symbol) {
        // checking logic
        return false;
    }

    abstract public boolean shouldEnterMarket(String symbol);

    abstract public boolean shouldExitMarket(String symbol);
}
