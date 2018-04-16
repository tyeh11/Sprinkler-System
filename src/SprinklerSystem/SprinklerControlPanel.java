package SprinklerSystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import scehdule.ScheduleManager;

public class SprinklerControlPanel extends JPanel{
	private ScheduleManager scheduleManager;
	private JTextField[] hoursToOverride;
	private JComboBox<String>[] operationSelection;
	private String[] groupNames;
	private SystemClock systemClock;
	public SprinklerControlPanel(ScheduleManager scheduleManager, SystemClock systemClock) {
		this.systemClock = systemClock;
		groupNames = new String[4];
		groupNames[0] = "E";
		groupNames[1] = "W";
		groupNames[2] = "S";
		groupNames[3] = "N";
		this.scheduleManager = scheduleManager;
		hoursToOverride = new JTextField[4];
		operationSelection = new JComboBox[4];
		Dimension dimension = new Dimension(50,30);
		for (int i = 0; i < 4; i++) {
			hoursToOverride[i] = new JTextField("0");
			hoursToOverride[i].setPreferredSize(dimension);
			operationSelection[i] = new JComboBox<String>();
			operationSelection[i].addItem("Do nothing");
			operationSelection[i].addItem("Keep Turned ON");
			operationSelection[i].addItem("Keep Turned Off");
		}
		
		setLayout(new BorderLayout());
		add(new JLabel("You can set each group to on/off for a period of time."), BorderLayout.NORTH);
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(2,2));

		for (int i = 0; i < Location.values().length; i++) {
			JLabel tempLabel = new JLabel("Select a operation: ");	
			JPanel temp1 = new JPanel();
			temp1.add(tempLabel);
			temp1.add(operationSelection[i]);
			
			JPanel temp2 = new JPanel();
			tempLabel = new JLabel("Enter the Value of Hour");
			temp2 = new JPanel();
			temp2.add(tempLabel);
			temp2.add(hoursToOverride[i]);
			
			JPanel temp3 = new JPanel();
			TitledBorder exampleTitle = 
					new TitledBorder(LineBorder.createGrayLineBorder(),Location.values()[i].toString() + " Group");
			temp3.setBorder(exampleTitle);
			temp3.setLayout(new GridLayout(0, 1));
			temp3.add(temp1);
			temp3.add(temp2);
			center.add(temp3);
		}
		add(center, BorderLayout.CENTER);
		
		JButton runSetup = new JButton("Confirm and Enable the Setting");
		runSetup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scheduleManager.clearSchedule(3);
				for (int i = 0; i < Location.values().length; i++) {
					if (operationSelection[i].getSelectedIndex() == 1) {
						scheduleManager.updateSchedule(groupNames[i], 
								systemClock.getDate(), (long) Integer.parseInt(hoursToOverride[i].getText()), 3, true);
					}
					else if (operationSelection[i].getSelectedIndex() == 2) {
						scheduleManager.updateSchedule(groupNames[i], 
								systemClock.getDate(), (long)Integer.parseInt(hoursToOverride[i].getText()), 3, false);
					}
				}
				scheduleManager.levelSwitch(3, true);
			}
		});
		
		JButton stopSetup = new JButton("Disable the Setting");
		stopSetup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scheduleManager.levelSwitch(3, false);
			}
		});
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 0));
		southPanel.add(stopSetup);
		southPanel.add(runSetup);
		add(southPanel, BorderLayout.SOUTH);
		
		
	}
	

}
