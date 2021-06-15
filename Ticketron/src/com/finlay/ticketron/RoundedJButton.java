package com.finlay.ticketron;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class RoundedJButton extends JButton {
	
	private int radius;
	private Boolean shadow;
	private int shadowGap = 4;
	
	private boolean mouseOver = false;
	private boolean mousePressed = false;

	public RoundedJButton(String text, Boolean shadow) {
		super(text);
		this.shadow = shadow;
		radius = 10;

		setFocusable(false);
		setSize(getPreferredSize());
		setOpaque(false);
		this.setForeground(Color.RED);
		setFont(new Font("Arial", Font.PLAIN, 15));
		//setForeground(Color.RED);	
		
		MouseAdapter mouseListener = new MouseAdapter(){
			
			@Override
			public void mousePressed(MouseEvent me){
				if(contains(me.getX(), me.getY())){
					mousePressed = true;
					repaint();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent me){
				mousePressed = false;
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent me){
				mouseOver = false;
				mousePressed = false;
				repaint();
			}
			
			@Override
			public void mouseMoved(MouseEvent me){
				mouseOver = contains(me.getX(), me.getY());
				repaint();
			}	
		};
		
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);		
	}
		
	@Override
	protected void paintBorder(Graphics g) {
	    //g.setColor(Color.BLACK);
	    //g.drawOval(0, 0, getSize().width-1,     getSize().height-1);
	}

	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /*if(mousePressed){

		}
		else{

		}
		
		if(mouseOver){

		}
		else{

		}

*/
        int width;
        int height;
        //Draws the rounded panel with borders.
        if(shadow) {
        	width = getWidth() - shadowGap;
        	height = getHeight() - shadowGap;
        	
        	g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(shadowGap, shadowGap, width, height, radius, radius); //paint shadow
            
            g.setColor(super.getBackground());
            g.fillRoundRect(0, 0, width, height, radius, radius); //paint background
        }
        else {
        	width = getWidth();
            height = getHeight();
        	g.setColor(super.getBackground());
            g.fillRoundRect(0, 0, width, height, radius, radius); //paint background
        }
        
        
        g.setColor(Color.WHITE);
        g.setFont(getFont());
        FontMetrics metrics = g.getFontMetrics(getFont());
		int stringWidth;
		int stringHeight;
		stringWidth = metrics.stringWidth(getText());
		stringHeight = (int) getFont().createGlyphVector(metrics.getFontRenderContext(), getText()).getVisualBounds().getHeight();
		
		g.drawString(getText(), getWidth()/2 - stringWidth/2, getHeight()/2 + stringHeight/2);
    }

}
