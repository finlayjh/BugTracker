package com.finlay.ticketron;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class TintedGlassPane extends JPanel{

	Color tintColor;
	
	TintedGlassPane(Color color){
		tintColor = color;
	}
	
	protected void paintComponent(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setColor(tintColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
	}
}
