package com.finlay.ticketron;

import java.awt.Color;
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

public class CircleJButton extends JButton{
	
	private int diameter;
	private Boolean shadow;
	private int shadowGap = 4;
	private Point centerPoint;
	private Font font = new Font("Arial", Font.PLAIN, 50);
	private int fontSize = font.getSize();

	private boolean mouseOver = false;
	private boolean mousePressed = false;
	
	public CircleJButton(String text, Boolean shadow) {
		
		super(text);
		diameter = 60;
		this.shadow = shadow;
		centerPoint = new Point(0,0);
		
		setSize(diameter, diameter);
		setOpaque(false);
		setFont(new Font("Arial", Font.PLAIN, 15)); 
		
		
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
	
	protected void setCenterLocation(Point center) {
		centerPoint = center;
	}
	
	public void setFont(Font f) {
		font = f;
	}
	
	private int getDiameter(){
		int diameter = Math.min(getWidth(), getHeight());
		return diameter;
	}
	
	
	@Override
	public boolean contains(int x, int y){
		int radius = getDiameter()/2;
		return Point2D.distance(x, y, getWidth()/2, getHeight()/2) < radius;
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
        int currHeight = getSize().height;
        if(mousePressed){
        	if(currHeight>diameter*.9) {
				setSize((int) Math.round(diameter*.9), (int) Math.round(diameter*.9));
				fontSize = (int) Math.round(font.getSize() * .9);
				setBounds(centerPoint.x - getSize().height/2, centerPoint.y - getSize().height/2, getSize().height, getSize().height);
			}
		}
		else{
			if(currHeight>diameter*.9 && !mouseOver) {
				setSize(diameter, diameter);
				fontSize = font.getSize();
				setBounds(centerPoint.x - getSize().height/2, centerPoint.y - getSize().height/2, getSize().height, getSize().height);
			}
		}
		
		if(mouseOver){
			if(currHeight<diameter * 1.1 && !mousePressed) {
				setSize((int) Math.round(diameter * 1.1), (int) Math.round(diameter * 1.1));
				fontSize = (int) Math.round(font.getSize() * 1.1);
				setBounds(centerPoint.x - getSize().height/2, centerPoint.y - getSize().height/2, getSize().height, getSize().height);
			}
		}
		else{
			if(currHeight>diameter || currHeight<diameter) {
				setSize(diameter, diameter);
				fontSize = font.getSize();
				setBounds(centerPoint.x - getSize().height/2, centerPoint.y - getSize().height/2, getSize().height, getSize().height);
			}
		}
		
    	int d = getSize().height;
    	if(shadow) {
    		d -= shadowGap;
    		graphics.setColor(Color.DARK_GRAY);
    		g.fillOval(0+shadowGap, 0+shadowGap, d, d);
    		graphics.setColor(super.getBackground());
    		g.fillOval(0, 0, d, d);
    	}else {
			graphics.setColor(super.getBackground());
    		g.fillOval(0, 0, d, d);
    	}
        
        g.setColor(Color.BLACK);
		g.setFont(new Font("Ariel", Font.PLAIN, fontSize));
		FontMetrics metrics = g.getFontMetrics(font);
		int stringWidth;
		int stringHeight;
		stringWidth = (int) font.createGlyphVector(metrics.getFontRenderContext(), getText()).getVisualBounds().getWidth();
		int stringWidthPadding = metrics.stringWidth(getText()) - stringWidth;
		stringHeight = (int) font.createGlyphVector(metrics.getFontRenderContext(), getText()).getVisualBounds().getHeight();
		int stringHeightPadding = metrics.getAscent() - stringHeight;
		
		g.drawString(getText(), getWidth()/2 - stringWidth/2 - stringWidthPadding/2, getHeight()/2 + stringHeight/2 + stringHeightPadding/4); //idk why stringHeightPadding/4 works but it does
		//System.out.println("button: " + getWidth() + ","+getHeight()+" and string: " + stringWidth + ","+stringHeight);
		//System.out.println("padding-> x: " + stringWidthPadding + " y:"+stringHeightPadding);

    }

}
