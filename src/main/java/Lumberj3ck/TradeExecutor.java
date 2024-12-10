package Lumberj3ck;

abstract class TradeExecutor {

    protected abstract boolean isEnoughFunds(Double stockPrice);

    public boolean isPositionOpen(String symbol) {
        // checking logic
        return false;
    }

    abstract public void buy(String symbol, String amount);

    abstract public void sell(String symbol, String amount);

}
