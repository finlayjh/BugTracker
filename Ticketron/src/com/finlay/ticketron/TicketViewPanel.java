package com.finlay.ticketron;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class TicketViewPanel extends BaseTicketInfoPanel {
	
	private Frame frame;
	private JLabel lblType;
	private JLabel lblRating;
	private JLabel lblDateCreated;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnArchive;
	private JButton btnResurect;
	
	public TicketViewPanel() {
		frame = ProjectViewUI.getInstance().frame;
		Rectangle r = frame.getBounds();
		setLocation(r.width/2 - width/2, r.height/2 - height/2);
        
		
		
        lblType = new JLabel();
        lblType.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
        lblType.setSize(getMaximumSize()); 
        typeContainer.add(lblType);  
  
        lblRating = new JLabel();
        lblRating.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
        lblRating.setSize(getMaximumSize()); 
        ratingContainer.add(lblRating);
        
        lblDateCreated = new JLabel();
        lblDateCreated.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 20)); 
        lblDateCreated.setSize(getMaximumSize()); 
        dateCreatedContainer.add(lblDateCreated);
        
        btnEdit = new RoundedJButton("Edit", false);
        btnEdit.setBackground(Color.BLUE);
        btnEdit.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnEdit.setContentAreaFilled(false);
        btnEdit.setFocusPainted(false);
        btnEdit.addActionListener(this);
        btnEdit.setActionCommand("Edit");
        bottomRightBtnContainer.add(btnEdit);   
        
        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnDelete.setSize(150, 20); 
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String[] options = {"Yes! Delete.", "Cancel"}; 
               int result = JOptionPane.showOptionDialog(
                  frame,
                  "Delete this ticket?", 
                  "Delete Confirmation",            
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,     //no custom icon
                  options,  //button titles
                  options[0] //default button
               );
               if(result == JOptionPane.YES_OPTION){
            	   ticketCard.getTicket().closeTicket();
		           try {
		        	   ProjectViewUI.getInstance().closeTicketView(ticketCard, true);
		   		   } catch (SQLException e1) {
		   				e1.printStackTrace();
		   		   }
               }
            }
        });
        btnDelete.setActionCommand("Delete");
        bottomLeftBtnContainer.add(btnDelete);
        
        btnArchive = new JButton("Archive");
        btnArchive.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnArchive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String[] options = {"Yes! Archive.", "Cancel"}; 
               int result = JOptionPane.showOptionDialog(
                  frame,
                  "Archive this ticket?", 
                  "Archive Confirmation",            
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,     //no custom icon
                  options,  //button titles
                  options[0] //default button
               );
               if(result == JOptionPane.YES_OPTION){
            	   ticketCard.getTicket().setIsArchived(true);
		           try {
		        	   ProjectViewUI.getInstance().closeTicketView(ticketCard, true);
		   		   } catch (SQLException e1) {
		   				e1.printStackTrace();
		   		   }
               }
            }
        });
        btnArchive.setActionCommand("Archive");
        bottomLeftBtnContainer.add(btnArchive);  
        btnArchive.setVisible(false);
        
        btnResurect = new JButton("Resurect");
        btnResurect.setFont(new Font("Arial", Font.PLAIN, 15)); 
        btnResurect.setSize(150, 20); 
        btnResurect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String[] options = {"Yes! Resurect.", "Cancel"}; 
               int result = JOptionPane.showOptionDialog(
                  frame,
                  "Resurect this ticket?", 
                  "Resurect Confirmation",            
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,     //no custom icon
                  options,  //button titles
                  options[0] //default button
               );
               if(result == JOptionPane.YES_OPTION){
            	   ticketCard.getTicket().setIsArchived(false);
		           try {
		        	   ProjectViewUI.getInstance().closeTicketView(ticketCard, true);
		   		   } catch (SQLException e1) {
		   				e1.printStackTrace();
		   		   }
               }
            }
        });
        btnResurect.setActionCommand("Resurect");
        bottomLeftBtnContainer.add(btnResurect);
        btnResurect.setVisible(false);
        
        setVisible(false);
	}
	
	@Override
	protected void loadTicket(TicketCard ticketCard) {
		
		Ticket t = ticketCard.getTicket();
		//TESTCODE
		t.addNote("test note 69");
		t.addNote("test note 2");
		//t.addNote("test note 3");
		
		super.loadTicket(ticketCard);
		
		
		txtSubject.setText(t.getSubject());
		txtSubject.setRows((int) Math.ceil(t.getSubject().length()/27));
		lblType.setText(t.getType().toString());
		lblRating.setText(t.getRating().toString());
		lblDateCreated.setText(t.getDateCreated().toString());

		if(t.getStatus() == TicketStatus.CLOSED) {
			if(t.getIsArchived()) {
				btnResurect.setVisible(true);
				btnArchive.setVisible(false);
				btnDelete.setVisible(false);
				btnEdit.setVisible(false);
			}else {
				btnResurect.setVisible(false);
				btnArchive.setVisible(true);
				btnDelete.setVisible(false);
				btnEdit.setVisible(true);
			}			
		}else {
			btnResurect.setVisible(false);
			btnArchive.setVisible(false);
			btnDelete.setVisible(true);
			btnEdit.setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String action = e.getActionCommand();
		
		if (action.equals("Edit")) {
			try {
				ProjectViewUI.getInstance().closeTicketView(ticketCard, false);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}

}
