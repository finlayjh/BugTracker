package com.finlay.ticketron;

import java.util.Scanner;

import javax.swing.*;

import java.awt.*;
import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args) {
		
		ResourceLoader.getInstance();
		
		ProjectViewController controller = ProjectViewController.getInstance();
		
		//DatabaseManager dbManager = new DatabaseManager();
		//dbManager.loadProject();
		/*Ticket t = new Ticket("testsubject", TicketType.UI, TicketRating.BOOTLEGGED, "testnotes");
		try {
			dbManager.insertTicket(t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//dbManager.printSQL("tickets");
		//dbManager.closeConnection();
		//JFrame MyGUI = new TicketDisplayGUI();
		
	}

}
