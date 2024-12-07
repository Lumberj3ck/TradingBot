package Lumberj3ck;

abstract class TradeExecutor extends UserDataProvider {

    // public TradeExecutor() {

    // }

    // protected abstract boolean isEnoughFunds();

    abstract public void buy(String symbol, String amount);

    abstract public void sell(String symbol, String amount);

}
