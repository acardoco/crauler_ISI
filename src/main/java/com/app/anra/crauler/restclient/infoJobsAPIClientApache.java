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

import com.app.anra.crauler.model.VOs.Oferta;

public class infoJobsAPIClientApache {

	static String infoJobsUrl = "https://api.infojobs.net/api/1/offer";
	static String username = "5657566713114d71b71516ba9395d4ae";
	static String password = "cmo+EdVRuqQyEwGQRV4H5J5+lITBAoTPLHr+rwLmRu5EoD7WHJ";

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpHost targetHost = new HttpHost("api.infojobs.net", 443, "https");
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(username, password));
		
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		// create a GET method that queries some API operation
		HttpGet request = new HttpGet("/api/1/offer");

		try {
			// execute the operation
			HttpResponse response = client.execute(targetHost, request);

			// print the status and the contents of the response
			System.out.println(response.getStatusLine());
			
			JSONObject jsonObj = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
			JSONArray array = jsonObj.getJSONArray("offers");
			ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
			
			for(int n = 0; n < array.length(); n++)
			{
			    JSONObject object = array.getJSONObject(n);
			    
				Oferta ofertilla = new Oferta();
				ofertilla.setNombre_oferta(object.get("title").toString());
				ofertilla.setNombre_empresa(object.getJSONObject("author").get("name").toString());
				ofertilla.setLocalizacion(object.getJSONObject("province").get("value").toString());
				ofertilla.setDescrp_oferta("descripción");
				ofertilla.setExperiencia(object.getJSONObject("experienceMin").get("value").toString());
				ofertas.add(ofertilla);
			}
			
			// Para comprobar.
			for(Oferta o: ofertas){
				System.out.println(o.getNombre_oferta());
				System.out.println(o.getNombre_empresa());
				System.out.println(o.getLocalizacion());
				System.out.println(o.getExperiencia());
				System.out.println("-------------------------------");
			}
			
		} finally {
			// release any connection resources used by the method
			client.close();
		}

	}

}
