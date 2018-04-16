package scehdule;

import java.util.ArrayList;
import java.util.HashMap;

public class TemperatureSchedule extends AbstractSchedule{
	private HashMap<String, ArrayList<Long>> limitMatch;
	private ArrayList<String> groupNames;
	
	public TemperatureSchedule(int level) {
		super(level);
		limitMatch = new HashMap<String, ArrayList<Long>>();
		groupNames = new ArrayList<String>();
	}
	
	@Override
	public void setSchedule(String groupName, long data1, long data2, boolean on){
		//data1 = upper limit, data2 = lower limit
		ArrayList<Long> temp = new ArrayList<Long>();
		temp.add(data1);
		temp.add(data2);
		groupNames.add(groupName);
		limitMatch.put(groupName, temp);

	}

	@Override
	public ArrayList<ArrayList<String>> getCurrentStatus(Long data) { // data is temperature
		ArrayList<String> groupTurnOn = new ArrayList<String>();
		ArrayList<String> groupTurnOff = new ArrayList<String>();
		
		for (int i = 0; i < groupNames.size(); i++) {
			if (data >= limitMatch.get(groupNames.get(i)).get(0)) { // to compare with upper limit
				groupTurnOn.add(groupNames.get(i));
			}
			else if (data <= limitMatch.get(groupNames.get(i)).get(1)) {// to compare with lower limit
				groupTurnOff.add(groupNames.get(i));
			}
		}
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		result.add(groupTurnOn);
		result.add(groupTurnOff);
		
		return result;
	}

	@Override
	public void clearSchedule() {
		// TODO Auto-generated method stub
		
	}
	
}
