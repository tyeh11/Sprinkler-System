package SprinklerSystem;

import java.util.Observable;

public class Sprinkler extends Observable{
	String id;
	int index;
	String location;
	int waterFlowRate;
	boolean functionStatus;
	boolean onStatus;
	WaterConsumptionClock waterConsumptionClock;
	int dateWaterConsumption;
	
	public Sprinkler(String location, int index){
		this.id = location+String.valueOf(index);
		this.location = location;
		this.index = index;
		waterFlowRate = 1;
		functionStatus = false;
		onStatus = false;
		waterConsumptionClock = new WaterConsumptionClock(this);
		dateWaterConsumption = 0;
	}
	
	public String getLocation(){
		return location;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getID(){
		return id;
	}
	
	public int getWaterFlowRate(){
		return waterFlowRate;
	}
	
	public boolean getFunctionStatus(){
		return functionStatus;
	}
	
	public boolean getOnStatus(){
		return onStatus;
	}
	
	public void setWaterFlowRate(int flowRate){
		waterFlowRate = flowRate;
	}

	public void setFunctionStatus(boolean status){
		if (status == false){
			setOnStatus(false);
		}
		functionStatus = status;
	}

	public void setOnStatus(boolean status){
		if (functionStatus){
			//status from ON to ON or OFF to OFF
			if (status == onStatus){}
			//status from OFF to ON
			else if(status && !onStatus){ 
				waterConsumptionClock.ClockStartTimer();
				setChanged();
				notifyObservers(status);
			}
			//status from ON to OFF
			else {
				waterConsumptionClock.ClockCencelTimer();
				setChanged();
				notifyObservers(status);
			}
			onStatus = status;
		}
	}
	
	synchronized public void addDateWaterConsumption(){
		this.dateWaterConsumption = dateWaterConsumption + waterFlowRate;
	}
	
	public int getDateWaterConsumption(){
		return dateWaterConsumption;
	}
	
	synchronized public void setDateWaterConsumption(int dateWaterConsumption){
		this.dateWaterConsumption = dateWaterConsumption;
	}
}
