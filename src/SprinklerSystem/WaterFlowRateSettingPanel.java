package SprinklerSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WaterFlowRateSettingPanel extends JPanel{
	GroupWaterFlowRateSettingPanel eastGardenPanel, westGardenPanel, southGardenPanel, northGardenPanel;
	JPanel confirmPanel;
	JButton confirmButton;
	
	WaterFlowRateSettingPanel(SprinklerMgr sprinklerMgr){
		setLayout(new GridLayout(3,3));
		eastGardenPanel = new GroupWaterFlowRateSettingPanel("EastSprinklers");
		westGardenPanel = new GroupWaterFlowRateSettingPanel("WestSprinklers");
		southGardenPanel = new GroupWaterFlowRateSettingPanel("SouthSprinklers");
		northGardenPanel = new GroupWaterFlowRateSettingPanel("NorthSprinklers");
		add(eastGardenPanel);
		add(westGardenPanel);
		add(southGardenPanel);
		add(northGardenPanel);
		add(new JPanel());
		
		confirmPanel = new JPanel(new FlowLayout());
		confirmButton = new JButton("confirm all settings");
		confirmPanel.add(confirmButton);
		add(confirmPanel);
		
		confirmButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	if (eastGardenPanel.getWaterFlowRateStatus()) 
            		sprinklerMgr.setSprinklerFlowRate(Integer.valueOf(eastGardenPanel.getWaterFlowRate()),"E");
            	if (westGardenPanel.getWaterFlowRateStatus()) 
            		sprinklerMgr.setSprinklerFlowRate(Integer.valueOf(westGardenPanel.getWaterFlowRate()),"W");
            	if (southGardenPanel.getWaterFlowRateStatus()) 
            		sprinklerMgr.setSprinklerFlowRate(Integer.valueOf(southGardenPanel.getWaterFlowRate()),"S");
            	if (northGardenPanel.getWaterFlowRateStatus()) 
            		sprinklerMgr.setSprinklerFlowRate(Integer.valueOf(northGardenPanel.getWaterFlowRate()),"N");
            	sprinklerMgr.reportGroupInfo();
            }
        });

	}

	public class GroupWaterFlowRateSettingPanel extends JPanel{
		JPanel flowRateTextFieldPanel;
		JLabel sprinklerGroup;
		JTextField flowRateTextField;
		public GroupWaterFlowRateSettingPanel(String sprinklerGroup){
			setLayout(new GridLayout(2,1));

			this.sprinklerGroup = new JLabel(sprinklerGroup+" water flow rate (per hour) : ");
			
			flowRateTextFieldPanel = new JPanel(new FlowLayout());
			this.flowRateTextField = new JTextField(10);
			flowRateTextField.setText("1");
			flowRateTextFieldPanel.add(this.flowRateTextField);
			
			add(this.sprinklerGroup);
			add(this.flowRateTextFieldPanel);
		}
		
		public boolean getWaterFlowRateStatus() {
			String answerText = flowRateTextField.getText();
			if (answerText.equals("")){
				return false;
			}else return true;
		}
		
		protected String getWaterFlowRate() {
			return flowRateTextField.getText();
		}
	}
}