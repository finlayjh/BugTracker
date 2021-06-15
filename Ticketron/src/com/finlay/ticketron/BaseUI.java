package com.finlay.ticketron;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import java.awt.Frame;

public class BaseUI extends JFrame{

	protected JFrame frame;
	
	protected BaseUI() {
		
		frame = new JFrame("Ticketron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setResizable(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
	}
	
	protected Frame getFrame() {
		return frame;
	}
	
}
