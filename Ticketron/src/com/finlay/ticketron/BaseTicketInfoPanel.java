package com.finlay.ticketron;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class BaseTicketInfoPanel extends RoundedJPanel implements ActionListener{
	
	int width;
	int height;
	private int radius = 15;
	protected SpringLayout layout;
	protected TicketCard ticketCard;
	protected JLabel subjectLabel;
	protected JPanel subjectContainer;
	protected JTextArea subjectText;
	protected JLabel typeLabel;
	protected JPanel typeContainer;
	protected JLabel ratingLabel;
	protected JPanel ratingContainer;
	protected JLabel dateCreatedLabel;
	protected JPanel dateCreatedContainer;
	protected JButton btnClose;
	protected JPanel bottomRightBtnContainer;
	protected JPanel bottomLeftBtnContainer;
	private JPanel notesContainer;
	private JScrollPane scrollPane;
	private Font strikethrough;
	protected int textCenterLine;
	protected int shadowOffset = 4;

	BaseTicketInfoPanel() {
		super(false);
		width = 400;
		height = 500;
		textCenterLine = 100;
		
		setSize(width, height);
		layout = new SpringLayout();
		setLayout(layout);
		setOpaque(false);
		setBackground(Color.WHITE);
		
		initCloseButton();
		initTicketLayout();
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
	
	protected void initTicketLayout() {
		subjectLabel = new JLabel("Subject:"); 
		subjectLabel.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
		subjectLabel.setSize(100, 20); 
        add(subjectLabel); 
		layout.putConstraint(SpringLayout.EAST, subjectLabel, textCenterLine, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, subjectLabel, 30, SpringLayout.NORTH, this);
        
        subjectText = new JTextArea(1,20); 
        subjectText.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
        subjectText.setWrapStyleWord(true);
        subjectText.setLineWrap(true);
        subjectText.setOpaque(false);
        subjectText.setEditable(false);
        subjectText.setFocusable(false);
        add(subjectText);
        layout.putConstraint(SpringLayout.WEST, subjectText, 5, SpringLayout.EAST, subjectLabel);
        layout.putConstraint(SpringLayout.NORTH, subjectText, 0, SpringLayout.NORTH, subjectLabel);
        layout.putConstraint(SpringLayout.EAST, subjectText, -10, SpringLayout.EAST, this);
		
		typeLabel = new JLabel("Type:"); 
        typeLabel.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
        typeLabel.setSize(100, 20);
        add(typeLabel);
        layout.putConstraint(SpringLayout.EAST, typeLabel, textCenterLine, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, typeLabel, 20, SpringLayout.SOUTH, subjectText);
		
		typeContainer = new JPanel();
		typeContainer.setSize(100, 20); 
		typeContainer.setOpaque(false);
        add(typeContainer);  
        layout.putConstraint(SpringLayout.WEST, typeContainer, 5, SpringLayout.EAST, typeLabel);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, typeContainer, 0, SpringLayout.VERTICAL_CENTER, typeLabel);
        
		ratingLabel = new JLabel("Rating:"); 
        ratingLabel.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
        ratingLabel.setSize(100, 20); 
        add(ratingLabel); 
        layout.putConstraint(SpringLayout.EAST, ratingLabel, textCenterLine, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, ratingLabel, 20, SpringLayout.SOUTH, typeLabel);
		
		ratingContainer = new JPanel(); 
		ratingContainer.setSize(100, 20); 
		ratingContainer.setOpaque(false);
	    add(ratingContainer); 
	    layout.putConstraint(SpringLayout.WEST, ratingContainer, 5, SpringLayout.EAST, ratingLabel);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, ratingContainer, 0, SpringLayout.VERTICAL_CENTER, ratingLabel);
        
        dateCreatedLabel = new JLabel("Created:"); 
        dateCreatedLabel.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
        dateCreatedLabel.setSize(100, 20); 
        add(dateCreatedLabel); 
        layout.putConstraint(SpringLayout.EAST, dateCreatedLabel, textCenterLine, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, dateCreatedLabel, 20, SpringLayout.SOUTH, ratingLabel);
		
		dateCreatedContainer = new JPanel();
		dateCreatedContainer.setSize(100, 20); 
		dateCreatedContainer.setOpaque(false);
	    add(dateCreatedContainer); 
	    layout.putConstraint(SpringLayout.WEST, dateCreatedContainer, 5, SpringLayout.EAST, ratingLabel);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, dateCreatedContainer, 0, SpringLayout.VERTICAL_CENTER, dateCreatedLabel);
		
        bottomRightBtnContainer = new JPanel();
        bottomRightBtnContainer.setOpaque(false);
        add(bottomRightBtnContainer);
        layout.putConstraint(SpringLayout.SOUTH, bottomRightBtnContainer, -10, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, bottomRightBtnContainer, -10, SpringLayout.EAST, this);
        
        bottomLeftBtnContainer = new JPanel();
        bottomLeftBtnContainer.setOpaque(false);
        add(bottomLeftBtnContainer);
        layout.putConstraint(SpringLayout.SOUTH, bottomLeftBtnContainer, -10, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, bottomLeftBtnContainer, 10, SpringLayout.WEST, this);
	}
	
	protected void initScrollPane() {
		notesContainer = new JPanel();
		
		//notesContainer.setOpaque(false);
		//notesContainer.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
		scrollPane = new JScrollPane(notesContainer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBar(scrollBar);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		//scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		notesContainer.setLayout(new BoxLayout(notesContainer, BoxLayout.Y_AXIS));
	    add(scrollPane);
	    layout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, dateCreatedLabel);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.NORTH, bottomRightBtnContainer);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -20 - getShadowGap(), SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, this);
        
        //prep strickthrough font 
        Map fontAttr = new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20).getAttributes();
		fontAttr.put (TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		strikethrough = new Font(fontAttr);
	}
	
	protected void loadTicket(TicketCard ticketCard) {
		subjectText.setVisible(true);
		this.ticketCard = ticketCard;
		Ticket t = ticketCard.getTicket();
		Font regFont = new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20);
		JTextArea textArea = new JTextArea();
		for(String[] s: t.getNotesList()) {	
			String note = s[0];
			if(s[1].equals("0")) {
				textArea.setFont(regFont);
			}else {
				textArea.setFont(regFont);
			}
			textArea.setText(textArea.getText() + "\n\u2022 "+note);
			textArea.setLineWrap(true);
		}
		notesContainer.add(textArea);
		
	}
	
	protected boolean isValid(Ticket t) {
		if(!t.getSubject().matches("[a-zA-z1-9\\?\\. ]*")) {
			ProjectViewOverlayUI.getInstance().sendPopUpAlert("Subject uses invalid characters");
			return false;
		}
		if(t.getSubject().length() > 50) {
			ProjectViewOverlayUI.getInstance().sendPopUpAlert("Subject is too long");
			return false;
		}
		return true;
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
            System.out.println("closed? idiots?");
        }
	}
	
	@Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setComposite(AlphaComposite.SrcIn);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width, height, radius, radius);                                             //paint background
        
        //graphics.setColor(Color.BLACK);
        //graphics.drawRoundRect(0, 0, width-1, height-1, radius, radius);  
    }
}
