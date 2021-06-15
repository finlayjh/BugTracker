package com.finlay.ticketron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class KanbanPanel extends JPanel {
	
	private String title;
	private TicketStatus statusCatagory;
	private JLabel titleLabel;
	private JPanel panel;
	private BaseInteractablePanel cardPanel;
	
	KanbanPanel(String title, TicketStatus statusCatagory){
		// title
		this.title = title;
		this.statusCatagory = statusCatagory;
		setSize(getMaximumSize());
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(Stylesheet.getInstance().font("default"), Font.ITALIC, 50));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBorder(new EmptyBorder(20,10,40,10));
		add(titleLabel);
		setOpaque(false);
		
		// card container
		cardPanel = new BaseInteractablePanel(true);
		cardPanel.setBackground(Color.decode("#fffff2"));
		cardPanel.setCloseButtonShowing(false);
		add(cardPanel);
		
		new DropTargetListImp(cardPanel);
		
		panel =  new DisabledPanel(this);
	}
	
	protected void setEnabled(Boolean b) {
		panel.setEnabled(b);
	}
	
	protected int getNumTickets() {
		return cardPanel.getNumTickets();
	}
	
	protected void removeTicketCard(TicketCard t){
		cardPanel.removeTicketCard(t);
	}
	
	protected void addTicketCard(TicketCard t) {
		cardPanel.addTicketCard(t);
	}
	
	protected RoundedJPanel getCardPanel() {
		return cardPanel;
	}
	
	class DropTargetListImp extends DropTargetAdapter implements DropTargetListener {
	
		private DropTarget dropTarget;
		private RoundedJPanel panel;
		
		public DropTargetListImp(JPanel panel) {
			this.panel = (RoundedJPanel) panel;
			dropTarget = new DropTarget(panel, DnDConstants.ACTION_MOVE, this, true, null);
		}
		
		public void dragEnter(DropTargetDragEvent dtde) {
			System.out.println("drag enter");
			if(ProjectViewOverlayUI.getInstance().isWIPFull() && statusCatagory.equals(TicketStatus.WIP)) {
				panel.isGlowing(true, Color.RED);
			}
			else {
				panel.isGlowing(true);
			}
			panel.repaint();
		}
		public void dragExit(DropTargetEvent dte) {
			System.out.println("drag exit");
			panel.isGlowing(false);
			panel.repaint();
		}
		
		public void drop(DropTargetDropEvent event) {
			System.out.println("droping...");
			try {
				panel.isGlowing(false);
				Transferable transferable = event.getTransferable();
				TicketCard ticketCard = (TicketCard) transferable.getTransferData(TicketCard.dataFlavor);
				
				if (event.isDataFlavorSupported(TicketCard.dataFlavor)) {
					event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					if(!ticketCard.getTicket().getStatus().equals(statusCatagory)) {
						ticketCard.setStatus(statusCatagory);
						ProjectViewUI.getInstance().moveTicketCard(ticketCard);					
					}
					event.dropComplete(true);
					repaint();
					return;
				}
				
				event.rejectDrop();
			} catch (UnsupportedFlavorException ufe) {
	            System.out.println("importData: unsupported data flavor");
	        } catch (Exception e) {
				e.printStackTrace();
				event.rejectDrop();
			}
		}
	}
}
