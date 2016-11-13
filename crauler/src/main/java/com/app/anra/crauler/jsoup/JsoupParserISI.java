package com.app.anra.crauler.jsoup;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * en obras...
 */
public class JsoupParserISI {

	public static String monster_url = "http://www.monster.es/trabajo/buscar/?cy=ES";
	public static String infoempleo_url = "http://www.infoempleo.com/trabajo/";
	// SelectorGadget->extensión de chrome

	public static String getClassJobTitle(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		System.out.println("Processing " + url);
		Elements lst = doc.select(".block"); // coje de momento todo con este
												// tipo de clase, por lo que hay
												// que filtrar bien(estudiar la
												// pagina web)
		StringBuffer out = new StringBuffer();
		for (Element elem : lst) {
			out.append(elem.text() + "\n");
		}
		return out.toString();
	}

	public static void main(String args[]) throws IOException {
		System.out.println(getClassJobTitle(infoempleo_url));
	}

}
