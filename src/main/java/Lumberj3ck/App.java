package Lumberj3ck;

import java.time.LocalDate;
import java.util.ArrayList;


public class App {
    public static void main(String[] args) {
        MarketDataProvider provider = new MarketDataProvider();

        provider.getDataFromMarket("AAPL");
        LocalDate startingDate = LocalDate.of(2024, 10, 1);
        ArrayList<Integer> cp = provider.getClosingPrices(startingDate, "1D");
        provider.calculateSMA(cp);
        provider.closeClient();
    }
}