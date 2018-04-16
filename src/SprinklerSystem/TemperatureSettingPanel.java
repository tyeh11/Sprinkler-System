package SprinklerSystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import scehdule.ScheduleManager;

public class TemperatureSettingPanel extends JPanel{
	private ScheduleManager scheduleManager;
	private JTextField[] upperLimit;
	private JTextField[] lowerLimit;
	private JTextField simTemp;
	private String[] groupName;
	
	public TemperatureSettingPanel(ScheduleManager scheduleManager) {		
		groupName = new String[4];
		groupName[0] = "E";
		groupName[1] = "W";
		groupName[2] = "S";
		groupName[3] = "N";
		
		JLabel northL = new JLabel("Temperature setting is now DISABLED");
		
		this.scheduleManager = scheduleManager;
		Dimension sizeOfTextField = new Dimension(50, 30);
		upperLimit = new JTextField[Location.values().length];
		for (int i = 0; i < Location.values().length; i++) {
			upperLimit[i] = new JTextField();
			upperLimit[i].setPreferredSize(sizeOfTextField);
			upperLimit[i].setText("30");
		}
		
		lowerLimit = new JTextField[Location.values().length];
		for (int i = 0; i < Location.values().length; i++) {
			lowerLimit[i] = new JTextField();
			lowerLimit[i].setPreferredSize(sizeOfTextField);
			lowerLimit[i].setText("15");
		}
		
		JPanel limitSettingPanel = new JPanel();
		limitSettingPanel.setLayout(new GridLayout(2, 2));
		for (int i = 0; i < Location.values().length; i++) {
			TitledBorder exampleTitle = 
					new TitledBorder(LineBorder.createGrayLineBorder(),Location.values()[i].toString() + " Group");
			JPanel tempPanel = new JPanel();
			tempPanel.setBorder(exampleTitle);
			tempPanel.setLayout(new GridLayout(0, 1));
			JPanel tempPanel2 = new JPanel();
			tempPanel2.add(new JLabel("Upper Limit(Celsius):"));
			tempPanel2.add(upperLimit[i]);
			tempPanel.add(tempPanel2);
			
			JPanel tempPanel3 = new JPanel();
			tempPanel3.add(new JLabel("Lower Limit(Celsius):"));
			tempPanel3.add(lowerLimit[i]);
			tempPanel.add(tempPanel3);
			limitSettingPanel.add(tempPanel);
		}
		JPanel centerPanerl = new JPanel();
		centerPanerl.setLayout(new BorderLayout());
		centerPanerl.add(limitSettingPanel, BorderLayout.CENTER);
		JButton setLimt = new JButton("Update and Enable Temperature Setting");
		setLimt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				northL.setText("Temperature setting is now ENABLED");
				scheduleManager.clearSchedule(2);
				for (int i = 0; i < Location.values().length; i++) {
					int upper = Integer.parseInt(upperLimit[i].getText());
					int lower = Integer.parseInt(lowerLimit[i].getText());
					scheduleManager.levelSwitch(2, true);
					scheduleManager.updateSchedule(groupName[i],
							(long)upper, (long)lower, 2, true);
				}
				scheduleManager.levelSwitch(2, true);
			}
		});
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(1, 0));
		
		JButton stopSetting = new JButton("Disable Temperature Setting");
		stopSetting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				northL.setText("Temperature setting is now DISABLED");
				scheduleManager.levelSwitch(2, false);
			}
		});
		JPanel temp1 = new JPanel();
		temp1.setLayout(new GridLayout(1, 0));
		temp1.add(stopSetting);
		temp1.add(setLimt);
		centerPanerl.add(temp1, BorderLayout.SOUTH);
		
		TitledBorder titledBorder = 
				new TitledBorder(LineBorder.createGrayLineBorder(),"Simulation Setting");
		JPanel simulationPanel = new JPanel();
		simulationPanel.setBorder(titledBorder);
		simulationPanel.setLayout(new BorderLayout());
		simulationPanel.add(new JLabel("Simulation Temperature:"), BorderLayout.CENTER);
		JLabel simLabel = new JLabel("Simulated Temperature:");
		simTemp = new JTextField("20");
		simTemp.setPreferredSize(sizeOfTextField);
		JPanel temp2 = new JPanel();
		temp2.add(simLabel);
		temp2.add(simTemp);
		simulationPanel.add(temp2, BorderLayout.CENTER);
		
		JPanel setTempPanel = new JPanel();
		JButton setTemp = new JButton("Set Simulation Temperature");
		setTempPanel.add(setTemp);
		simulationPanel.add(setTempPanel, BorderLayout.SOUTH);
		setTemp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scheduleManager.setSimulatTemp(Integer.parseInt(simTemp.getText()));
				scheduleManager.setSprinklers();
			}
		});
		setLayout(new BorderLayout());
		
		JPanel morePanel = new JPanel(new BorderLayout());
		morePanel.add(northL, BorderLayout.NORTH);
		morePanel.add(centerPanerl, BorderLayout.CENTER);
		add(morePanel, BorderLayout.CENTER);
		add(simulationPanel, BorderLayout.NORTH);
		
		
	}
}
