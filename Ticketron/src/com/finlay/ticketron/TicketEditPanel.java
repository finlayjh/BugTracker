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
	private JTextField subjectTextField;
	private JComboBox<TicketType> typeComboBox;
	private JComboBox<TicketRating> ratingComboBox;
	private JLabel dateCreatedText;
	private JButton btnCancel;
	
	public TicketEditPanel() {
		frame = ProjectViewUI.getInstance().frame;
		Rectangle r = frame.getBounds();
		setLocation(r.width/2 - width/2, r.height/2 - height/2);
  
        typeComboBox = new JComboBox<>();
        typeComboBox.setModel(new DefaultComboBoxModel<>(TicketType.values()));
        typeComboBox.setFont(new Font("Arial", Font.PLAIN, 15)); 
        typeComboBox.setMaximumSize(typeComboBox.getPreferredSize());
        typeContainer.add(typeComboBox); 

        ratingComboBox = new JComboBox<>();
        ratingComboBox.setModel(new DefaultComboBoxModel<>(TicketRating.values()));
        ratingComboBox.setFont(new Font("Arial", Font.PLAIN, 15)); 
        ratingContainer.add(ratingComboBox); 
        
        dateCreatedText = new JLabel();
        dateCreatedText.setFont(new Font("Arial", Font.PLAIN, 15)); 
        dateCreatedText.setSize(getMaximumSize()); 
        dateCreatedContainer.add(dateCreatedText);
        
        JButton btnSave = new JButton("Save");
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
		subjectTextField.setText(t.getSubject());
		typeComboBox.setSelectedItem(t.getType());
		ratingComboBox.setSelectedItem(t.getRating());
		dateCreatedText.setText(t.getDateCreated().toString());
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        String action = e.getActionCommand();
        if (action.equals("Save")) {
        	Ticket ticket = ticketCard.getTicket();
        	Ticket t = new Ticket(ticket.getId(), subjectTextField.getText().toString(), (TicketType)typeComboBox.getSelectedItem(), (TicketRating)ratingComboBox.getSelectedItem(), "fix me bitch", ticket.getDateCreated(), ticket.getDateClosed(), ticket.getStatus(), ticket.getIsArchived());
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
