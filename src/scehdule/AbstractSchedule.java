package scehdule;

import java.util.ArrayList;

public abstract class AbstractSchedule {
	private int level;
	public AbstractSchedule(int level) {
		this.level = level;
	}
	
	abstract public ArrayList<ArrayList<String>>getCurrentStatus(Long data);
	abstract public void setSchedule(String groupName, long data1, long data2, boolean on);
	abstract public void clearSchedule();
	/**
	 * This method returns schedule level.
	 * @return int
	 */
	public int getLevel(){
		return level;
	}
	
}