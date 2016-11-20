package com.app.anra.crauler.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.app.anra.crauler.model.VOs.Oferta;

// TODO: Auto-generated Javadoc
/**
 * The Class JsoupParserISI.
 */
/*
 */
public class JsoupCrawlerInfoEmpleo {

	/** The monster_url. */
	public static String monster_url = "http://www.monster.es/trabajo/buscar/?cy=ES";

	
	/** The num_paginas_infoempleo. */
	public static int num_paginas_infoempleo = 250;
	
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
	public static ArrayList<String> getNombreOfertaInfoEmpleo(String url) throws IOException {
		ArrayList<String> listaNombres = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(0).get();

		Elements lst = doc.select("h2 > a");
		for (Element elem : lst) {
			listaNombres.add(elem.text());
		}
		return listaNombres;
	}

	/**
	 * Gets the nombre empresa info empleo.
	 *
	 * @param url
	 *            the url
	 * @return the nombre empresa info empleo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static ArrayList<String> getNombreEmpresaInfoEmpleo(String url) throws IOException {
		ArrayList<String> listaNombres = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(0).get();

		Elements lst = doc.select(".block");

		int filtrador = 0;

		for (Element elem : lst) {

			if (filtrador == 0) // Empezamos guardando el primer block, que es
				// el nombre de la empresa
				listaNombres.add(elem.text());
			if (filtrador == 2) {
				filtrador = 0;
				continue;
			}
			filtrador++;
		}

		return listaNombres;
	}

	/**
	 * Gets the localizacion info empleo.
	 *
	 * @param url
	 *            the url
	 * @return the localizacion info empleo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static ArrayList<String> getLocalizacionInfoEmpleo(String url) throws IOException {
		ArrayList<String> listaNombres = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(0).get();

		Elements lst = doc.select(".block");

		int filtrador = 0;

		for (Element elem : lst) {

			if (filtrador == 1) // Mete en el Hash la Localidad
				listaNombres.add(elem.text());
			if (filtrador == 2) {
				filtrador = 0;
				continue;
			}
			filtrador++;
		}

		return listaNombres;
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
	public static ArrayList<String> getExperienciaInfoEmpleo(String url) throws IOException {
		ArrayList<String> exp = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(0).get();

		Elements lst = doc.select("li > p[class*=small]");
		for (Element elem : lst) {
			exp.add(elem.text());
		}
		return exp;
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
	public static ArrayList<String> getDescripcionInfoEmpleo(String url) throws IOException {
		ArrayList<String> descp = new ArrayList<String>();
		Document doc = Jsoup.connect(url).timeout(0).get();

		Elements lst = doc.select("li > p[class*=description]");
		for (Element elem : lst) {
			descp.add(elem.text());
		}
		return descp;
	}

	/**
	 * To ofertas.
	 *
	 * @param url
	 *            the url
	 * @return the array list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static ArrayList<Oferta> toOfertas(String url) throws IOException {
		ArrayList<Oferta> ofertas = new ArrayList<Oferta>();

		ArrayList<String> nombresOfertas = getNombreOfertaInfoEmpleo(url);
		ArrayList<String> nombresEmpresas = getNombreEmpresaInfoEmpleo(url);
		ArrayList<String> localizaciones = getLocalizacionInfoEmpleo(url);
		ArrayList<String> descripciones = getDescripcionInfoEmpleo(url);
		ArrayList<String> experiencias = getExperienciaInfoEmpleo(url);

		for (int i = 0; i < nombresOfertas.size(); i++) {
			Oferta ofertilla = new Oferta();
			ofertilla.setNombre_oferta(nombresOfertas.get(i));
			ofertilla.setNombre_empresa(nombresEmpresas.get(i));
			ofertilla.setLocalizacion(localizaciones.get(i));
			ofertilla.setDescrp_oferta(descripciones.get(i));
			ofertilla.setExperiencia(experiencias.get(i));
			ofertas.add(ofertilla);
		}

		return ofertas;
	}

	/**
	 * Gets the all pages info empleo.
	 *
	 * @param url the url
	 * @return the all pages info empleo
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static ArrayList<Oferta> getAllPagesInfoEmpleo(String url) throws IOException {
		ArrayList<Oferta> ofertasTotales = new ArrayList<Oferta>();
		
		for (int j = 1; j <= num_paginas_infoempleo; j++) {
			
			if (j > 1){
				System.out.println("Voy por la pagina:" + url + "pagina_" + j);
				ofertasTotales.addAll(toOfertas(url + "pagina_" + j));
			}else
				ofertasTotales.addAll(toOfertas(url));
			
		}
		
		
		return ofertasTotales;
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
		List<Oferta> a = getAllPagesInfoEmpleo(infoempleo_url);

//		for (int i = 0; i < a.size(); i++) {
//			System.out.println(a.get(i).toString());
//		}
		System.out.println(a.size());
	}

}
