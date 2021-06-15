package com.finlay.ticketron;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

public class TicketCard extends JPanel implements Transferable, Serializable{
	
	private Ticket ticket;
	public ProjectViewUI mainUI;
	private JLabel subjectText;
	private int radius;
	private BufferedImage ratingIconImage;
	private Component optionalSpacing;
	transient static DataFlavor dataFlavor = new DataFlavor(Ticket.class, "TicketCard");

	public TicketCard(Ticket ticket) {
		
		radius = 20;
		
		
		mainUI = ProjectViewUI.getInstance();
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 150));
		setMinimumSize(new Dimension(500, 150));
		setMaximumSize(new Dimension(500, 150));
		//setBorder(new EmptyBorder(50,0,50,0));

		setOpaque(false);
		subjectText = new JLabel();	
		subjectText.setForeground(Color.BLACK);
		subjectText.setBorder(new EmptyBorder(0,20,0,0));
		subjectText.setVerticalAlignment(SwingConstants.CENTER);
		subjectText.setFont(new Font(Stylesheet.getInstance().font("default"), Font.PLAIN ,20));
		add(subjectText, BorderLayout.LINE_START);
		TicketCard tc = this;
		addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // do something
		    	mainUI.viewTicket(tc);
		    }
		});
		
		updateCard(ticket);
		
		DragSource source = new DragSource();
		source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, new DragGestureListImp());
	}

	public Ticket getTicket() {
		return ticket;
	}
	
	public void setStatus(TicketStatus status) {
		ticket.setStatus(status);
	}
	
	public void updateCard(Ticket t) {
		this.ticket = t;
		subjectText.setText(ticket.getSubject());
		switch(ticket.getRating()) {
		case MINOR:
			ratingIconImage = null;
			break;
		case IMPAIRED:
			ratingIconImage = Stylesheet.getInstance().image("yellow_alert");
			break;
		case CATASTROPHIC:
			ratingIconImage = Stylesheet.getInstance().image("red_alert");
			break;
		}
	}
	
	
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { dataFlavor };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(dataFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {

		if (flavor.equals(dataFlavor)) {
			System.out.println("packing flavor...");
			return this;
		}
		else
			throw new UnsupportedFlavorException(flavor);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(radius, radius);
        int shadowOffset = 8;
        int width = getWidth() - shadowOffset;
        int height = getHeight() - shadowOffset;
        Graphics2D graphics = (Graphics2D) g;
        graphics.setComposite(AlphaComposite.SrcIn);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRoundRect(shadowOffset, shadowOffset, width, height, arcs.width, arcs.height);       //paint shadow
        graphics.setColor(Color.WHITE);
        graphics.fillRoundRect(0, 0, width, height, radius, radius);                                      //paint background
        
        //top stripe
        switch(ticket.getStatus().toString()) {
        case "OPENED":
        	graphics.setColor(Color.decode("#1771de"));
        	break;
        case "WIP":
        	graphics.setColor(Color.decode("#ff5e00"));
        	break;
        case "CLOSED":
        	graphics.setColor(Color.decode("#4b8b05"));
        	break;
        }
        Area a1 = new Area(new Rectangle2D.Float(0, 0, width, 20));                                 // top stripe
        Area a2 = new Area(new RoundRectangle2D.Float(0, 0, width, height, radius, radius));
        a1.intersect(a2);
        graphics.fill(a1);
        
        graphics.setColor(Color.BLACK);
        graphics.drawRoundRect(0, 0, width, height, radius, radius);    
        
        if(ratingIconImage!=null) {
        	graphics.drawImage(ratingIconImage, width - 30, 20+5, 25, 25, Color.WHITE, this);                                                      // add rating alert icon
        }
	}

	public Component getOptionalSpacing() {
		return optionalSpacing;
	}

	public void setOptionalSpacing(Component optionalSpacing) {
		this.optionalSpacing = optionalSpacing;
	}

	class DragGestureListImp implements DragGestureListener {

		@Override
		public void dragGestureRecognized(DragGestureEvent event) {
			Cursor cursor = null;
			TicketCard ticketCard = (TicketCard) event.getComponent();
			System.out.println("ticket?: " + ticketCard.getTicket().getSubject());

			if (event.getDragAction() == DnDConstants.ACTION_MOVE) {
				System.out.println("mouse stuff...");
				cursor = DragSource.DefaultMoveDrop;
			}
			try {
				event.startDrag(cursor, (Transferable) ticketCard.getTransferData(dataFlavor));
			} catch (InvalidDnDOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
