package com.finlay.ticketron;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class ProjectViewModel {
	
	private ArrayList<Ticket> tickets;
	private Connection myConn = null;
	private static ProjectViewModel singleton = null;
	
	protected static ProjectViewModel getInstance() {
		if(singleton == null) {
			singleton = new ProjectViewModel();
		}
		return singleton;
	}
	
	private ProjectViewModel() {
		// :)
	}
	
	protected void loadProject() {
		tickets = new ArrayList<Ticket>();
		openConnection();
		try {
			// read tickets from sql into arraylist
			Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM tickets");
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			int id;
			String subject;
			TicketType type;
			TicketRating rating;
			String notes;
			Date created;
			Date closed;
			TicketStatus status;
			boolean isArchived;
			
			while (resultSet.next()) {
				id = resultSet.getInt(1);
				subject = resultSet.getString(2);                                  //untested code for loading tickets enum probaly an error here
				try {
					type = TicketType.valueOf(resultSet.getString(3));
				} catch(IllegalArgumentException e) {
					type = TicketType.UI;
				}
				try {
					rating = TicketRating.valueOf(resultSet.getString(4));
				} catch(IllegalArgumentException e) {
					rating = TicketRating.MINOR;
				}
				notes = resultSet.getString(5);
				created = resultSet.getDate(6);
				closed = resultSet.getDate(7);
				try {
					status = TicketStatus.valueOf(resultSet.getString(8));
				} catch(IllegalArgumentException e) {
					status = TicketStatus.OPENED;
				}
				try {
					isArchived = resultSet.getBoolean(9);
				} catch(IllegalArgumentException e) {
					isArchived = false;
				}
				isArchived = resultSet.getBoolean(9);
				tickets.add(new Ticket(id, subject, type, rating, notes, created, closed, status, isArchived));
			}
			//close connection
			closeConnection();
			System.out.println("Tickets loaded...");
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	protected ArrayList<Ticket> getTickets(){
		return tickets;
	}
	
	protected void updateTicket(Ticket t) throws SQLException {
		openConnection();
		System.out.println("updating...");
		int isA = (t.getIsArchived()) ? 1 : 0;             //converting boolean to tinyint
		String query = " UPDATE tickets"
		        + " SET subject = \"" + t.getSubject() + "\", status = \"" + t.getStatus().toString() + "\", rating = \"" + t.getRating().toString() + "\", isArchived = \"" + isA
		        		+ "\" WHERE id_ticket = \"" + t.getId() + "\"";
		java.sql.PreparedStatement preparedStmt = myConn.prepareStatement(query);
		preparedStmt.execute();
		closeConnection();
		System.out.println("done...");
	}
	
	protected void deleteTicket(Ticket t) throws SQLException {
		openConnection();
		System.out.println("deleting...");
		String query = " DELETE FROM tickets"
		        		+ " WHERE id_ticket = \"" + t.getId() + "\"";
		java.sql.PreparedStatement preparedStmt = myConn.prepareStatement(query);
		preparedStmt.execute();
		closeConnection();
		System.out.println("done...");
	}
	
	protected void insertTicket(Ticket ticket) throws SQLException {
		openConnection();
		System.out.println("in model...");
		String query = " INSERT INTO tickets (subject, notes, rating, type, date_created, status)"
		        + " VALUES (?, ?, ?, ?, ?, ?)";
		
		java.sql.PreparedStatement preparedStmt = myConn.prepareStatement(query);
		preparedStmt.setString (1, ticket.getSubject());
		preparedStmt.setString (2, ticket.getNotes());
		preparedStmt.setString (3, ticket.getRating().toString());
		preparedStmt.setString (4, ticket.getType().toString());
		preparedStmt.setDate   (5, ticket.getDateCreated());
		preparedStmt.setString (6, ticket.getStatus().toString());
		  
		preparedStmt.execute();
		closeConnection();
		System.out.println("done...");
	}
	
	protected void openConnection() {
		System.out.println("Connecting...");
		try {
			//connect to db
			String url = "jdbc:mysql://localhost:3306/programming_ticket_db";
		    Properties info = new Properties();
		    info.put("user", "testuser");
		    info.put("password", "testpass");
			myConn = DriverManager.getConnection(url, info);
			if(myConn != null) {
				System.out.println("Connected to db...");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("An error occurred while connecting MySQL database");
			e.printStackTrace();
		}
	}
	
	protected void closeConnection() {
		try {
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
