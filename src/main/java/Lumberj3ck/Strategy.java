package Lumberj3ck;

abstract public class Strategy {

    abstract public boolean shouldEnterMarket(String symbol);

    abstract public boolean shouldExitMarket(String symbol);
}
