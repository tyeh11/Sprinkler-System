package SprinklerSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class SprinklerMgr extends Observable{
	Map<String, ArrayList<Sprinkler>> sprinklerGroupMap;
	int numOfSprinklerInGroup = 4;
	
	SprinklerMgr(){
		sprinklerGroupMap = new HashMap<String, ArrayList<Sprinkler>>();
		for (Location location: Location.values()){
			ArrayList<Sprinkler> sprinklerList = new ArrayList<Sprinkler>();
			for (int i=0; i<numOfSprinklerInGroup; i++){
				Sprinkler sprinkler = new Sprinkler(location.getLocation(), i);
				sprinklerList.add(sprinkler);
			}
			sprinklerGroupMap.put(location.getLocation(), sprinklerList);
		}
	}
	
	public void addAllSprinklerObserver(Observer o){
		for (Location location: Location.values()){
			for ( Sprinkler sprinkler : sprinklerGroupMap.get(location.getLocation()))
				sprinkler.addObserver(o);
			}
	}
	
	public Map<String, ArrayList<Sprinkler>> getSprinklerGroupMap(){
		return sprinklerGroupMap;
	}
	
	public void setSprinklerOnStatus(boolean status, String location, int index){
		sprinklerGroupMap.get(location).get(index).setOnStatus(status);
//		// notify observers to change single sprinkler picture
//		setChanged();
//		notifyObservers(sprinklerGroupMap.get(location).get(index));
	}
	
	public void setSprinklerOnStatus(boolean status, String location){
		for ( Sprinkler sprinkler : sprinklerGroupMap.get(location))
				sprinkler.setOnStatus(status);
//		// notify observers to change group sprinkler picture
//		setChanged();
////		notifyObservers(location);
//		notifyObservers(sprinklerGroupMap.get(location));
	}
	
	public void setSprinklerFlowRate(int flowRate, String location, int index){
		sprinklerGroupMap.get(location).get(index).setWaterFlowRate(flowRate);
	}
	
	public void setSprinklerFlowRate(int flowRate, String location){
		for ( Sprinkler sprinkler : sprinklerGroupMap.get(location))
				sprinkler.setWaterFlowRate(flowRate);
	}
	
	public void setAllSprinklerFunctionStatus(boolean status){
		for (Location location: Location.values()){
			for ( Sprinkler sprinkler : sprinklerGroupMap.get(location.getLocation()))
				sprinkler.setFunctionStatus(status);
			}
	}
	
	// use for debug
	public void reportGroupInfo(){
		for (Location location: Location.values()){
			for (int i=0; i<numOfSprinklerInGroup; i++){
				Sprinkler s = sprinklerGroupMap.get(location.getLocation()).get(i);
				System.out.print(s.getID());
				System.out.println(s.getWaterFlowRate());
			}
		}
	}
}


enum Location {
	East("E"), West("W"), South("S"), North("N");
	private final String location;
	private Location(String location){
		this.location = location;
	}
	public String getLocation(){
		return location;
	}
}
