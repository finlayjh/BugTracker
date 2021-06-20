package com.finlay.ticketron;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ResourceLoader {
	
	private static ResourceLoader singleton = null;
	
	private GraphicsEnvironment ge;
	private BufferedImage imgYellowAlert;
	private BufferedImage imgRedAlert;
	
	private ResourceLoader() {
		loadFonts();
		loadImages();
	}
	
	protected static ResourceLoader getInstance() {
		if(singleton == null) {
			singleton = new ResourceLoader();
		}
		return singleton;
	}

	private void loadFonts(){
		ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		registerFont("JosefinSans-SemiBoldItalic");
		
	}
	
	private void registerFont(String name) {
		try {
			boolean isRegistered = false;
			String []fonts=ge.getAvailableFontFamilyNames();
	        for (int i = 0; i < fonts.length; i++) {
	            if(fonts[i].equals(name)){
	                System.out.println("font "+ name + " already registered");
	                isRegistered = true;
	            }
	        }
	        if(!isRegistered) {
	        	Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/Fonts/"+name+".ttf"));
				System.out.println("registering: " + name);
				ge.registerFont(font);
				Stylesheet.getInstance().addFont("default", font.getName());
	        }
		} catch (IOException|FontFormatException e) {
			System.out.println("Cant find " + name + ", replacing with Ariel");
			//Stylesheet.getInstance().addFont("default", "Ariel");
			e.printStackTrace();
		     //Handle exception
		}   
	}
	
	private void loadImages() {
		File dir = new File("Resources/Images");
        for (File file : dir.listFiles()) {
            System.out.println("File: " + file.getAbsolutePath());
            try { 
            	BufferedImage image = ImageIO.read(file);
            	Stylesheet.getInstance().addImage(file.getName().replace("64x64.png", ""), image);
  	       } catch (IOException ex) {
  	            // handle exception...
  	       }
        }
	}
}
