package Lumberj3ck;

public class Strategy1 extends Strategy{
   
    Strategy(){

    } 

    public boolen ShouldEnterMarket(int[] ind){
        return true;
    }
    
    public boolen ShouldExitMarket(int[] ind){
        return true;
    }

    public void buy(){
        // buy on trading view
    }

    public void sell(){

    }
}
