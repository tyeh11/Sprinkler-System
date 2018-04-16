package SprinklerSystem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import scehdule.ScheduleManager;

public class SystemClock extends Observable{
	private Timer timer;
	private Long currentTime;
	private int speedRate;
	private int period;
	private int step;
	private static Calendar calendar  = Calendar.getInstance();
	private static Date date = new Date();
	private SimpleDateFormat simpleDateFormat, simpleMinuteFormat, simpleYearFormat;
	private String currentDateString, updatedCurrentDateString;
	private ScheduleManager scheduleManager;
	private static boolean collectWaterConsumption;
	WaterConsumptionMgr wcMgr;
	
	public SystemClock(int period, int speedRate, WaterConsumptionMgr waterConsumptionMgr) {
		currentTime = new Date().getTime();
//		currentTime = 1478655720000L;
		this.period = period;
		this.step = period*speedRate;
		scheduleManager = null;
		simpleDateFormat = new SimpleDateFormat("MM-dd");
		simpleMinuteFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
//		simpleYearFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
		simpleYearFormat = new SimpleDateFormat("MM/dd/yyyy");
		currentDateString = getDateString(currentTime);
		
		collectWaterConsumption = false;
		wcMgr = waterConsumptionMgr;
	}
	
	public void startTimer(){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				currentTime += step;
				scheduleManager.setSprinklers();
				setChanged();
//				notifyObservers();
				notifyObservers(getMinuteSring(currentTime)+"!"+getDayOfWeekStr(currentTime));
				updatedCurrentDateString = getDateString(currentTime);
//				System.out.println(getSecondSring(currentTime));
				if (!currentDateString.equals(updatedCurrentDateString) && collectWaterConsumption){
					wcMgr.collectDateWaterConsumption();
					System.out.print("consumption is : ");
					wcMgr.dumpTotalDateWaterConsumption(currentDateString);
					currentDateString = updatedCurrentDateString;
					System.out.println();
				}
				
			}
		}, 0, period);
	}
	
	public void setCurrentTime(String timeStr){
		try {
			System.out.println(timeStr);
//		    simpleYearFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		    Date d = (Date)simpleYearFormat.parse(timeStr);

		    long milliseconds = d.getTime();
		    System.out.println(milliseconds);
		    currentTime = milliseconds;
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	}
	
	public void setCollectWaterConsumptionStatus(boolean status){
		if (status == true){
			currentDateString = updatedCurrentDateString;
			wcMgr.initializeWaterConsumptionSystem();
		}
		collectWaterConsumption = status;
	}
	
	public Long getDate(){
		return currentTime;
	}
	
	public String getDateString(long timeInMillis){
		return simpleDateFormat.format(timeInMillis);
	}
	
	public String getMinuteSring(long timeInMillis){
		return simpleMinuteFormat.format(timeInMillis);
	}
	
	public static int getDayOfWeek(Long currentTime){
		date.setTime(currentTime);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	public String getDayOfWeekStr(Long currentTime){
		int dayOfWeek = getDayOfWeek(currentTime);
		if (dayOfWeek == 0) dayOfWeek = 7;
		DayOfWeek dow = DayOfWeek.values()[dayOfWeek-1];
		return dow.toString();
	}
	
	public static int getHour(Long currentTime){
		date.setTime(currentTime);
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public void setScheduleManager(ScheduleManager scheduleManager){
		this.scheduleManager = scheduleManager;
	}
	
}
