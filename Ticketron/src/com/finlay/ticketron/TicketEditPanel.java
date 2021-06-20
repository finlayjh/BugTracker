package com.finlay.ticketron;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TicketEditPanel extends BaseTicketInfoPanel{
	
	private Frame frame;
	private JTextField txtSubject;
	private JComboBox<TicketType> cmbType;
	private JComboBox<TicketRating> cmbRating;
	private JLabel lblDateCreated;
	private JButton btnCancel;
	private JButton btnSave;
	
	public TicketEditPanel() {
		frame = ProjectViewUI.getInstance().frame;
		Rectangle r = frame.getBounds();
		setLocation(r.width/2 - width/2, r.height/2 - height/2);
  
        cmbType = new JComboBox<>();
        cmbType.setModel(new DefaultComboBoxModel<>(TicketType.values()));
        cmbType.setFont(new Font("Arial", Font.PLAIN, 15)); 
        cmbType.setMaximumSize(cmbType.getPreferredSize());
        typeContainer.add(cmbType); 

        cmbRating = new JComboBox<>();
        cmbRating.setModel(new DefaultComboBoxModel<>(TicketRating.values()));
        cmbRating.setFont(new Font("Arial", Font.PLAIN, 15)); 
        ratingContainer.add(cmbRating); 
        
        lblDateCreated = new JLabel();
        lblDateCreated.setFont(new Font("Arial", Font.PLAIN, 15)); 
        lblDateCreated.setSize(getMaximumSize()); 
        dateCreatedContainer.add(lblDateCreated);
        
        btnSave = new JButton("Save");
        btnSave.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnSave.setSize(150, 20); 
        btnSave.addActionListener(this);
        btnSave.setActionCommand("Save");
        bottomRightBtnContainer.add(btnSave); 
        
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnCancel.setSize(150, 20); 
        btnCancel.addActionListener(this);
        btnCancel.setActionCommand("Cancel");
        bottomLeftBtnContainer.add(btnCancel);
        
        setVisible(false);
	}
	
	protected void loadTicket(TicketCard ticketCard) {
		
		super.loadTicket(ticketCard);
		
		Ticket t = ticketCard.getTicket();
		txtSubject.setText(t.getSubject());
		cmbType.setSelectedItem(t.getType());
		cmbRating.setSelectedItem(t.getRating());
		lblDateCreated.setText(t.getDateCreated().toString());
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        String action = e.getActionCommand();
        if (action.equals("Save")) {
        	Ticket ticket = ticketCard.getTicket();
        	Ticket t = new Ticket(ticket.getId(), txtSubject.getText().toString(), (TicketType)cmbType.getSelectedItem(), (TicketRating)cmbRating.getSelectedItem(), "fix me bitch", ticket.getDateCreated(), ticket.getDateClosed(), ticket.getStatus(), ticket.getIsArchived());
        	if(isValid(t)) {
        		ticketCard.updateCard(t);
        		System.out.println(ticketCard.getTicket().getSubject());
        		try {
        			ProjectViewUI.getInstance().closeTicketView(ticketCard, true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        }
        else if(action.equals("Cancel")) {
        	try {
    			ProjectViewUI.getInstance().closeTicketView(null, false);
    			ProjectViewUI.getInstance().viewTicket(ticketCard);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
	
}
