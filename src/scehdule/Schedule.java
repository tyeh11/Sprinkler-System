package scehdule;

import java.util.ArrayList;

import SprinklerSystem.SystemClock;

public class Schedule extends AbstractSchedule{
	private ArrayList<String>[][] groupForTurnOn;
	private ArrayList<String>[][] groupForTurnOff;
	
	public Schedule(int level) {
		super(level);
		groupForTurnOn = (ArrayList<String>[][])new ArrayList[7][4];
		groupForTurnOff = (ArrayList<String>[][])new ArrayList[7][4];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 4; j++) {
				groupForTurnOn[i][j] = new ArrayList<String>();
				groupForTurnOff[i][j] = new ArrayList<String>();
			}
		}

	}

	@Override
	public void setSchedule(String groupName, long data1, long data2, boolean on){
		//data1 == dayofweek, data2 = hour	
		int dayOfWeek = (int)data1;
		int hour = (int)data2;
		if (on) {
			groupForTurnOn[dayOfWeek][hour].add(groupName);
		}
		else groupForTurnOff[dayOfWeek][hour].add(groupName);
	}

	@Override
	public  ArrayList<ArrayList<String>> getCurrentStatus(Long data) {
		ArrayList<ArrayList<String>> currentStatus = new ArrayList<ArrayList<String>>();
		int hour = SystemClock.getHour(data) / 6;
		int dayOfWeek = SystemClock.getDayOfWeek(data);
//		System.out.println(dayOfWeek);
//		System.out.println(SelfMakeClock.getHour(data));
		currentStatus.add(groupForTurnOn[dayOfWeek][hour]);
		currentStatus.add(groupForTurnOff[dayOfWeek][hour]);
		return currentStatus;
	}
	@Override
	public void clearSchedule() {
		groupForTurnOn = (ArrayList<String>[][])new ArrayList[7][4];
		groupForTurnOff = (ArrayList<String>[][])new ArrayList[7][4];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 4; j++) {
				groupForTurnOn[i][j] = new ArrayList<String>();
				groupForTurnOff[i][j] = new ArrayList<String>();
			}
		}
	}
	

}
