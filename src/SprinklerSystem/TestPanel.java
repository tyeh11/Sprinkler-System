package SprinklerSystem;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TestPanel extends JPanel{
	SprinklerMgr sMgr;
	TestPanel(SprinklerMgr sprinklerMgr){
		sMgr = sprinklerMgr;
		setLayout(new GridLayout(4,2));
		JButton b1 = new JButton("North Off");
		b1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(false, "N");
            }
        });
		JButton b2 = new JButton("North On");
		b2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(true, "N");
            }
        });

		
		JButton b3 = new JButton("South Off");
		b3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(false, "S");
            }
        });
		
		
		JButton b4 = new JButton("South On");
		b4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(true, "S");
            }
        });
		
		JButton b5 = new JButton("East Off");
		b5.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(false, "E");
            }
        });
		
		
		JButton b6 = new JButton("East On");
		b6.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(true, "E");
            }
        });
		
		JButton b7 = new JButton("West Off");
		b7.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(false, "W");
            }
        });
		
		
		JButton b8 = new JButton("West On");
		b8.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	sMgr.setSprinklerOnStatus(true, "W");
            }
        });
		
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
		add(b6);
		add(b7);
		add(b8);
	}
}
