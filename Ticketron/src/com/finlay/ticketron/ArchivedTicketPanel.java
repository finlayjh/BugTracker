package com.finlay.ticketron;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class ArchivedTicketPanel extends BaseInteractablePanel{
	
	private static ArchivedTicketPanel archivedTicketPanelSingleton = null;
	
	private ArchivedTicketPanel() {
		super(false);
		Frame frame = ProjectViewUI.getInstance().frame;
		frame.setVisible(true);
		RoundedJPanel cardPanel = ProjectViewUI.getInstance().getKanbanPanel(TicketStatus.CLOSED).getCardPanel();
		setBounds(cardPanel.getLocationOnScreen().x, cardPanel.getLocationOnScreen().y -23, cardPanel.getWidth() - cardPanel.getShadowGap(), cardPanel.getHeight() - cardPanel.getShadowGap());
		frame.setVisible(false);
		
		
		setBackground(Color.decode("#fffff2"));
		setVisible(false);
	}
	
	protected static ArchivedTicketPanel getInstance() {
		if(archivedTicketPanelSingleton == null) {
			archivedTicketPanelSingleton = new ArchivedTicketPanel();
		}
		return archivedTicketPanelSingleton;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String action = e.getActionCommand();
		if (action.equals("Close")) {
			ProjectViewUI.getInstance().closeArchivedPanel();
        }
	}
}
