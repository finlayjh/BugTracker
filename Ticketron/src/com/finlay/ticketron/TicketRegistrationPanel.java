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
	private JTextField txtSubject;
	private JComboBox<TicketType> cmbType;
	private JComboBox<TicketRating> cmbRating;
	private JLabel lblDateCreated;
	private JButton btnCreate;
	
	public TicketRegistrationPanel() {
		frame = ProjectViewUI.getInstance().frame;
		Rectangle r = frame.getBounds();
		setLocation(r.width/2 - width/2, r.height/2 - height/2);	
  
        txtSubject = new JTextField(); 
        layout.putConstraint(SpringLayout.WEST, txtSubject, 5, SpringLayout.EAST, lblSubject);
        layout.putConstraint(SpringLayout.NORTH, txtSubject, 0, SpringLayout.NORTH, lblSubject);
        layout.putConstraint(SpringLayout.EAST, txtSubject, -40, SpringLayout.EAST, this);
        txtSubject.setFont(new Font("Arial", Font.PLAIN, 15)); 
        add(txtSubject);
        
        layout.putConstraint(SpringLayout.NORTH, lblType, 20, SpringLayout.SOUTH, txtSubject);
  
        cmbType = new JComboBox<>();
        cmbType.setModel(new DefaultComboBoxModel<>(TicketType.values()));
        cmbType.setFont(new Font("Arial", Font.PLAIN, 15)); 
        cmbType.setMaximumSize(cmbType.getPreferredSize());
        typeContainer.add(cmbType); 
  
        cmbRating = new JComboBox<>();
        cmbRating.setModel(new DefaultComboBoxModel<>(TicketRating.values()));
        cmbRating.setFont(new Font("Arial", Font.PLAIN, 15)); 
        cmbRating.setMaximumSize(cmbRating.getPreferredSize()); 
        ratingContainer.add(cmbRating); 
        
        lblDateCreated = new JLabel();
        lblDateCreated.setFont(new Font("Arial", Font.PLAIN, 15)); 
        lblDateCreated.setSize(getMaximumSize()); 
        dateCreatedContainer.add(lblDateCreated);
        
        btnCreate = new JButton("Create");
        btnCreate.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnCreate.setSize(150, 20); 
        btnCreate.addActionListener(this);
        btnCreate.setActionCommand("Save");
        bottomRightBtnContainer.add(btnCreate);  
        
        setVisible(false);
	}	
	
	private void clearUI() {
		txtSubject.setText(null);
		cmbType.setSelectedIndex(0);
		cmbRating.setSelectedIndex(0);
	}
	
	@Override 
	public void setVisible(boolean visible) {
		if(visible) {
			clearUI();
			txtSubject.setVisible(false);
			lblDateCreated.setText(new java.sql.Date(System.currentTimeMillis()).toString());
		}
		super.setVisible(visible);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        String action = e.getActionCommand();
        if (action.equals("Save")) {
        	Ticket t = new Ticket(txtSubject.getText().toString(), (TicketType)cmbType.getSelectedItem(), (TicketRating)cmbRating.getSelectedItem(), "fix me bitch");
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
