package Lumberj3ck;

public class App {
    public static void main(String[] args) {
        // --------- Testing Market Data Provider
        // MarketDataProvider provider = new MarketDataProvider();
        // provider.getDataFromMarket("AAPL");
        // provider.getAssetsList();

        // ArrayList<String> s = provider.getAssetsList();
        // provider.getStartingDateForDays(200);
        // provider.getDataFromMarket("AAPL");
        // LocalDate startingDate = LocalDate.of(2024, 10, 1);
        // ArrayList<Double> cp = provider.getClosingPrices("NVDA", startingDate, "1D");
        // // provider.calculateSMA(cp);
        // provider.closeClient();

        // SimpleMovingAverageIndicator.calculate(cp, 10);
        // SimpleMovingAverageIndicator.calculate(cp, 50);
        // RelativeStrengthIndexIndicator.calculate(cp, 14);

        // -------------- Testing Runner with Strategies ----
        SMARSIDailyStrategy s = new SMARSIDailyStrategy();
        TestExecutor e = new TestExecutor();
        // Strategy s = new SmaStrategy();
        // // AlpacaPaperExecutor e = new AlpacaPaperExecutor();
        // s.shouldEnterMarket("AAPL");
        Runner r = new Runner();
        r.run(s, e);
    }
}