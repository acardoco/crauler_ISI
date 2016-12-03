package com.app.anra.crauler.restclient;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.app.anra.crauler.model.VOs.Empresa;
import com.app.anra.crauler.model.VOs.Oferta;

public class infoJobsAPIClientApache {

	private static String infoJobsOffersURL = "/api/1/offer?page=";
	private static int numPages = 1; //MAX = 114;
	private static String infoJobsCompanyURL = "/api/1/profile/";
	private static String username = "5657566713114d71b71516ba9395d4ae";
	private static String password = "cmo+EdVRuqQyEwGQRV4H5J5+lITBAoTPLHr+rwLmRu5EoD7WHJ";

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpHost targetHost = new HttpHost("api.infojobs.net", 443, "https");
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(username, password));
		
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		HttpGet offersRequest = null;
		HttpGet companyRequest = null;
		HttpResponse response = null;
		
		JSONArray ofertasJSON = null;
		JSONObject ofertaJSON = null;
		JSONObject empresaJSON = null;
		
		ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
		ArrayList<Empresa> empresas = new ArrayList<Empresa>();

		try {
		
		for(int page = 1; page <= numPages; page++){
			
			offersRequest = new HttpGet(infoJobsOffersURL+page);
			response = client.execute(targetHost, offersRequest);
			ofertasJSON = (new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8")).getJSONArray("offers"));
			
			System.out.println(" ----- Recogiendo ofertas de página " + page);
			
			for(int numOferta = 0; numOferta < ofertasJSON.length(); numOferta++)
			{
				ofertaJSON = ofertasJSON.getJSONObject(numOferta);
			    
				Oferta oferta = new Oferta();
				oferta.setNombre_oferta(ofertaJSON.get("title").toString());
				oferta.setNombre_empresa(ofertaJSON.getJSONObject("author").get("name").toString());
				oferta.setLocalizacion(ofertaJSON.getJSONObject("province").get("value").toString());
				oferta.setDescrp_oferta("descripción");
				oferta.setExperiencia(ofertaJSON.getJSONObject("experienceMin").get("value").toString());
				ofertas.add(oferta);
				
				companyRequest = new HttpGet(infoJobsCompanyURL+ofertaJSON.getJSONObject("author").get("id"));
				response = client.execute(targetHost, companyRequest);
				empresaJSON = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
	
				empresas.add(new Empresa(
						empresaJSON.get("name").toString(), 
						empresaJSON.get("description").toString(), 
						empresaJSON.getJSONObject("typeIndustry").get("value").toString(),
						Integer.parseInt(empresaJSON.get("numberWorkers").toString()),
						empresaJSON.getJSONObject("country").get("value").toString()));
			}
			
		}
		
		for(Oferta o: ofertas){
			System.out.println(o.getNombre_oferta());
			System.out.println(o.getNombre_empresa());
			System.out.println(o.getLocalizacion());
			System.out.println(o.getExperiencia());
			System.out.println("-------------------------------");
			}
			
		for(Empresa e: empresas){
			System.out.println(e.getName());
			System.out.println(e.getNumEmployers());
			System.out.println("-------------------------------");
		}
			
		} finally {
			client.close();
		}

	}

}
