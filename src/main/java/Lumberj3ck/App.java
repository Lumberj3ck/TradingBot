package Lumberj3ck;



public class App{
    public static void main(String[] args) {
        MarketDataProvider provider = new MarketDataProvider();
        provider.getDataFromMarket("AAPL");
        provider.getClosingPrices(, 10);
        provider.closeClient();
    }
}