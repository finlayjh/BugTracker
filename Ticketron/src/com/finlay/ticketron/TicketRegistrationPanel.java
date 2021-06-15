package com.finlay.ticketron;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.sql.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TicketRegistrationPanel extends BaseTicketInfoPanel implements ActionListener{
	
	private Frame frame;
	private JTextField subjectTextField;
	private JComboBox<TicketType> typeComboBox;
	private JComboBox<TicketRating> ratingComboBox;
	private JLabel dateCreatedText;
	private JButton btnCreate;
	
	public TicketRegistrationPanel() {
		frame = ProjectViewUI.getInstance().frame;
		Rectangle r = frame.getBounds();
		setLocation(r.width/2 - width/2, r.height/2 - height/2);	
  
        subjectTextField = new JTextField(); 
        layout.putConstraint(SpringLayout.WEST, subjectTextField, 5, SpringLayout.EAST, subjectLabel);
        layout.putConstraint(SpringLayout.NORTH, subjectTextField, 0, SpringLayout.NORTH, subjectLabel);
        layout.putConstraint(SpringLayout.EAST, subjectTextField, -40, SpringLayout.EAST, this);
        subjectTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
        add(subjectTextField);
        
        layout.putConstraint(SpringLayout.NORTH, typeLabel, 20, SpringLayout.SOUTH, subjectTextField);
  
        typeComboBox = new JComboBox<>();
        typeComboBox.setModel(new DefaultComboBoxModel<>(TicketType.values()));
        typeComboBox.setFont(new Font("Arial", Font.PLAIN, 15)); 
        typeComboBox.setMaximumSize(typeComboBox.getPreferredSize());
        typeContainer.add(typeComboBox); 
  
        ratingComboBox = new JComboBox<>();
        ratingComboBox.setModel(new DefaultComboBoxModel<>(TicketRating.values()));
        ratingComboBox.setFont(new Font("Arial", Font.PLAIN, 15)); 
        ratingComboBox.setMaximumSize(ratingComboBox.getPreferredSize()); 
        ratingContainer.add(ratingComboBox); 
        
        dateCreatedText = new JLabel();
        dateCreatedText.setFont(new Font("Arial", Font.PLAIN, 15)); 
        dateCreatedText.setSize(getMaximumSize()); 
        dateCreatedContainer.add(dateCreatedText);
        
        btnCreate = new JButton("Create");
        btnCreate.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnCreate.setSize(150, 20); 
        btnCreate.addActionListener(this);
        btnCreate.setActionCommand("Save");
        bottomRightBtnContainer.add(btnCreate);  
        
        setVisible(false);
	}	
	
	private void clearUI() {
		subjectTextField.setText(null);
		typeComboBox.setSelectedIndex(0);
		ratingComboBox.setSelectedIndex(0);
	}
	
	@Override 
	public void setVisible(boolean visible) {
		if(visible) {
			clearUI();
			subjectText.setVisible(false);
			dateCreatedText.setText(new java.sql.Date(System.currentTimeMillis()).toString());
		}
		super.setVisible(visible);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        String action = e.getActionCommand();
        if (action.equals("Save")) {
        	Ticket t = new Ticket(subjectTextField.getText().toString(), (TicketType)typeComboBox.getSelectedItem(), (TicketRating)ratingComboBox.getSelectedItem(), "fix me bitch");
        	if(isValid(t)) {
        		ProjectViewUI.getInstance().addTicket(t);
                System.out.println("saved idiots");
        	}
        }
        else if (action.equals("Close")) {
        	ProjectViewUI.getInstance().addTicket(null);
            System.out.println("both bitch");
        }
    }	
}
