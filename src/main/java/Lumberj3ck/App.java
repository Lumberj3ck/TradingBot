package Lumberj3ck;

import java.time.LocalDate;


public class App{
    public static void main(String[] args) {
        MarketDataProvider provider = new MarketDataProvider();
        provider.getDataFromMarket("AAPL");
        LocalDate startingDate = LocalDate.of(2024, 12, 1);
        provider.getClosingPrices(startingDate, "1H");
//        provider.getSMA(); WRITE FUNCTION
        provider.closeClient();
    }
}
 