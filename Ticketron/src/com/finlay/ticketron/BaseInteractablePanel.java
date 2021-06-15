package com.finlay.ticketron;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class BaseInteractablePanel extends RoundedJPanel implements ActionListener{
	int width;
	int height;
	protected SpringLayout layout;
	protected JButton btnClose;
	protected JPanel bottomRightBtnContainer;
	protected int textCenterLine;
	private JPanel cardContainer;
	private JScrollPane scrollPane;
	private int cardCount = 0;
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

	BaseInteractablePanel(boolean hasShadow) {
		super(hasShadow);
		width = 400;
		height = 500;
		
		setSize(width, height);
		layout = new SpringLayout();
		setLayout(layout);
		setOpaque(false);
		setBackground(Color.BLUE);
		
		initCloseButton();
		initScrollPane();
	}	
	
	protected void initCloseButton() {
		btnClose = new JButton("X");
        btnClose.setBackground(null);
        btnClose.setBorder(null);
        btnClose.setFocusable(false);
        btnClose.setPreferredSize(new Dimension(25,25));
        btnClose.setFont(new Font("Arial", Font.BOLD, 20));
        btnClose.addActionListener(this);
        btnClose.setActionCommand("Close");
        add(btnClose);
        layout.putConstraint(SpringLayout.NORTH, btnClose, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, this);
	}
	
	protected void initScrollPane() {
		cardContainer = new JPanel();
		cardContainer.setOpaque(false);
		System.out.println("shadow width: " + getShadowGap());
		cardContainer.setBorder(new EmptyBorder(0,0,0,-8));
		cardContainer.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
		scrollPane = new JScrollPane(cardContainer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBar(scrollBar);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));
	    add(scrollPane);
	    layout.putConstraint(SpringLayout.NORTH, scrollPane, 40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -40, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -20 - getShadowGap(), SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, this);
	}
	
	protected void loadTickets(ArrayList<Ticket> ticketsList) {
		if(!ticketsList.equals(tickets)) {
			for(Ticket t: ticketsList) {
				if(!tickets.contains(t)) {
					tickets.add(t);
					addTicketCard(new TicketCard(t));
				}
			}
			cardCount = tickets.size();
			cardContainer.revalidate();
			//repaint();
		}
	}
	
	protected void removeTicketCard(TicketCard t){
		Component[] components = cardContainer.getComponents();
		for(Component c: components) {
			if(c.equals(t)) {
				cardContainer.remove(t);
				cardCount--;
				if(t.getOptionalSpacing() != null) {
					cardContainer.remove(t.getOptionalSpacing());
				}
			}
		}
	}
	
	protected void addTicketCard(TicketCard t) {
		System.out.println("adding...");
		if(cardCount>0) {
			Component box = Box.createRigidArea(new Dimension(20,20));
			t.setOptionalSpacing(box);
			cardContainer.add(box);
		}
		cardContainer.add(t);
		cardContainer.revalidate();
		cardCount++;
	}
	
	protected void setCloseButtonShowing(boolean bool) {
		btnClose.setVisible(bool);
	}
	
	protected int getNumTickets() {
		return cardCount;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Close")) {
			try {
				ProjectViewUI.getInstance().closeTicketView(null, false);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}
	
	/*@Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int shadowOffset = 4;
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setComposite(AlphaComposite.SrcIn);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width, height, radius, radius);                                             //paint background
        
        //graphics.setColor(Color.BLACK);
        //graphics.drawRoundRect(0, 0, width-1, height-1, radius, radius);  
    }*/
}
