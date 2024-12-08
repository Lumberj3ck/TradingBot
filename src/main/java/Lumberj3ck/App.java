package Lumberj3ck;
// import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // MarketDataProvider provider = new MarketDataProvider();

        // provider.getDataFromMarket("AAPL");
        // LocalDate startingDate = LocalDate.of(2024, 10, 1);
        // ArrayList<Integer> cp = provider.getClosingPrices(startingDate, "1D");
        // // provider.calculateSMA(cp);
        // provider.closeClient();

        // TestExecutor t = new TestExecutor();
        // TestStrategy s = new TestStrategy();

        // Runner.run(s, t);

        // Strategy s = new SmaStrategy();
        // s.isPositionOpen("NVDA");

        UserDataProvider us = new UserDataProvider();
        us.getBalance();
        // us.getBalance();

    }
}