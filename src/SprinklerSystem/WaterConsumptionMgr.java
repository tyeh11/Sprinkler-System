package SprinklerSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TimerTask;

public class WaterConsumptionMgr extends Observable{
	int totalWaterConsumption;
	int totaldateWaterConsumption;
	ArrayList<WaterConsumptionRecord> waterConsumptionRecordArrayList;
	
	SprinklerMgr sprinklerMgr;
	
	WaterConsumptionMgr(SprinklerMgr sprinklerMgr){
		totalWaterConsumption = 0;
		totaldateWaterConsumption = 0;
		this.sprinklerMgr = sprinklerMgr;
		waterConsumptionRecordArrayList = new ArrayList<WaterConsumptionRecord>();
		deSerializeWaterConsumptionRecord();
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("10/30", 50));
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/01", 42));
////		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/02", 58));
////		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/03", 60));
////		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/11", 30));
////		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/12", 35));
////		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/13", 36));
////		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/14", 28));
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/16", 30));
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/17", 35));
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/18", 36));
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/19", 28));
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/20", 30));
//		waterConsumptionRecordArrayList.add(new WaterConsumptionRecord("11/21", 35));

	}
	
	public void addToWaterConsumptionRecordArrayList(WaterConsumptionRecord wcr){
		waterConsumptionRecordArrayList.add(wcr);
		totalWaterConsumption += wcr.getWaterConsumption();
		totaldateWaterConsumption = 0;
	}
	public int getTotalWaterConsumption(){
		return totalWaterConsumption;
	}
	
	
	public void initializeWaterConsumptionSystem(){
		for (Location location: Location.values()){
			ArrayList<Sprinkler> sprinklers = sprinklerMgr.getSprinklerGroupMap().get(location.getLocation());
			for (int i=0; i<sprinklers.size(); i++){
				Sprinkler s = sprinklers.get(i);
				s.setDateWaterConsumption(0);
			}
		}
		totaldateWaterConsumption = 0;
	}
	
	public void collectDateWaterConsumption(){
		for (Location location: Location.values()){
			ArrayList<Sprinkler> sprinklers = sprinklerMgr.getSprinklerGroupMap().get(location.getLocation());
			for (int i=0; i<sprinklers.size(); i++){
				Sprinkler s = sprinklers.get(i);
				totaldateWaterConsumption += s.getDateWaterConsumption();
//				totalWaterConsumption += totaldateWaterConsumption;
				s.setDateWaterConsumption(0);
			}
		}
		System.out.println("total water consumption!!!!!!!!!!!!!!!!!");
		System.out.println(totaldateWaterConsumption);
	}
	
	public void dumpTotalDateWaterConsumption(String dateString){
		WaterConsumptionRecord wcr = new WaterConsumptionRecord(dateString, totaldateWaterConsumption);
		addToWaterConsumptionRecordArrayList(wcr);
		System.out.println(wcr);
		String filePath = "waterConsumptionData\\"+wcr.getDate()+".ser";
		serializeWaterConsumptionRecord(wcr, filePath);
		// notify
		setChanged();
		notifyObservers(wcr);
	}
	
	public void serializeWaterConsumptionRecord(WaterConsumptionRecord wcr, String fileName){
		try{
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(wcr);
			oos.flush();
			oos.close();
			}catch (Exception e){}
	}
	
	public void deSerializeWaterConsumptionRecord(){
		try {
			final File folder = new File("waterConsumptionData");
		    for (final File fileEntry : folder.listFiles()) {
		    	String fileName = fileEntry.getName();
//		    	if (fileName.split("-")[0].equals("11")){
					FileInputStream fis = new FileInputStream("waterConsumptionData\\"+fileEntry.getName());
					ObjectInputStream ois = new ObjectInputStream(fis);
					WaterConsumptionRecord wcr = (WaterConsumptionRecord) ois.readObject();
					System.out.println(wcr.getDate());
					addToWaterConsumptionRecordArrayList(wcr);
					ois.close();
//		    	}
		    }
			}catch (Exception e){}
	}
	
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

	
		
	public String showTotaldateWaterConsumption(){
		return String.valueOf(totaldateWaterConsumption);
	}
	
	public ArrayList<WaterConsumptionRecord> getWaterConsumptionRecordArrayList(){
		return waterConsumptionRecordArrayList;
	}
}
