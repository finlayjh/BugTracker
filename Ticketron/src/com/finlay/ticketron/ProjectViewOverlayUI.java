package com.finlay.ticketron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ProjectViewOverlayUI extends JPanel implements MouseListener, ActionListener{
	
	private static ProjectViewOverlayUI projectViewOverlayUISingleton = null;
	
	private Frame frame;
	private CircleJButton addTicketButton;
	private CircleJButton archivedButton;
	private FadingLabel popAlertLabel;
	
	//center panel counter
	private RoundedJPanel limitCountPanel;
	private JLabel limitCountLabel;
	private int limitCount = 0;
	private int limitCountMax = 3;
	
	private ProjectViewOverlayUI(){
		frame = ProjectViewUI.getInstance().frame;
		Rectangle r = frame.getBounds();
		setLayout(null);
		setBounds(0,0, r.width, r.height);
		setOpaque(false);
		frame.setVisible(true);   //need to show to get real sizes and location i think??
		
		initLimitCounter();
		
		initNewTicketButton();
		
		initArchivedButton();
		
		initPopAlert();
		
		frame.setVisible(false);
	}
	
	protected void initLimitCounter() {
		int width = 100;
		int height = 70;
		JPanel mPanel = ProjectViewUI.getInstance().getKanbanPanel(TicketStatus.WIP).getCardPanel();
		int x = mPanel.getLocationOnScreen().x + mPanel.getWidth()/2 - width/2;
		int y = mPanel.getLocationOnScreen().y - height/2 - 23;                                                      //23 is menu bar height fix this later please :)
		System.out.println(y);                       
		limitCountPanel = new RoundedJPanel(false);
		limitCountPanel.setLayout(new BorderLayout());
		limitCountPanel.setOpaque(false);
		limitCountPanel.setBackground(Color.decode("#d62d20"));
		limitCountPanel.setBounds(x, y, width, height);	
		limitCount = mPanel.getComponentCount();
		limitCountLabel = new JLabel(limitCount + "/" + limitCountMax, SwingConstants.CENTER);
		limitCountLabel.setFont(new Font(Stylesheet.getInstance().font("default"), Font.PLAIN ,40));
		limitCountPanel.add(limitCountLabel, BorderLayout.CENTER);
		add(limitCountPanel);
	}	
	
	protected void initNewTicketButton() {
		JPanel lPanel = ProjectViewUI.getInstance().getKanbanPanel(TicketStatus.OPENED).getCardPanel();
		int x = lPanel.getLocationOnScreen().x;
		int y = lPanel.getLocationOnScreen().y + lPanel.getHeight() - 23 - 8;                           //23 is menu bar height fix this later please :)
			
		addTicketButton = new CircleJButton("+", false);
		addTicketButton.setCenterLocation(new Point(x,y));
		addTicketButton.addActionListener(this);
		addTicketButton.setFont(new Font("Arial", Font.PLAIN ,100));
		addTicketButton.setActionCommand("new ticket");
		addTicketButton.setOpaque(false);
		addTicketButton.setBackground(Color.decode("#4b8b05"));
		addTicketButton.setContentAreaFilled(false);
		addTicketButton.setFocusPainted(false);
		addTicketButton.setFocusable(false);
		add(addTicketButton);
	}
	
	protected void initArchivedButton() {
		JPanel lPanel = ProjectViewUI.getInstance().getKanbanPanel(TicketStatus.CLOSED).getCardPanel();
		int x = lPanel.getLocationOnScreen().x + lPanel.getWidth() - 8;
		int y = lPanel.getLocationOnScreen().y - 23 ; 												//23 is menu bar height fix this later please :)
		
			
		ArrayList<Ticket> l = ProjectViewUI.getInstance().getArchivedTickets();
		System.out.println("init archived button: " + l.size());
		
		archivedButton = new CircleJButton(String.valueOf(l.size()), false);
		archivedButton.setCenterLocation(new Point(x,y));
		archivedButton.addActionListener(this);
		archivedButton.setFont(new Font("Arial", Font.PLAIN ,50));
		archivedButton.setActionCommand("archived");
		archivedButton.setOpaque(false);
		archivedButton.setBackground(Color.decode("#4b8b05"));
		archivedButton.setContentAreaFilled(false);
		archivedButton.setFocusPainted(false);
		archivedButton.setFocusable(false);
		add(archivedButton);
	}
	
	protected void initPopAlert() {
		int x = frame.getWidth()/2;
		int y = frame.getHeight()/4*3;
		
		popAlertLabel = new FadingLabel();
		popAlertLabel.setBounds(x, y, 500, 200);
		add(popAlertLabel);
	}
	
	protected static ProjectViewOverlayUI getInstance() {
		if(projectViewOverlayUISingleton == null) {
			System.out.println("kshhhhh, we in baby");
			projectViewOverlayUISingleton = new ProjectViewOverlayUI();
		}
		return projectViewOverlayUISingleton;
	}
	
	protected boolean updateLimitCounter(int newCount) {
		if(newCount <= limitCountMax) {
			limitCount = newCount;
			limitCountLabel.setText(limitCount + "/" + limitCountMax);
			return true;
		}
		return false;
	}
	
	protected void updateArchivedButton() {
		int size = ProjectViewUI.getInstance().getArchivedTickets().size();
		String sizeString = String.valueOf(size);
		if(size>99) {
			sizeString = "99+";
		}
		archivedButton.setText(sizeString);
	}
	
	protected boolean isWIPFull() {
		if(limitCount >= limitCountMax) {
			System.out.println("full");
			return true;
		}
		System.out.println("has room...");
		return false;
	}
	
	protected void sendPopUpAlert(String text) {
		popAlertLabel.popAndFade(text);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
        if (action.equals("new ticket")) {
        	ProjectViewUI.getInstance().newTicketUI();
        }
        else if (action.equals("archived")) {
        	//ProjectViewUI.getInstance().newTicketUI();
        	ProjectViewUI.getInstance().viewArchivedTickets();
        }
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
