package SprinklerSystem;

import java.io.Serializable;

public class WaterConsumptionRecord implements Serializable {
	private int waterConsumption;
	private String date;
	WaterConsumptionRecord(String date, int waterConsumption){
		this.date = date;
		this.waterConsumption = waterConsumption;
	}
	
	public String getDate(){
		return date;
	}
	
	public int getWaterConsumption(){
		return waterConsumption;
	}
	
	public String toString(){
		return date+" "+String.valueOf(waterConsumption);
		
	}
}
