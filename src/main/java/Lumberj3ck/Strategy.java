package Lumberj3ck;

abstract public class Strategy {

    abstract public void buy();

    abstract public void sell();

    abstract public boolean shouldEnterMarket();

    abstract public boolean shouldExitMarket();
}
