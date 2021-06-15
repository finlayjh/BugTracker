package com.finlay.ticketron;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class FadingLabel extends JLabel{
	
	private float alpha;
	
	public FadingLabel() {
		setForeground(new Color(255, 0, 0));
		setFont(new Font("Arial", Font.PLAIN ,40));
		alpha = 0f;
		setAlpha(alpha);
	}
	
	protected void popAndFade(String text) {
		setText(text);
		setSize(getPreferredSize());
		this.setLocation(ProjectViewUI.getInstance().getFrame().getWidth()/2 - getWidth()/2, getY());
		Runnable runnable = new Runnable() {
			public void run(){
	            System.out.println("Runnable running");
	            Timer timer = new Timer();
	            long initialDelay = 1000L;
	            setAlpha(1f);
	            TimerTask fadeTask = new TimerTask() {
	            	float alpha = getAlpha();
	            	public void run() {
	            		alpha -= 0.05f;
	            		if(alpha>0) {
	            			setAlpha(alpha);
	            		}
	            		else {
	            			setAlpha(0f);
	            			cancel();
	            		}
	                }
	            };
	            timer.scheduleAtFixedRate(fadeTask , initialDelay, 100L);	            
	        }
		};
		Thread thread = new Thread(runnable);
		thread.start();
		System.out.println("truther?^^");
	}
	
	public float getAlpha() {
        return alpha;
    }
	
	public void setAlpha(float value) {
        if (alpha != value) {
            float old = alpha;
            alpha = value;
            firePropertyChange("alpha", old, alpha);
            repaint();
        }
    }
	
	 @Override
     public void paint(Graphics g) {
         Graphics2D g2d = (Graphics2D) g.create();
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
         super.paint(g2d);
         g2d.dispose();
     }
}
