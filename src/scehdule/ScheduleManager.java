package scehdule;

import java.util.ArrayList;
import java.util.Arrays;

import SprinklerSystem.SprinklerMgr;
import SprinklerSystem.SystemClock;

public class ScheduleManager {
	private Schedule regularSchedule;
	private TemperatureSchedule temperatureSchedule;
	private SpecialSchedule specialSchedule;
	private AbstractSchedule[] schedules;
	private boolean[] levelSwitch;
	private SystemClock systemClock;
	private SprinklerMgr sprinklerMgr;
	private int simulateTemp;
	
	public ScheduleManager(SystemClock selfMakeClock, SprinklerMgr sprinklerMgr) {//initialize data member
		simulateTemp = 20;
		schedules = new AbstractSchedule[3];
		
		regularSchedule = new Schedule(1);
		schedules[0] = regularSchedule;
		
		temperatureSchedule = new TemperatureSchedule(2);
		schedules[1] = temperatureSchedule;
		
		specialSchedule = new SpecialSchedule(3);
		schedules[2] = specialSchedule;
		
		levelSwitch = new boolean[3];
		levelSwitch[0] = true;
		levelSwitch[1] = false;
		levelSwitch[2] = false;
		
		this.systemClock = selfMakeClock;
		this.sprinklerMgr = sprinklerMgr;
	}
	
	public void setSprinklers(){//calculate final result according schedule level
		ArrayList<String> gruopForTurnOn = new ArrayList<String>();
		ArrayList<String> gruopForTurnOff = new ArrayList<String>();
		long currentTime = systemClock.getDate();
		long data = currentTime;
		for (int i = 0; i < schedules.length; i++) {
			if(schedules[i] instanceof TemperatureSchedule) data = simulateTemp;
			else data = currentTime;
			if (levelSwitch[i]) {
				ArrayList<String> tempForTurnOn = schedules[i].getCurrentStatus(data).get(0);
				ArrayList<String> tempForTurnOff = schedules[i].getCurrentStatus(data).get(1);
				
				for (int j = 0; j < tempForTurnOff.size(); j++) {
					//System.out.println(tempForTurnOff.get(j) + "," + j);
					if (!gruopForTurnOff.contains(tempForTurnOff.get(j))) {
						gruopForTurnOff.add(tempForTurnOff.get(j));
					}
					gruopForTurnOn.remove(tempForTurnOff.get(j));
				}
				for (int j = 0; j < tempForTurnOn.size(); j++) {
					if (!gruopForTurnOn.contains(tempForTurnOn.get(j))) {
						gruopForTurnOn.add(tempForTurnOn.get(j));
					}
					gruopForTurnOff.remove(tempForTurnOn.get(j));
				}
			}
		}
//		System.out.println("size on " + gruopForTurnOn.size() );
//		System.out.println("size off " + gruopForTurnOn.size() );
		for (int i = 0; i < gruopForTurnOn.size(); i++) {
			sprinklerMgr.setSprinklerOnStatus(true, gruopForTurnOn.get(i));
		}
		for (int i = 0; i < gruopForTurnOff.size(); i++) {
			sprinklerMgr.setSprinklerOnStatus(false, gruopForTurnOff.get(i));
		}
	}
	
	public void updateSchedule(String groupName, Long data1, Long data2, int level, boolean on){
		schedules[level-1].setSchedule(groupName, data1, data2, on);
	}
	
	public void levelSwitch(int level, boolean on){
		levelSwitch[level - 1] = on;
		setSprinklers();
	}
	
	public void clearSchedule(int level){
		schedules[level-1].clearSchedule();
	}
	
	public void setSimulatTemp(int temp){
		simulateTemp = temp;
	}

}
