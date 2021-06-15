package com.finlay.ticketron;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.HashMap;

public class Stylesheet {

	private static Stylesheet singleton = null;
	
	private Font defaultFont;
	private HashMap<String, String> fontDict = new HashMap<>();
	private HashMap<String, Color> colorDict = new HashMap<>();
	private HashMap<String, BufferedImage> imageDict = new HashMap<>();
	
	private Stylesheet() {
		loadColors();
	}
	
	protected static Stylesheet getInstance() {
		if(singleton == null) {
			singleton = new Stylesheet();
		}
		return singleton;
	}
	
	protected void addFont(String nameTag, String fontName) {
		fontDict.put(nameTag, fontName);
	}
	
	protected String font(String fontName) {
		return fontDict.get(fontName);
	}
	
	protected void addColor(String nameTag, Color color) {
		colorDict.put(nameTag, color);
	}
	
	protected Color color(String name) {
		return colorDict.get(name);
	}
	
	protected void addImage(String nameTag, BufferedImage image) {
		System.out.println(nameTag);
		imageDict.put(nameTag, image);
	}
	
	protected BufferedImage image(String name) {
		return imageDict.get(name);
	}
	
	private void loadColors() {
		addColor("soft_blue", Color.decode("#3282b8"));
		addColor("grey_blue", Color.decode("#404040"));
	}
}
