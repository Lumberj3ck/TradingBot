package Lumberj3ck;

abstract public class Strategy {

    public void buy(String symbol, int amount){
        // buying logic
    }

    public void sell(String symbol){
        // selling logic 
    };


    public boolean isPositionOpen(String symbol){
        // checking logic
        return false;
    }

    abstract public boolean shouldEnterMarket(String symbol);

    abstract public boolean shouldExitMarket(String symbol);
}
