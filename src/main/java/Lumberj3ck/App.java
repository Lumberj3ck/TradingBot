package Lumberj3ck;


public class App {
    public static void main(String[] args) {
        // MarketDataProvider provider = new MarketDataProvider();
        // provider.getDataFromMarket("AAPL");
        // LocalDate startingDate = LocalDate.of(2024, 10, 1);
        // ArrayList<Integer> cp = provider.getClosingPrices(startingDate, "1D");
        // System.out.println(cp);
        // // provider.calculateSMA(cp);
        // provider.closeClient();

        // TestExecutor t = new TestExecutor();
        // TestStrategy s = new TestStrategy();

        // Runner.run(s, t);

        Strategy s = new SmaStrategy();
        s.shouldEnterMarket("AAPL");
        // s.isPositionOpen("AAPL");

        // UserDataProvider us = new UserDataProvider();
        // us.getBalance();

        // AlpacaPaperExecutor a = new AlpacaPaperExecutor();
        // a.buy("AAPL", "1");
    }
}