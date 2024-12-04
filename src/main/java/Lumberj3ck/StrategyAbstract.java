package Lumberj3ck;

abstract class StrategyAbstract {
    Strategy(){

    } 

    public abstract ShouldEnterMarket(int[] ind){
    }
    
    public abstract ShouldExitMarket(int[] ind){
    }

    public void buy(){
        // buy on trading view
    }

    public void sell(){

    }
}
