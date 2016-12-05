package com.app.anra.crauler.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// TODO: Auto-generated Javadoc
/**
 * The Class Util.
 */
public class Util {

	/**
	 * Lector html.
	 *
	 * @param url the url
	 * @return the string
	 */
	/*
	 * Función auxiliar de apoyo para traerse todo el html de una página
	 */
	public static String lectorHtml(String url) {
		String content = null;
		URLConnection connection = null;
		try {
			connection = new URL(url).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
			scanner.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return content;
	}

	/**
	 * Gets the html.
	 *
	 * @param url the url
	 * @param ruta_fich the ruta_fich
	 * @return the html
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Document getHtml(String url, String ruta_fich) throws IOException {
		File input = new File(ruta_fich);
		Document doc = Jsoup.connect(url).timeout(0).get();

		return doc;
	}

	/**
	 * Gets the html.
	 *
	 * @param url the url
	 * @return the html
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Document getHtml(String url) throws IOException {

		Document doc = Jsoup.connect(url).timeout(0).get();

		return doc;
	}
	
	public static String cleanName(String name){
		
		String[] splitName = name.split(("\\s+"));
		String newName = name;
		
		if(splitName[splitName.length -1].contains("."))
		{
			newName = name.replace(" " + splitName[splitName.length -1], "");
			if(newName.charAt(newName.length()-1) == ',') 
				newName = newName.substring(0, newName.length()-1);
		}
			
		return newName;
	}

}
