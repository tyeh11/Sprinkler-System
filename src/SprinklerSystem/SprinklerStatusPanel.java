package SprinklerSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class SprinklerStatusPanel extends JPanel implements Observer{
	JLabel houseImageLabel, compassImageLabel, timeLabel, dayOfWeekLabel;
	Map<String, SprinklerGroupGarden> locationToSprinklerGroupGardenMap;
	JButton SystemOnBtn, SystemOffBtn;
	SprinklerMgr sMgr;
	SystemClock systemClock;
	
	SprinklerStatusPanel(SprinklerMgr sMgr, SystemClock systemClock){
		this.sMgr = sMgr;
		this.systemClock = systemClock;
		setLayout(new GridLayout(3,3));
		setBackground(Color.green);
		
		locationToSprinklerGroupGardenMap = new HashMap<String, SprinklerGroupGarden>();
		
		BufferedImage houseBufferedImage = loadImage("house.png");
		houseImageLabel = new JLabel();
		Image houseImage = houseBufferedImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		if (houseImage != null) houseImageLabel.setIcon(new ImageIcon(houseImage));
		
		BufferedImage compassBufferedImage = loadImage("compass.png");
		compassImageLabel = new JLabel();
		Image compassImage = compassBufferedImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		if (compassImage != null) compassImageLabel.setIcon(new ImageIcon(compassImage));

		timeLabel = new JLabel("", SwingConstants.CENTER);
        Font labelFont = new Font("Serif", Font.PLAIN, 30);
        timeLabel.setFont(labelFont);
		
		dayOfWeekLabel = new JLabel("", SwingConstants.CENTER);
        Font labelFont2 = new Font("Serif", Font.PLAIN, 20);
        dayOfWeekLabel.setFont(labelFont2);
        
        SystemOnBtn = new JButton("System On");
        SystemOnBtn.setBackground(Color.yellow);
        SystemOffBtn = new JButton("System Off");
        SystemOffBtn.setBackground(Color.yellow);
        
        SystemOnBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setAllSprinklerFunctionStatus(true);
            	systemClock.setCollectWaterConsumptionStatus(true);
            }
        });
        
        SystemOffBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setAllSprinklerFunctionStatus(false);
            	systemClock.setCollectWaterConsumptionStatus(false);
            }
        });
        
        JTextField timeSettingTextField = new JTextField();
        JLabel timeSettingLabel = new JLabel("Set Current Time");
        JComboBox timeSettingJComboBox = new JComboBox();
        timeSettingJComboBox.addItem("");
        timeSettingJComboBox.addItem("07/01/2016");
        timeSettingJComboBox.setSelectedIndex(0);
//        timeSettingJComboBox.setPreferredSize(new Dimension(20,10));
        
        timeSettingJComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	String timeStr = (String)timeSettingJComboBox.getSelectedItem();
            	systemClock.setCurrentTime(timeStr);
            }
        });
        
        
		JPanel greenLawn1 = new JPanel(new BorderLayout());

		JPanel systemControlPanel = new JPanel(new GridLayout(1,2));
		systemControlPanel.setBackground(Color.green);
		systemControlPanel.add(SystemOnBtn);
		systemControlPanel.add(SystemOffBtn);
		greenLawn1.setBackground(Color.green);
		greenLawn1.add(systemControlPanel,BorderLayout.NORTH);
		greenLawn1.add(compassImageLabel);
		add(greenLawn1);
		
//		JPanel greenLawn1 = new JPanel();
//		greenLawn1.setBackground(Color.green);
//		add(greenLawn1);
//		add(compassImageLabel);
		
		SprinklerGroupGarden northSprinklerGroupGarden = new SprinklerGroupGarden("N");
		locationToSprinklerGroupGardenMap.put(northSprinklerGroupGarden.getGroupGardenLocation(), northSprinklerGroupGarden);
		add(northSprinklerGroupGarden);
		
		JPanel greenLawn2 = new JPanel(new GridLayout(3,1));
		JPanel greenLawn2TimePanel = new JPanel();
		greenLawn2TimePanel.setBackground(Color.green);
		greenLawn2.setBackground(Color.green);
		greenLawn2.add(timeLabel);
		greenLawn2.add(dayOfWeekLabel);
		greenLawn2TimePanel.add(timeSettingLabel);
		greenLawn2TimePanel.add(timeSettingJComboBox);
		greenLawn2.add(greenLawn2TimePanel,BorderLayout.CENTER);
		add(greenLawn2);
		
		SprinklerGroupGarden westSprinklerGroupGarden = new SprinklerGroupGarden("W");
		locationToSprinklerGroupGardenMap.put(westSprinklerGroupGarden.getGroupGardenLocation(), westSprinklerGroupGarden);
		add(westSprinklerGroupGarden);
		
		add(houseImageLabel);
		
		SprinklerGroupGarden eastSprinklerGroupGarden = new SprinklerGroupGarden("E");
		locationToSprinklerGroupGardenMap.put(eastSprinklerGroupGarden.getGroupGardenLocation(), eastSprinklerGroupGarden);
		add(eastSprinklerGroupGarden);
		
		JPanel greenLawn3 = new JPanel();
		greenLawn3.setBackground(Color.green);
		add(greenLawn3);
		
		SprinklerGroupGarden southSprinklerGroupGarden = new SprinklerGroupGarden("S");
		locationToSprinklerGroupGardenMap.put(southSprinklerGroupGarden.getGroupGardenLocation(), southSprinklerGroupGarden);
		add(southSprinklerGroupGarden);
		
		JPanel greenLawn4 = new JPanel();
		greenLawn4.setBackground(Color.green);
		add(greenLawn4);
		
	}
	
	/**
	 * load image from file
	 * @param filename the file name of the image
	 * @return the image
	 */
    public BufferedImage loadImage(String filename){
    	BufferedImage image = null;
    	try {
            // read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Could not open file: " + filename);
        }
    	return image;
    }
	
	
	public class SprinklerGroupGarden extends JPanel{
		int numOfSprinklerInGroup = 4;
		String groupGardenLocation;
		ArrayList<SprinklerSingleGarden> sprinklerSingleGardenArrayList;
		
		SprinklerGroupGarden(String groupGardenLocation){
			setLayout(new GridLayout(2,2));
			setBackground(Color.green);
			sprinklerSingleGardenArrayList = new ArrayList<SprinklerSingleGarden>();
			this.groupGardenLocation = groupGardenLocation;
			
			for (int i=0; i<numOfSprinklerInGroup; i++){
				SprinklerSingleGarden sprinklerSingleGarden = new SprinklerSingleGarden();
				sprinklerSingleGardenArrayList.add(sprinklerSingleGarden);
				add(sprinklerSingleGarden);
			}
		}
		
		public void setSprinklerSingleOnStatus(boolean onStatus, int index){
			sprinklerSingleGardenArrayList.get(index).setSprinklerSingleOnStatus(onStatus);
		}
		
		public void setSprinklerGroupOnStatus(boolean onStatus){
			for (SprinklerSingleGarden s : sprinklerSingleGardenArrayList){
				s.setSprinklerSingleOnStatus(onStatus);
			}
		}
		
		public void setSprinklerSingleFunctionStatus(boolean FunctionStatus, int index){
			sprinklerSingleGardenArrayList.get(index).setSprinklerSingleFunctionStatus(FunctionStatus);
		}
		
		public void setSprinklerGroupFunctionStatus(boolean FunctionStatus){
			for (SprinklerSingleGarden s : sprinklerSingleGardenArrayList){
				s.setSprinklerSingleFunctionStatus(FunctionStatus);
			}
		}
		
		public String getGroupGardenLocation(){
			return groupGardenLocation;
		}
		
	}
	
	public class SprinklerSingleGarden extends JPanel{
//		Image sprinklerOnImage, sprinklerOffImage;
		JLabel sprinklerOnStatusLabel, imageLabel;
		ImageLabelController imageLabelController;
		SprinklerSingleGarden(){
			setLayout(new BorderLayout());
			setBackground(Color.green);
			
//			BufferedImage sprinklerOnBufferedImage = loadImage("SprinklerOn.png");
//			BufferedImage sprinklerOffBufferedImage = loadImage("SprinklerOff.png");
//			sprinklerOnImage = sprinklerOnBufferedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//			sprinklerOffImage = sprinklerOffBufferedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			
//			imageLabel = new JLabel(new ImageIcon(sprinklerOffImage), JLabel.CENTER);
			imageLabelController = new ImageLabelController();
			imageLabel = imageLabelController.getImageLabel();
			
			sprinklerOnStatusLabel = new JLabel("Status:OK",JLabel.CENTER);
			sprinklerOnStatusLabel.setForeground(Color.blue);
	        Font labelFont = new Font("Serif", Font.PLAIN, 15);
	        sprinklerOnStatusLabel.setFont(labelFont);
	        
			add(imageLabel, BorderLayout.CENTER);
			add(sprinklerOnStatusLabel, BorderLayout.SOUTH);
		}
		
		public void setSprinklerSingleOnStatus(boolean onStatus){
			if (onStatus){
				imageLabelController.setImageIconOn();
//				imageLabel.setIcon(new ImageIcon(sprinklerOnImage));
			} else{
				imageLabelController.setImageIconOff();
//				imageLabel.setIcon(new ImageIcon(sprinklerOffImage));
			}
		}
		
		public void setSprinklerSingleFunctionStatus(boolean FunctionStatus){
			if (FunctionStatus){
				sprinklerOnStatusLabel.setText("Status:OK");
				sprinklerOnStatusLabel.setForeground(Color.blue);
			} else{
				sprinklerOnStatusLabel.setText("Status:NOT OK");
				sprinklerOnStatusLabel.setForeground(Color.red);
			}
		}
		
	}
	
	public class ImageLabelController{
		JLabel imageLabel;
		Image sprinklerOffImage, sprinklerOnImage;
		boolean onOffState;
		Timer timer;
		ImageLabelController(){
			BufferedImage sprinklerOnBufferedImage = loadImage("SprinklerOn.png");
			BufferedImage sprinklerOffBufferedImage = loadImage("SprinklerOff.png");
			sprinklerOnImage = sprinklerOnBufferedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			sprinklerOffImage = sprinklerOffBufferedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			imageLabel = new JLabel(new ImageIcon(sprinklerOffImage), JLabel.CENTER);
			onOffState = false;
		}
		
		public JLabel getImageLabel(){
			return imageLabel;
		}
		
		public void setImageIconOn(){
			startAnimation();
		}
		
		public void setImageIconOff(){
			stopAnimation();
			imageLabel.setIcon(new ImageIcon(sprinklerOffImage));
			this.onOffState = onOffState;
		}

		ActionListener timerListener = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if (onOffState){
            		imageLabel.setIcon(new ImageIcon(sprinklerOffImage));
            		onOffState = false;
            	}else if (!onOffState){
            		imageLabel.setIcon(new ImageIcon(sprinklerOnImage));
            		onOffState = true;
            	}
      
            }
        };
		
        void startAnimation() {
	          if (timer == null) {
	             timer = new Timer(200, timerListener);
	             timer.start();
	          }
	    }
		public void stopAnimation() {
	         if (timer != null) {
	            timer.stop();
	            timer = null;
	         }
	    }		
	}
	
	@Override
	public void update(Observable o, Object arg) {
//		if (arg instanceof String){
//			SprinklerMgr sMgr = (SprinklerMgr)sprinklerMgr;
//			String location = (String)arg;
//			boolean onStatus = sMgr.getSprinklerGroupMap().get(location).get(0).getOnStatus();
		if (arg instanceof ArrayList<?>){
//			ArrayList<Sprinkler> sprinklers = (ArrayList<Sprinkler>)arg;
//			Sprinkler sprinkler = sprinklers.get(0);
//			String location = sprinkler.getLocation();
//			boolean onStatus = sprinkler.getOnStatus();
//			
////			System.out.println(location);
////			System.out.println(onStatus);
//			locationToSprinklerGroupGardenMap.get(location).setSprinklerGroupOnStatus(onStatus);
			
//		}else if (arg instanceof Sprinkler){
//			int index = ((Sprinkler) arg).getIndex();
////			System.out.println(index);
//			String location = ((Sprinkler) arg).getLocation();
////			System.out.println(location);
//			boolean onStatus = ((Sprinkler) arg).getOnStatus();
////			System.out.println(onStatus);
//			locationToSprinklerGroupGardenMap.get(location).setSprinklerSingleOnStatus(onStatus, index);
			
		}else if (arg instanceof Boolean){
			int index = ((Sprinkler) o).getIndex();
			System.out.println(index);
			String location = ((Sprinkler) o).getLocation();
			System.out.println(location);
			boolean onStatus = (boolean)arg;
			System.out.println(onStatus);
			locationToSprinklerGroupGardenMap.get(location).setSprinklerSingleOnStatus(onStatus, index);
			
		}else if (arg instanceof String){
			String time = (String)arg;
			timeLabel.setText(time.split("!")[0].replace("-", "/"));
			dayOfWeekLabel.setText(time.split("!")[1]);
		}
		
	}
}
