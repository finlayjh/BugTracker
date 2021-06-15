package com.finlay.ticketron;

import java.sql.SQLException;

public class ProjectViewController {
	
	ProjectViewUI gui;
	ProjectViewModel model;
	private static ProjectViewController singleton = null;
	
	private ProjectViewController() {
		
		gui = ProjectViewUI.getInstance();
		model = ProjectViewModel.getInstance();
		
		//get tickets info
		model.loadProject(); //and project types later
		
		//load tickets to GUI
		if(model.getTickets() != null) {
			gui.loadTickets(model.getTickets());
		}
		
		gui.showGUI();
		
	}
	
	protected static ProjectViewController getInstance() {
		if(singleton == null) {
			singleton = new ProjectViewController();
		}
		return singleton;
	}
	
	protected void updateTicket(Ticket t) throws SQLException {
		System.out.println("(controller)Updating Ticket...");
		model.updateTicket(t);
	}
	
	protected void deleteTicket(Ticket t) throws SQLException {
		model.deleteTicket(t);
	}
	
	protected void addTicket(Ticket t) {
		//add to sql
		try {
			model.insertTicket(t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
