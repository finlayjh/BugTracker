package com.finlay.ticketron;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class RoundedJPanel extends JPanel{
	
	private int radius;
	private Boolean shadow;
	private boolean hoverGlow;
	private int shadowGap = 8;
	private Color glowColor;
	private Color defaultColor = Color.YELLOW;

	public RoundedJPanel(Boolean hasShadow) {
		hoverGlow = false;
		radius = 20;
		shadow = hasShadow;
		if(!shadow)
			shadowGap = 0;
	}
	
	public void isGlowing(Boolean isGlowing) {
		glowColor = defaultColor;
		hoverGlow = isGlowing;
	}
	
	public void isGlowing(Boolean isGlowing, Color color) {
		glowColor = color;
		hoverGlow = isGlowing;
	}
	
	protected int getShadowGap() {
		return shadowGap;
	}
	
	protected int getRadius() {
		return radius;
	}
	
	protected void setRadius(int newRadius) {
		radius = newRadius;
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(radius, radius);
        int width = getWidth() - shadowGap;
        int height = getHeight() - shadowGap;
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the rounded panel with borders.
        if(shadow) {
        	graphics.setColor(Color.DARK_GRAY);
            graphics.fillRoundRect(shadowGap, shadowGap, width, height, arcs.width, arcs.height); //paint shadow
            
            graphics.setColor(super.getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height); //paint background
        }
        else {
        	graphics.setColor(super.getBackground());
            graphics.fillRoundRect(shadowGap, shadowGap, width, height, arcs.width, arcs.height); //paint background
        }
        if(hoverGlow) {
        	graphics.setColor(glowColor);
        	graphics.setStroke(new BasicStroke(6));
            graphics.drawRoundRect(1, 1, width-1, height-1, arcs.width, arcs.height); //paint background
            graphics.setStroke(new BasicStroke(1));
        }
    }
}
