package scehdule;

import java.util.ArrayList;
import java.util.HashMap;

import SprinklerSystem.SystemClock;

public class SpecialSchedule extends AbstractSchedule{
	private HashMap<String, Long> groupForTurnOn;
	private HashMap<String, Long> groupForTurnOff;
	private Long toHour;

	public SpecialSchedule(int level) {
		super(level);
		groupForTurnOn = new HashMap<String, Long>();
		groupForTurnOff =  new HashMap<String, Long>();
		toHour = (long) (60*60*1000);
	}
	
	@Override
	public void setSchedule(String groupName, long data1, long data2, boolean on){
		//data1 = current time, data2 = hours
		if (on) {
			groupForTurnOn.put(groupName, data1 + data2 * toHour);
		}
		else{
			groupForTurnOff.put(groupName, data1 + data2 * toHour);
		}
	}
	
	@Override
	public ArrayList<ArrayList<String>> getCurrentStatus(Long data) { // data is hours
		ArrayList<String> groupTurnOn = new ArrayList<String>();
		ArrayList<String> groupTurnOff = new ArrayList<String>();
		
		String[] temp = new String[groupForTurnOn.size()];
		groupForTurnOn.keySet().toArray(temp);
		for (int i = 0; i < groupForTurnOn.size(); i++) {
			if (groupForTurnOn.get(temp[i]) > data) {
				groupTurnOn.add(temp[i]);
			}
		}
		
		temp = new String[groupForTurnOff.size()];
		groupForTurnOff.keySet().toArray(temp);
		for (int i = 0; i < groupForTurnOff.size(); i++) {
			if (groupForTurnOff.get(temp[i]) > data) {
				groupTurnOff.add(temp[i]);
			}
		}
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		result.add(groupTurnOn);
		result.add(groupTurnOff);
		
		return result;
	}

	@Override
	public void clearSchedule() {
		groupForTurnOn.clear();
		groupForTurnOff.clear();
		
	}
}
