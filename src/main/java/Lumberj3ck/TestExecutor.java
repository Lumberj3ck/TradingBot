package Lumberj3ck;

public class TestExecutor extends TradeExecutor {
    @Override
    public void buy(String symbol, int amount){
        System.out.println(String.format("Heyyy you just bought %s of amount %s", symbol, amount));
    }

    @Override
    public void sell(String symbol){
        System.out.println(String.format("Heyyy you just sold %s", symbol));
    }
}
