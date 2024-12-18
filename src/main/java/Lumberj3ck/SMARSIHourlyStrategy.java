package Lumberj3ck;

public class SMARSIHourlyStrategy extends Strategy {

    private HourlyRSIStrategy hourlyRSI = new HourlyRSIStrategy();
    private HourlySMAStrategy hourlySMA = new HourlySMAStrategy();

    @Override
    public boolean shouldEnterMarket(String symbol) {
        return hourlyRSI.shouldEnterMarket(symbol) || hourlySMA.shouldEnterMarket(symbol);
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        return hourlyRSI.shouldEnterMarket(symbol) && hourlySMA.shouldEnterMarket(symbol);
    }

}
