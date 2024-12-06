package Lumberj3ck;

public class TestExecutor extends AlpacaPaperExecutor {
    @Override
    public void buy(String symbol, String amount) {
        super.buy(symbol, amount);
        System.out.println(String.format("Heyyy you just bought %s of amount %s",
                symbol, amount));
    }

    @Override
    public void sell(String symbol, String amount) {
        super.sell(symbol, amount);
        System.out.println(String.format("Heyyy you just sold %s of amount %s",
                symbol, amount));
    }
}
