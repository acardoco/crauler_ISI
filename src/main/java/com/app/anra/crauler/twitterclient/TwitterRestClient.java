package com.app.anra.crauler.twitterclient;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.app.anra.crauler.model.VOs.Empresa;
import com.app.anra.crauler.model.VOs.Oferta;

/*
 * Comunicación con la Search API de Twitter:
 * 
 * 1. Crearse una cuenta de desarrollador y registrar una aplicación.
 * 2. Se crean automáticamente dos claves: "consumer key" y "consumer secret".
 * 3. Enviar una petición al servicio oauth2 de Twitter autenticándose con esas dos claves.
 * 4. Como respuesta, te devuelve un token de acceso.
 * 5. Utilizar el token de acceso para realizar las peticiones al API Search.
 * 
 * En resumen: antes de usar el API Search directamente, hay que pedirle un token de acceso a Twitter,
 * el cual se incorpora en todas las peticiones.
 * 
 * DATOS IMPORTANTES:
 * 
 * - Los tokens, en principio, no expiran. 
 * - Límite: 480 peticiones cada 15 minutos.
 * 
 * https://dev.twitter.com/rest/reference/get/search/tweets
 * https://dev.twitter.com/oauth/application-only
 * 
 * */

public class TwitterRestClient {
	
	private static String twitterSearchURL = "/1.1/search/tweets.json?q=";
	
	private static String consumerKey = "Feo1nWdnmPamgaqd6YIUUATaw";
	private static String consumerSecret = "2msBTP7akFaJwKH9GjW7LMbS0l78X6drnBF3lYziz8RHwd3awC";
	private static String consumerEncoded = Base64.encodeBase64String((consumerKey+":"+consumerSecret).getBytes());
	private static String ACCESS_TOKEN = "AAAAAAAAAAAAAAAAAAAAABfyyAAAAAAAUCoM9L0sdG4I%2B"
			+ "4eWUC6nzG5%2Bhrs%3D9hFaHcfdOdwlk1q1X3zqEImKSa0Dm49KBplIGHWrQstwzIXKoC";
	
	
	public static void main(String[] args) {

		
		HttpHost targetHost = new HttpHost("api.twitter.com", 443, "https");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
	try {
		
		// Proceso para pedir un token de acceso (por si el otro expirase).
		
		/* 

		HttpPost accessRequest = new HttpPost("/oauth2/token");
		BasicNameValuePair postParameter = new BasicNameValuePair("grant_type", "client_credentials");
		List <BasicNameValuePair> parametersList = new ArrayList <BasicNameValuePair>();
		parametersList.add(postParameter);
		
		accessRequest.setEntity(new UrlEncodedFormEntity(parametersList));
		accessRequest.addHeader("User-Agent" , "SearchRestClient");
		accessRequest.addHeader("Authorization" , "Basic " + consumerEncoded);
	
		HttpResponse accessResponse = client.execute(targetHost, accessRequest);
		JSONObject bearerToken = new JSONObject (EntityUtils.toString(accessResponse.getEntity(), "UTF-8"));
		System.out.println("Access token: " + bearerToken.getString("access_token"));
		
		*/
		
		HttpGet tweetsRequest = new HttpGet(twitterSearchURL + "everis" + "&lang=es&result_type=recent");
		tweetsRequest.addHeader("User-Agent" , "SearchRestClient");
		tweetsRequest.addHeader("Authorization" , "Bearer " + ACCESS_TOKEN);
			
		HttpResponse tweetsResponse = client.execute(targetHost, tweetsRequest);
		
		JSONArray tweets = (new JSONObject(EntityUtils.toString(tweetsResponse.getEntity(), "UTF-8")).getJSONArray("statuses"));
		
		for(int numTweet = 0; numTweet < tweets.length(); numTweet++)
		{
			System.out.println(tweets.getJSONObject(numTweet).getString("text"));
		}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
