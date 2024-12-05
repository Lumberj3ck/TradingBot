package Lumberj3ck;

public class SmaStrategy extends Strategy {

    SmaStrategy(TradeExecutor executor){
        super(executor);
    }

    @Override
    public boolean shouldEnterMarket(String symbol) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void buy(String symbol, int amount){
        this.executor.buy(symbol, amount);
    }

    
    @Override
    public void sell(String symbol){
        this.executor.sell(symbol);
    };
}
