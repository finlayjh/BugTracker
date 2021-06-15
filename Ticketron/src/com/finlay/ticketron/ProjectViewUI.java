package com.finlay.ticketron;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class ProjectViewUI extends BaseUI implements MouseListener{
	
	private static ProjectViewUI projectViewUISingleton = null;
	
	private JPanel gridPanel;
	private TicketRegistrationPanel ticketRegistrationPanel;
	private TicketViewPanel ticketViewPanel;
	private TicketEditPanel ticketEditPanel;
	private ArchivedTicketPanel archivedTicketPanel;
	private KanbanPanel leftPanel;
	private KanbanPanel middlePanel;
	private KanbanPanel rightPanel;
	private JLayeredPane layeredPane = null;
	private ProjectViewOverlayUI overlayUI;
	private TintedGlassPane tintedWindow;
	
	private ArrayList<Ticket> archivedTickets = new ArrayList<Ticket>();
	
	
	private ProjectViewUI() {
		//------constructing main panel------
		frame.setLayout(new BorderLayout());

		//-----constructing 3 column center------
		layeredPane = new JLayeredPane();
		gridPanel = new JPanel();
		gridPanel.setBackground(Stylesheet.getInstance().color("soft_blue"));
		gridPanel.setLayout(new GridLayout(1,3, 40, 30));
		gridPanel.setBorder(new EmptyBorder(20,40,70,32));     //right side is -8 for 8 width shadows
		Rectangle r = frame.getBounds();
		gridPanel.setBounds(0,0, r.width, r.height);
		layeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
		frame.add(layeredPane, BorderLayout.CENTER);
		
		//window pane?
		tintedWindow = new TintedGlassPane(Stylesheet.getInstance().color("grey_blue"));
		tintedWindow.setBounds(0,0,r.width, r.height);
		tintedWindow.setOpaque(false);
		tintedWindow.addMouseListener(new MouseListener() {


		    public void mouseClicked(MouseEvent e) {
		    }

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			} 
		});
		tintedWindow.setVisible(false);
		layeredPane.add(tintedWindow, JLayeredPane.MODAL_LAYER);
	}
	
	protected void initUI() {
		
				
			//Left panel
			leftPanel =  new KanbanPanel("To Do", TicketStatus.OPENED );
			gridPanel.add(leftPanel);
			
			//middle panel
			middlePanel =  new KanbanPanel("In Progress", TicketStatus.WIP);
			gridPanel.add(middlePanel);

			//right panel
			rightPanel =  new KanbanPanel("Completed", TicketStatus.CLOSED);
			gridPanel.add(rightPanel);
			
			//overlay ui
			overlayUI = ProjectViewOverlayUI.getInstance();
			layeredPane.add(overlayUI, JLayeredPane.PALETTE_LAYER);
			
			//popup layer preload popups?
			ticketRegistrationPanel = new TicketRegistrationPanel();
			layeredPane.add(ticketRegistrationPanel, JLayeredPane.POPUP_LAYER);
			ticketViewPanel = new TicketViewPanel();
			layeredPane.add(ticketViewPanel, JLayeredPane.POPUP_LAYER);
			ticketEditPanel = new TicketEditPanel();
			layeredPane.add(ticketEditPanel, JLayeredPane.POPUP_LAYER);
			archivedTicketPanel = ArchivedTicketPanel.getInstance();
			layeredPane.add(archivedTicketPanel, JLayeredPane.POPUP_LAYER);
	}
	
	protected static ProjectViewUI getInstance() {
		if(projectViewUISingleton == null) {
			System.out.println("kshhhhh, we in baby");
			projectViewUISingleton = new ProjectViewUI();
			projectViewUISingleton.initUI();
		}
		return projectViewUISingleton;
	}
		
	protected void loadTickets(ArrayList<Ticket> tickets) {
		for(Ticket t: tickets) {
			if(!t.getIsArchived()) {
				TicketCard tb = new TicketCard(t);
				switch(t.getStatus().toString()) {
					case "OPENED":
						leftPanel.addTicketCard(tb);
						break;
					case "WIP":
						middlePanel.addTicketCard(tb);
						break;
					case "CLOSED":
						rightPanel.addTicketCard(tb);
						break;
				}
			}else {
				System.out.println("archiving...");
				archivedTickets.add(t);
			}
		}
		overlayUI.updateLimitCounter(middlePanel.getNumTickets());
		overlayUI.updateArchivedButton();
		gridPanel.revalidate();
	}
	
	protected void viewArchivedTickets() {
		archivedTicketPanel.loadTickets(archivedTickets);
		archivedTicketPanel.setVisible(true);
		tintedWindow.setVisible(true);
	}
	
	protected void viewTicket(TicketCard t) {
		ticketViewPanel.loadTicket(t);
		ticketViewPanel.setVisible(true);
		tintedWindow.setVisible(true);
	}
	
	protected void closeTicketView(TicketCard t, Boolean update) throws SQLException {	
		ticketViewPanel.setVisible(false);
		ticketEditPanel.setVisible(false);
		if (update) {                                    // returns to normal layout
			moveTicketCard(t);
			if(!archivedTicketPanel.isVisible()) {
				tintedWindow.setVisible(false);
			}	
		}
		else if(t != null){                             // switching to edit panel
			ticketEditPanel.loadTicket(t);
			ticketEditPanel.setVisible(true);
		}
		if(t == null) {
			if(!archivedTicketPanel.isVisible()) {
				tintedWindow.setVisible(false);
			}
		}
		
	}
	
	protected void closeArchivedPanel() {
		archivedTicketPanel.setVisible(false);
		tintedWindow.setVisible(false);
	}
	
	protected void moveTicketCard(TicketCard t) throws SQLException {
		System.out.println("moving ticket...");
		leftPanel.removeTicketCard(t);
		middlePanel.removeTicketCard(t);
		rightPanel.removeTicketCard(t);
		overlayUI.updateLimitCounter(middlePanel.getNumTickets());
		if(overlayUI.isWIPFull() && t.getTicket().getStatus().equals(TicketStatus.WIP)) {
			overlayUI.sendPopUpAlert("Too Many Tickets In Progress");
			t.getTicket().setStatus(TicketStatus.OPENED);
		}
		if(t.getTicket().getIsArchived()) {
			System.out.println("archiving...");
			archivedTickets.add(t.getTicket());
			System.out.println(archivedTickets.size());
			overlayUI.updateArchivedButton();
			//add to archived
		}else {
			switch(t.getTicket().getStatus()) {
				case OPENED:
					leftPanel.addTicketCard(t);
					break;
				case WIP:
					middlePanel.addTicketCard(t);
					break;
				case CLOSED:
					rightPanel.addTicketCard(t);
					break;
				default:
					break;
			}
		}
		editTicket(t.getTicket());
	}
	
	protected void editTicket(Ticket t) throws SQLException {
		System.out.println(t.getSubject());
		if(t.getStatus() == TicketStatus.TOBEDELETED) {
			System.out.println("deleting...");
			ProjectViewController.getInstance().deleteTicket(t);
		}
		overlayUI.updateLimitCounter(middlePanel.getNumTickets());
		System.out.println("updating...");
		ProjectViewController.getInstance().updateTicket(t);
	}
	
	protected void addTicket(Ticket t) {
		System.out.println("(in UI)Adding Ticket...");
		if(t != null) {
			//add to sql
			ProjectViewController.getInstance().addTicket(t);
			//update appropriate panel
			leftPanel.addTicketCard(new TicketCard(t));
		}
		ticketRegistrationPanel.setVisible(false);
		tintedWindow.setVisible(false);
	}
	
	protected void newTicketUI(){
		ticketRegistrationPanel.setVisible(true);
		tintedWindow.setVisible(true);
	}
	
	public void showGUI() {
		frame.setVisible(true);
	}
	
	public KanbanPanel getKanbanPanel(TicketStatus status) {
		switch(status) {
		case WIP:
			return middlePanel;
		case OPENED:
			return leftPanel;
		case CLOSED:
			return rightPanel;
		default:
			return null;
		}
		
	}
	
	protected ArrayList<Ticket> getArchivedTickets(){
		return archivedTickets;
	}
	
	public void mouseClicked(MouseEvent me){
		  //call pop up
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
