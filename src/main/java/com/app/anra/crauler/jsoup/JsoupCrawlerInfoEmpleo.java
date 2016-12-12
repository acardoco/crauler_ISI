package com.app.anra.crauler.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.app.anra.crauler.model.VOs.Oferta;
import com.app.anra.crauler.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class JsoupParserISI.
 */
/*
 */
public class JsoupCrawlerInfoEmpleo {

	
	/** The num_paginas_infoempleo. */
	public static int num_paginas_infoempleo = 2; //maximo esta en 250 la ultima vez que lo mire 
	//cada pagina tiene 20 elementos
	
	/** The infoempleo_url. */
	public static String infoempleo_url = "http://www.infoempleo.com/trabajo/";
	// SelectorGadget->extensión de chrome

	/**
	 * Gets the nombre oferta info empleo.
	 *
	 * @param url
	 *            the url
	 * @param doc 
	 * @return the nombre oferta info empleo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static ArrayList<String> getNombreOfertaInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> listaNombres = new ArrayList<String>();
		

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
	public static ArrayList<String> getNombreEmpresaInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> listaNombres = new ArrayList<String>();
		

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
	public static ArrayList<String> getLocalizacionInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> listaLocalizaciones = new ArrayList<String>();
		

		Elements lst = doc.select(".block");

		int filtrador = 0;

		for (Element elem : lst) {

			if (filtrador == 1)
				listaLocalizaciones.add(elem.text());
			if (filtrador == 2) {
				filtrador = 0;
				continue;
			}
			filtrador++;
		}

		return listaLocalizaciones;
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
	public static ArrayList<String> getExperienciaInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> exp = new ArrayList<String>();
		
		Elements lst = doc.select("li > p[class*=small]");
		
		for (Element elem : lst) {
			exp.add(getDato("experiencia", elem.text()));
		}
		return exp;
	}
	
	
	public static ArrayList<String> getSalarioInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> salario = new ArrayList<String>();
		

		Elements lst = doc.select("li > p[class*=small]");
		for (Element elem : lst) {
			salario.add(getDato("salario", elem.text()));
		}
		return salario;
	}
	
	public static ArrayList<String> getJornadaInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> jornada = new ArrayList<String>();
		

		Elements lst = doc.select("li > p[class*=small]");
		for (Element elem : lst) {
			jornada.add(getDato("jornada", elem.text()));
		}
		return jornada;
	}
	
	public static ArrayList<String> getContratoInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> contrato = new ArrayList<String>();
		

		Elements lst = doc.select("li > p[class*=small]");
		for (Element elem : lst) {
			contrato.add(getDato("contrato", elem.text()));
		}
		return contrato;
	}
	

	public static ArrayList<String> getFechaInfoEmpleo(Document doc) throws IOException {
		
		ArrayList<String> listaFechas = new ArrayList<String>();;
		

		Elements lst = doc.select(".block");

		int filtrador = 0;

		for (Element elem : lst) {

			if (filtrador == 2){
				listaFechas.add(elem.text());
				filtrador = 0;
				continue;
			}

			filtrador++;
		}

		return listaFechas;
	}
	
	
	private static String getDato(String dato, String texto){
		
		String [] array = texto.split(",");
		String datoString = null;
		for(int i=0; i< array.length; i++){
			if(array[i].toLowerCase().contains(dato)) datoString = array[i].replaceAll("salario", "").trim();
		}

		return datoString;
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
	public static ArrayList<String> getDescripcionInfoEmpleo(Document doc) throws IOException {
		ArrayList<String> descp = new ArrayList<String>();
	
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
		
		Document doc = Util.getHtml(url);

		ArrayList<String> nombresOfertas = getNombreOfertaInfoEmpleo(doc);
		ArrayList<String> nombresEmpresas = getNombreEmpresaInfoEmpleo(doc);
		ArrayList<String> localizaciones = getLocalizacionInfoEmpleo(doc);
		ArrayList<String> descripciones = getDescripcionInfoEmpleo(doc);
		ArrayList<String> experiencias = getExperienciaInfoEmpleo(doc);
		ArrayList<String> salarios = getSalarioInfoEmpleo(doc);
		ArrayList<String> contratos = getContratoInfoEmpleo(doc);
		ArrayList<String> jornadas = getJornadaInfoEmpleo(doc);
		ArrayList<String> fechas = getFechaInfoEmpleo(doc);

		for (int i = 0; i < nombresOfertas.size(); i++) {
			Oferta oferta = new Oferta();
			oferta.setNombre_oferta(nombresOfertas.get(i));
			oferta.setNombre_empresa(nombresEmpresas.get(i));
			oferta.setLocalizacion(localizaciones.get(i));
			oferta.setDescrp_oferta(descripciones.get(i));
			oferta.setExperiencia(experiencias.get(i));
			oferta.setSalario(salarios.get(i));
			oferta.setJornada(jornadas.get(i));
			oferta.setContrato(contratos.get(i));
			oferta.setFechaInfoEmpleo(fechas.get(i));
			ofertas.add(oferta);
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
	public static ArrayList<Oferta> getAllPagesInfoEmpleo() throws IOException {
		ArrayList<Oferta> ofertasTotales = new ArrayList<Oferta>();
		
		for (int j = 1; j <= num_paginas_infoempleo; j++) {
			
			System.out.println("página: " + j + "------------");
			if (j > 1){
				ofertasTotales.addAll(toOfertas(infoempleo_url + "pagina_" + j));
			}else
				ofertasTotales.addAll(toOfertas(infoempleo_url));
			
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
		
		List<Oferta> a = getAllPagesInfoEmpleo();

		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).toString());
		}
		System.out.println(a.size());
	}

}
