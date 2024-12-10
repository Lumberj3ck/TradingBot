package Lumberj3ck;

public class TestStrategy extends Strategy {
    // Cyled buying and selling for test


    @Override
    public boolean shouldEnterMarket(String symbol) {
        return true;
    }

    @Override
    public boolean shouldExitMarket(String symbol) {
        return true;
    }

}
