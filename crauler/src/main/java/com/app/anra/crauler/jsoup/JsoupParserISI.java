package com.app.anra.crauler.jsoup;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// TODO: Auto-generated Javadoc
/**
 * The Class JsoupParserISI.
 */
/*
 * en obras...
 * 
 */
public class JsoupParserISI {

	/** The monster_url. */
	public static String monster_url = "http://www.monster.es/trabajo/buscar/?cy=ES";

	/** The infoempleo_url. */
	public static String infoempleo_url = "http://www.infoempleo.com/trabajo/";
	// SelectorGadget->extensión de chrome

	/**
	 * Gets the nombre oferta info empleo.
	 *
	 * @param url
	 *            the url
	 * @return the nombre oferta info empleo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getNombreOfertaInfoEmpleo(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();

		Elements lst = doc.select("h2 > a");
		StringBuffer out = new StringBuffer();
		for (Element elem : lst) {
			out.append(elem.text() + "\n");
		}
		return out.toString();
	}

	/**
	 * EN OBRAS-->CAMBIAR EL HASHMAP POR HASHSET (hay key que se repiten y claro, el Map las sobreescribe).
	 *
	 * @param url the url
	 * @return the nombre empresa and localizacion info empleo
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Map<String, String> getNombreEmpresaAndLocalizacionInfoEmpleo(String url) throws IOException {

		Map<String, String> nombreEmpresa_Localizacion = new HashMap<String, String>();

		Document doc = Jsoup.connect(url).get();

		Elements lst = doc.select(".block");
		int filtrador = 0;// cuando llegue a 2, reinicio. De esta manera, como
							// por cada oferta hay tres elementos "block" y el
							// ultimo no interesa(hace cuanto tiempo se puso la
							// oferta) incluyo en un HashMap los que nos
							// interesan
		String primerBlock_nombreEmpresa = "";
		for (Element elem : lst) {

			if (filtrador == 0) // Empezamos guardando el primer block, que es
								// el nombre de la empresa
				primerBlock_nombreEmpresa = elem.text();

			if (filtrador == 1) // ahora mete en el Hash el nombre de la
								// Empresa y la Localidad
				nombreEmpresa_Localizacion.put(primerBlock_nombreEmpresa, elem.text());

			if (filtrador == 2) {
				filtrador = 0;
				continue;
			}
			filtrador++;

		}
		// return out.toString();

		return nombreEmpresa_Localizacion;
	}

	/**
	 * Gets the experiencia info empleo.
	 *
	 * @param url
	 *            the url
	 * @return the experiencia info empleo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getExperienciaInfoEmpleo(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();

		Elements lst = doc.select("li > p[class*=small]");
		StringBuffer out = new StringBuffer();
		for (Element elem : lst) {
			out.append(elem.text() + "\n");
		}
		return out.toString();
	}

	/**
	 * Gets the descripcion info empleo.
	 *
	 * @param url
	 *            the url
	 * @return the descripcion info empleo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getDescripcionInfoEmpleo(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();

		Elements lst = doc.select("li > p[class*=description]");
		StringBuffer out = new StringBuffer();
		for (Element elem : lst) {
			out.append(elem.text() + "\n");
		}
		return out.toString();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String args[]) throws IOException {
		// System.out.println(getNombreEmpresaAndLocalizacionInfoEmpleo(infoempleo_url));

		Map<String, String> pruebasBlocks = getNombreEmpresaAndLocalizacionInfoEmpleo(infoempleo_url);
		for (Entry<String, String> entrada : pruebasBlocks.entrySet()) {
			System.out.println("nombre Empresa: " + entrada.getKey() + " || Localizacion: " + entrada.getValue());
		}
		System.out.println(pruebasBlocks.size());
	}

}
