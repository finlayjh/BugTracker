package com.finlay.ticketron;
import java.time.*;
import java.util.ArrayList;
import java.sql.Date;

public class Ticket {
	//user defined
	private String subject;
	private TicketType type;
	private TicketRating rating;
	private String notes;
	private ArrayList<String[]> notesList = new ArrayList<String[]>();
	
	//auto defined
	private Date dateCreated;
	private Date dateClosed = null;
	private TicketStatus status;
	private int id;
	private boolean isArchived;
	
	protected Ticket(String subject, TicketType type, TicketRating rating, String notes) {
		this.dateCreated = new java.sql.Date(System.currentTimeMillis());
		this.subject = subject;
		this.type = type;
		this.rating = rating;
		this.notes = notes;
		this.status = TicketStatus.OPENED;
		//save ticket
	}
	
	protected Ticket(int id, String subject, TicketType type, TicketRating rating, String notes, Date dateCreated, Date dateClosed, TicketStatus status, boolean isArchived) {
		this.id = id;
		this.subject = subject;
		this.type = type;
		this.rating = rating;
		this.notes = notes;
		this.dateCreated = dateCreated;
		this.dateClosed = dateClosed;
		this.status = status;
		this.isArchived = isArchived;
		
	}
	
	protected void closeTicket() {
		this.dateClosed = new java.sql.Date(System.currentTimeMillis());
		this.status = TicketStatus.TOBEDELETED;
		//close ticket
	}

	protected void addNote(String note) {
		String[] n = {note, "0"};
		notesList.add(n);
		System.out.println(notesList.size());
	}
	
	protected ArrayList<String[]> getNotesList(){
		return notesList;
	}
	
	//getters
	public String getSubject() {
		return subject;
	}

	public String getNotes() {
		return notes;
	}

	public TicketType getType() {
		return type;
	}

	public TicketRating getRating() {
		return rating;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public TicketStatus getStatus() {
		return status;
	}
	
	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public Date getDateClosed() {
		return dateClosed;
	}
	
	public boolean getIsArchived() {
		return isArchived;
	}
	
	public void setIsArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

}


