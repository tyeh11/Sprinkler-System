package SprinklerSystem;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import scehdule.ScheduleManager;

public class SprinklerSystem extends JFrame{
	SprinklerMgr sprinklerMgr;
	WaterConsumptionMgr waterConsumptionMgr;
	JPanel sprinklerStatusPanel;
	SystemClock systemClock;
	ScheduleManager	scheduleManager;
	
	SprinklerSystem(){
		super("SprinklerSystem");

		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(1,2));
		
		sprinklerMgr = new SprinklerMgr();
		waterConsumptionMgr = new WaterConsumptionMgr(sprinklerMgr);
		
		systemClock = new SystemClock(100, 60*60, waterConsumptionMgr);
		sprinklerStatusPanel = new SprinklerStatusPanel(sprinklerMgr, systemClock);
		
		sprinklerMgr.addAllSprinklerObserver((Observer) sprinklerStatusPanel);
//		sprinklerMgr.addObserver((Observer) sprinklerStatusPanel);

		scheduleManager = new ScheduleManager(systemClock, sprinklerMgr);
		systemClock.setScheduleManager(scheduleManager);
		
    	JTabbedPane tabbedPane = new JTabbedPane();
    	tabbedPane.addTab("Schedule Sprinklers", new SchedulePanel(scheduleManager));
    	tabbedPane.addTab("Control Sprinklers", new SprinklerControlPanel(scheduleManager, systemClock));
    	tabbedPane.addTab("Temperature Limit Setting", new TemperatureSettingPanel(scheduleManager));
    	tabbedPane.addTab("Water Flow Rate Setting", new WaterFlowRateSettingPanel(sprinklerMgr));
    	tabbedPane.addTab("Water Consumption", new WaterConsumptionPanel(waterConsumptionMgr));
//    	tabbedPane.addTab("Direct Control", new TestPanel(sprinklerMgr));
    	add(tabbedPane);
    	add(sprinklerStatusPanel);
    	
    	systemClock.addObserver((Observer) sprinklerStatusPanel);
		systemClock.startTimer();
    	
      	setSize( 1200, 600 );
      	setTitle("SprinklerSystem");
      	setLocationRelativeTo(null);
      	
	}
	
	/**
	 * Start the window
	 * @param args arguments
	 */
    public static void main( String args[] ){
    	SprinklerSystem sprinklerSystem = new SprinklerSystem();
    	sprinklerSystem.setDefaultCloseOperation( EXIT_ON_CLOSE );
    	sprinklerSystem.setVisible( true );
    }
}