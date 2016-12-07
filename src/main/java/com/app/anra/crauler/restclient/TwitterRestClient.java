package com.app.anra.crauler.restclient;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.app.anra.crauler.model.VOs.Tweet;

// TODO: Auto-generated Javadoc
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

/**
 * The Class TwitterRestClient.
 */
public class TwitterRestClient {

	/** The twitter search URL. */
	private static String twitterSearchURL = "/1.1/search/tweets.json?q=";

	/** The consumer key. */
	private static String consumerKey = "Feo1nWdnmPamgaqd6YIUUATaw";
	
	/** The consumer secret. */
	private static String consumerSecret = "2msBTP7akFaJwKH9GjW7LMbS0l78X6drnBF3lYziz8RHwd3awC";
	
	/** The consumer encoded. */
	private static String consumerEncoded = Base64.encodeBase64String((consumerKey + ":" + consumerSecret).getBytes());
	
	/** The access token. */
	private static String ACCESS_TOKEN = "AAAAAAAAAAAAAAAAAAAAABfyyAAAAAAAUCoM9L0sdG4I%2B"
			+ "4eWUC6nzG5%2Bhrs%3D9hFaHcfdOdwlk1q1X3zqEImKSa0Dm49KBplIGHWrQstwzIXKoC";

	/**
	 * Gets the token.
	 *
	 * @param targetHost the target host
	 * @param client the client
	 * @return the token
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Proceso para pedir un token de acceso (por si el otro expirase).
	public static void getToken(HttpHost targetHost, CloseableHttpClient client)
			throws ClientProtocolException, IOException {

		HttpPost accessRequest = new HttpPost("/oauth2/token");
		BasicNameValuePair postParameter = new BasicNameValuePair("grant_type", "client_credentials");
		List<BasicNameValuePair> parametersList = new ArrayList<BasicNameValuePair>();
		parametersList.add(postParameter);

		accessRequest.setEntity(new UrlEncodedFormEntity(parametersList));
		accessRequest.addHeader("User-Agent", "SearchRestClient");
		accessRequest.addHeader("Authorization", "Basic " + consumerEncoded);

		HttpResponse accessResponse = client.execute(targetHost, accessRequest);
		JSONObject bearerToken = new JSONObject(EntityUtils.toString(accessResponse.getEntity(), "UTF-8"));
		System.out.println("Access token: " + bearerToken.getString("access_token"));
	}

	/**
	 * Gets the tweets.
	 *
	 * @param empresa the empresa
	 * @return the tweets
	 */
	public static List<Tweet> getTweets(String empresa) {
		HttpHost targetHost = new HttpHost("api.twitter.com", 443, "https");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		JSONArray JSONtweets = null;
		List<Tweet> tweets = null;

		try {

			HttpGet tweetsRequest = new HttpGet(twitterSearchURL + URLEncoder.encode(empresa, "UTF-8") + 
					"&lang=es&result_type=&result_type=mixed");
			tweetsRequest.addHeader("User-Agent", "SearchRestClient");
			tweetsRequest.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);

			HttpResponse tweetsResponse = client.execute(targetHost, tweetsRequest);

			JSONtweets = (new JSONObject(EntityUtils.toString(tweetsResponse.getEntity(), "UTF-8"))
					.getJSONArray("statuses"));
			
			
			if(JSONtweets.length() > 0){
				tweets = new ArrayList<Tweet>();
				for (int numTweet = 0; numTweet < JSONtweets.length(); numTweet++) {
				tweets.add(new Tweet(JSONtweets.getJSONObject(numTweet).getString("text")));
				}
			}


		} catch (IOException e) {

			e.printStackTrace();
		}

		return tweets;
	}

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		getTweets("paté de pato");
	}

}
