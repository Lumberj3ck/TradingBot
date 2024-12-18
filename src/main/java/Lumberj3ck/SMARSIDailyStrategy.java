package Lumberj3ck;

public class SMARSIDailyStrategy extends Strategy {

    private DailyRSIStrategy dailyRSI = new DailyRSIStrategy();
    private DailySMAStrategy dailySMA = new DailySMAStrategy();

    @Override
    public boolean shouldEnterMarket(String symbol) {
        return dailyRSI.shouldEnterMarket(symbol) || dailySMA.shouldEnterMarket(symbol);
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        return dailyRSI.shouldExitMarket(symbol) && dailySMA.shouldExitMarket(symbol);
    }

}
