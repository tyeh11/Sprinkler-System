package SprinklerSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import scehdule.ScheduleManager;

public class SchedulePanel extends JPanel{
	ScheduleManager scheduleManager;
	JPanel[][] timeSectionPanel;
	JButton[][][] groupButton;
	ActionListener buttonListener;
	JButton[] dayOfWeek;
	
	public SchedulePanel(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
		setLayout(new BorderLayout());
		
		Font buttomFont = new Font("Times New Roman", Font.BOLD, 20);
		
		timeSectionPanel = new JPanel[4][7];
		groupButton = new JButton[4][7][4]; //hour, dayofweek, group
		buttonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton temp = (JButton)e.getSource();
				temp.setBackground((temp.getBackground() == Color.GREEN ? Color.RED : Color.green));
				
			}
		};
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(5,7));
		
		Insets buttonInsets = new Insets(0, 0, 0, 0);
		JPanel northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(1,0));
		northPanel.add(new JPanel());
		//setLayout(new GridLayout(5, 8));
		dayOfWeek = new JButton[7];
		dayOfWeek[0] = new JButton("Sun");
		dayOfWeek[1] = new JButton("Mon");
		dayOfWeek[2] = new JButton("Tue");
		dayOfWeek[3] = new JButton("Wen");
		dayOfWeek[4] = new JButton("Thu");
		dayOfWeek[5] = new JButton("Fri");
		dayOfWeek[6] = new JButton("Sat");
		for (int i = 0; i < dayOfWeek.length; i++) {
			dayOfWeek[i].setActionCommand(Integer.toString(i));
			dayOfWeek[i].setBackground(Color.green);
			dayOfWeek[i].setPreferredSize(new Dimension(50,40));
			dayOfWeek[i].setFont(buttomFont);
			dayOfWeek[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton temp = (JButton)e.getSource();
					for (int j = 0; j < 4; j++) {
						for (int k = 0; k < 4; k++) {
							groupButton[k][Integer.parseInt(e.getActionCommand())][j].setBackground(temp.getBackground());
						}
					}
					Color bg = temp.getBackground() != Color.green ? Color.green : Color.red;
					temp.setBackground(bg);
				}
			});
			centerPanel.add(dayOfWeek[i]);
		}
		
		JButton[] setAllGroup = new JButton[4];
		setAllGroup[0] = new JButton("E");
		setAllGroup[1] = new JButton("W");
		setAllGroup[2] = new JButton("S");
		setAllGroup[3] = new JButton("N");
		for(int i = 0; i < 4; i++){
			setAllGroup[i].setActionCommand(Integer.toString(i));
			setAllGroup[i].setBackground(Color.green);
			setAllGroup[i].setFont(buttomFont);
			setAllGroup[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton temp = (JButton)e.getSource();
					for (int j = 0; j < 4; j++) {
						for (int k = 0; k < 7; k++) {
							groupButton[j][k][Integer.parseInt(e.getActionCommand())].setBackground(temp.getBackground());
						}
					}
					Color bg = temp.getBackground() != Color.green ? Color.green : Color.red;
					temp.setBackground(bg);
				}
			});
		}
		
		
//		JPanel northPanel = new JPanel();
//		add(northPanel, BorderLayout.NORTH);
//		northPanel.setLayout(new GridLayout(1,0));
//		northPanel.add(new JPanel());
//		northPanel.add(new JLabel(" Sun"));
//		northPanel.add(new JLabel(" Mon"));
//		northPanel.add(new JLabel(" Tue"));
//		northPanel.add(new JLabel("  Wen"));
//		northPanel.add(new JLabel("     Thu"));
//		northPanel.add(new JLabel("      Fri"));
//		northPanel.add(new JLabel("      Sat"));
		
		JPanel westhPanel = new JPanel();
		add(westhPanel, BorderLayout.WEST);
		westhPanel.setLayout(new GridLayout(0,1));
		westhPanel.add(new JLabel(""));
		westhPanel.add(new JLabel("0 to 6"));
		westhPanel.add(new JLabel("6 to 12"));
		westhPanel.add(new JLabel("12 to 18"));
		westhPanel.add(new JLabel("18 to 24"));

//		JPanel centerPanel = new JPanel();
//		add(centerPanel, BorderLayout.CENTER);
//		centerPanel.setLayout(new GridLayout(4,7));
//		TitledBorder exampleTitle = 
//				new TitledBorder(LineBorder.createGrayLineBorder());
		LineBorder exampleTitle = new LineBorder(Color.black, 2);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				timeSectionPanel[i][j] = new JPanel();
				timeSectionPanel[i][j].setLayout(new GridLayout(2,2));
				
				groupButton[i][j][0] = new JButton("E");
				groupButton[i][j][0].addActionListener(buttonListener);
				groupButton[i][j][0].setMargin(buttonInsets);
				groupButton[i][j][0].setBackground(Color.red);
				groupButton[i][j][0].setFont(buttomFont);
				timeSectionPanel[i][j].add(groupButton[i][j][0]);
				
				groupButton[i][j][1] = new JButton("W");
				groupButton[i][j][1].addActionListener(buttonListener);
				groupButton[i][j][1].setMargin(buttonInsets);
				groupButton[i][j][1].setBackground(Color.red);
				groupButton[i][j][1].setFont(buttomFont);
				timeSectionPanel[i][j].add(groupButton[i][j][1]);
				
				groupButton[i][j][2] = new JButton("S");
				groupButton[i][j][2].addActionListener(buttonListener);
				groupButton[i][j][2].setMargin(buttonInsets);
				groupButton[i][j][2].setBackground(Color.red);
				groupButton[i][j][2].setFont(buttomFont);
				timeSectionPanel[i][j].add(groupButton[i][j][2]);
				
				groupButton[i][j][3] = new JButton("N");
				groupButton[i][j][3].addActionListener(buttonListener);
				groupButton[i][j][3].setMargin(buttonInsets);
				groupButton[i][j][3].setBackground(Color.red);
				groupButton[i][j][3].setFont(buttomFont);
				timeSectionPanel[i][j].add(groupButton[i][j][3]);
			  //timeSectionPanel[i][j].add(new JLabel("tet"));
		      //timeSectionPanel[i][j].add(new JLabel(Integer.toString(i) + "," + Integer.toString(j)));
				centerPanel.add(timeSectionPanel[i][j]);
				timeSectionPanel[i][j].setBorder(exampleTitle);
			}
		}
		
		JPanel southPanel = new JPanel(new GridLayout(1,0));
		JButton saveButton = new JButton("Set Schedule");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scheduleManager.clearSchedule(1);
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 7; j++) {
						for (int k = 0; k < 4; k++) {
						boolean on = groupButton[i][j][k].getBackground() == Color.green;
						scheduleManager.updateSchedule(groupButton[i][j][k].getText(), (long)j, (long)i, 1, on);
						}
					}
				}
				scheduleManager.setSprinklers();
			}
		});
		for(int i = 0; i < 4; i++){
			southPanel.add(setAllGroup[i]);
		}
		southPanel.add(saveButton);
		add(southPanel, BorderLayout.SOUTH);
	}
}
