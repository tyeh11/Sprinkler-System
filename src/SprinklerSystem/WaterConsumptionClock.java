package SprinklerSystem;

import java.util.Timer;
import java.util.TimerTask;

public class WaterConsumptionClock {
    Timer timer;
    Sprinkler sprinkler;
    int updatePeriodMillis = 1000;
    		
    public WaterConsumptionClock(Sprinkler sprinkler) {
        this.sprinkler = sprinkler;
        timer = null;
	}
    
    public void ClockStartTimer(){
    	timer = new Timer();
    	timer.schedule(new WaterConsumptionCalculator(sprinkler), updatePeriodMillis, updatePeriodMillis);
    }

    public void ClockCencelTimer(){
    	if (timer != null)
   	 		timer.cancel();
    }

//    public static void main(String args[]) {
//    	Clock c = new Clock(sprinkler);//5 second represent 1 hour 跳一次就是1小時
//        c.ClockStartTimer(2);
//        System.out.format("Task scheduled.%n");
//    }
}

class WaterConsumptionCalculator extends TimerTask {
	int totalWaterConsumption;
	Sprinkler sprinkler;
	
	WaterConsumptionCalculator(Sprinkler sprinkler){
		this.sprinkler = sprinkler;
	}
    public void run() {
//      System.out.format("water + + %n");
        sprinkler.addDateWaterConsumption();
    }
}