package com.app.anra.crauler.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	 * @param url
	 *            the url
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
	 * @param url
	 *            the url
	 * @param ruta_fich
	 *            the ruta_fich
	 * @return the html
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Document getHtml(String url, String ruta_fich) throws IOException {

		Document doc = Jsoup.connect(url).timeout(0).get();

		return doc;
	}

	/**
	 * Gets the html.
	 *
	 * @param url
	 *            the url
	 * @return the html
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Document getHtml(String url) throws IOException {

		Document doc = Jsoup.connect(url).timeout(0).get();

		return doc;
	}

	public static String cleanName(String name) {

		name = name.toLowerCase();
		String[] splitName = name.split(("\\s+"));
		String newName = name;

		if (splitName[splitName.length - 1].contains(".")) {
			newName = name.replace(" " + splitName[splitName.length - 1], "");
			if (newName.charAt(newName.length() - 1) == ',')
				newName = newName.substring(0, newName.length() - 1);
		}

		String[] stopwords = { "franquicia", "constructora", "ofertas", "oferta", "tienda", "tiendas", "tienda", " sl",
				" sau", " slu", "almacenes", "profesionales", "empleo", "trabajo", "temporal", "oficinas" };

		for (int i = 0; i < stopwords.length; i++) {
			if (newName.contains(stopwords[i]))
				newName = newName.replace(stopwords[i], "");
		}

		return newName.trim();
	}

	/*
	 * public static Integer setValoraciones(String val) { int returned = 0; if
	 * (val.equals("P+")) returned = 5; if (val.equals("P")) returned = 4; if
	 * (val.equals("NEU")) returned = 3; if (val.equals("N")) returned = 2; if
	 * (val.equals("N+")) returned = 1; return returned; }
	 */

	public static void main(String[] args) throws Exception {
		System.out.println(cleanName("Halcon Viajes Franquicia"));

		String fecha = "Hace 8 horas.";
		fecha = fecha.toLowerCase().replace("hace", "").replace("más de", "").trim();

		Calendar cal = Calendar.getInstance();
		int num = -Integer.parseInt(fecha.split("\\s+")[0].trim());

		if (fecha.contains("horas") || fecha.contains("hora")) {
			cal.add(Calendar.HOUR, num);

		} else if (fecha.contains("días") || fecha.contains("día")) {
			cal.add(Calendar.DATE, num);
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format1.format(cal.getTime());
		System.out.println(formatted);

	}

}
