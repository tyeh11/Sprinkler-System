package SprinklerSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WaterConsumptionPanel extends JPanel implements Observer{
	WaterConsumptionMgr wcMgr;
	BarChart chart;
	JLabel waterConsumptionTitle, chartXLabel, chartYLabel;
	
	WaterConsumptionPanel(WaterConsumptionMgr waterConsumptionMgr){
		wcMgr = waterConsumptionMgr;
		wcMgr.addObserver(this);
		setLayout(new BorderLayout());
		
		int getTotalWaterConsumption = wcMgr.getTotalWaterConsumption();
		waterConsumptionTitle = new JLabel(String.format("Your total water consumption is : %d", getTotalWaterConsumption),SwingConstants.CENTER);
    	waterConsumptionTitle.setForeground(Color.BLUE);
        Font waterConsumptionTitleFont = new Font("Serif", Font.PLAIN, 20);
        waterConsumptionTitle.setFont(waterConsumptionTitleFont);
        add(waterConsumptionTitle, BorderLayout.NORTH);
        
		chart = new BarChart();
		add(chart,BorderLayout.CENTER);

//		 the data values are hardcoded
		for (WaterConsumptionRecord wcr : wcMgr.getWaterConsumptionRecordArrayList()){
			chart.addBar(wcr.getDate(), wcr.getWaterConsumption());
		}
	}

	@Override
	public void update(Observable o, Object waterConsumptionRecord) {
		if (waterConsumptionRecord instanceof WaterConsumptionRecord){
			WaterConsumptionRecord wcr = (WaterConsumptionRecord)waterConsumptionRecord;
			chart.addBar(wcr.getDate(), wcr.getWaterConsumption());
			WaterConsumptionMgr wcMgr = (WaterConsumptionMgr)o;
			int getTotalWaterConsumption = wcMgr.getTotalWaterConsumption();
			String waterConsumptionTitleStr = String.format("Your total water consumption is : %d", getTotalWaterConsumption);
			waterConsumptionTitle.setText(waterConsumptionTitleStr);
			
//			for (WaterConsumptionRecord wcr : wcMgr.getWaterConsumptionRecordArrayList()){
//				chart.addBar(wcr.getDate(), wcr.getWaterConsumption());
//			}
//			chart.repaint();
			
		}
	}
	
	
//	@Override
//	public void update(Observable o, Object getWaterConsumptionRecordArrayList) {
//		for (WaterConsumptionRecord wcr : wcMgr.getWaterConsumptionRecordArrayList()){
//			chart.addBar(wcr.getDate(), wcr.getWaterConsumption());
//		}
//		chart.repaint();
//	}
}

class BarChart extends JPanel{
	private Map<String, Integer> bars = new LinkedHashMap<String, Integer>();
	public BarChart()
	{
//		setPreferredSize(new Dimension(500,500));		
	}
	/**
	 * Add new bar to chart
	 * @param color color to display bar
	 * @param value size of bar
	 */
	public void addBar(String date, int value)
	{
		bars.put(date, value);	
		while (bars.size() > 14){
			if (!bars.isEmpty()){
			      String FirstDate = bars.keySet().iterator().next();
			      bars.remove(FirstDate);
			}
		}
		// cannot call paintComponent() or paint() directly
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		// determine longest bar
		int max = Integer.MIN_VALUE;
		for (Integer value : bars.values())
		{
			max = Math.max(max, value);
		}
//		int max = 200;

//		System.out.println(getWidth());
//		System.out.println(getHeight());

		Line2D lineY = new Line2D.Double(100,getHeight()-100,100,100); 
		g2d.draw(lineY);
		Line2D lineX = new Line2D.Double(100,getHeight()-100, getWidth()-100,getHeight()-100); 
		g2d.draw(lineX);

		ListIterator<Map.Entry<String, Integer>> barIter = new ArrayList<>(bars.entrySet()).listIterator(bars.size());
		
		int x = 100;
		int width = ((getWidth()-200) / 14) - 2; //2 weeks so 14
		int count = 0;
		// paint bars
		while (barIter.hasPrevious()) {
		    Map.Entry<String, Integer> entry = barIter.previous();
		    String dateString = entry.getKey().replace("-","/");

			int value = entry.getValue();
			int height = (int)((getHeight()-200) * ((double)value / max));
			g.setColor(Color.cyan);
			g.fillRect(x, getHeight() - height - 100, width, height);
			g.setColor(Color.black);
			g.drawRect(x, getHeight() - height - 100, width, height);
			
//			Font newFont = new Font("Courier",Font.PLAIN,10);    
//			g2d.setFont(newFont);
			if (count%2 != 0) g2d.drawString(dateString, x-2, getHeight() - 85 );
			else g2d.drawString(dateString, x-2, getHeight() - 70 );
			
			g2d.drawString(String.valueOf(value), x+6, getHeight() - 105 - height);
			
			x += (width + 2);
			count++;
		}
		x = 100;
		count = 0;
		
		Font labelXFont = new Font("Courier",Font.PLAIN,20);    
		g2d.setFont(labelXFont);
		g2d.drawString("Date (recent 14 days)", getWidth()/2-110 , getHeight() - 40 );
		
//		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
//		g2d.setTransform(at);
//		g2d.drawString("Water Consumption", -(getHeight()/2)-100 , 70 );
		Font labelYFont = new Font("Courier",Font.PLAIN,15);    
		g2d.setFont(labelYFont);
		g2d.drawString("Water", 30 , getHeight()/2 );
		g2d.drawString("Consumption", 10 , getHeight()/2+20 );
		
//		for (String date : bars.keySet())
//		{
//			int value = bars.get(date);
//			int height = (int)((getHeight()-200) * ((double)value / max));
//			g.setColor(Color.cyan);
//			g.fillRect(x, getHeight() - height - 100, width, height);
//			g.setColor(Color.black);
//			g.drawRect(x, getHeight() - height - 100, width, height);
//			x += (width + 2);
//		}
	}
//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension(bars.size() * 10 + 2, 50);
//	}
//	
}