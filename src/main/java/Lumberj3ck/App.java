package Lumberj3ck;


public class App {
    public static void main(String[] args) {
        // MarketDataProvider provider = new MarketDataProvider();
        // ArrayList<String> s = provider.getAssetsList();
        // System.out.println(s);
        // provider.getStartingDateForDays(200);
        // provider.getDataFromMarket("AAPL");
        // LocalDate startingDate = LocalDate.of(2024, 10, 1);
        // ArrayList<Integer> cp = provider.getClosingPrices(startingDate, "1D");
        // System.out.println(cp);
        // // provider.calculateSMA(cp);
        // provider.closeClient();

        // TestStrategy s = new TestStrategy();
        TestExecutor e = new TestExecutor();
        Strategy s = new SmaStrategy();
        // AlpacaPaperExecutor e = new AlpacaPaperExecutor();
        // s.shouldEnterMarket("AAPL");
        Runner r = new Runner(); 
        r.run(s, e);

        // UserDataProvider us = new UserDataProvider();
        // us.getBalance();


    }
}