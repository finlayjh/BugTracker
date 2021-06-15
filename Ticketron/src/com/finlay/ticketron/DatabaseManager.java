package com.finlay.ticketron;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class DatabaseManager {
	
	private Connection myConn = null;
	private ArrayList<Ticket> projectDatabase;
	
	protected void loadProject() {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/programming_ticket_db?user=testuser&password=testpass");
			if(myConn != null) {
				System.out.println("Connected to db...");
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	protected void insertTicket(Ticket ticket) throws SQLException {
		System.out.println("Adding Ticket...");
	
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
	}
	
	protected void updateTicket(Ticket ticket) {
		
	}
	
	protected void deleteTicket(Ticket ticket) {
		
	}
	
	protected void printSQL(String table) {
		try {
			Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM tickets");
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = resultSet.getString(i);
			        System.out.print(columnValue + " " + rsmd.getColumnName(i));
			    }
			    System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
