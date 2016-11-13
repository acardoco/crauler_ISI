package com.app.anra.crauler.util;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Util {

	
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
	
}
