package com.app.anra.crauler.twitterclient;

import java.io.IOException;
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
	public static JSONArray getTweets(String empresa) {

		HttpHost targetHost = new HttpHost("api.twitter.com", 443, "https");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		JSONArray tweets = null;

		try {

			HttpGet tweetsRequest = new HttpGet(twitterSearchURL + empresa + "&lang=es&result_type=recent");
			tweetsRequest.addHeader("User-Agent", "SearchRestClient");
			tweetsRequest.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);

			HttpResponse tweetsResponse = client.execute(targetHost, tweetsRequest);

			tweets = (new JSONObject(EntityUtils.toString(tweetsResponse.getEntity(), "UTF-8"))
					.getJSONArray("statuses"));

		} catch (IOException e) {

			e.printStackTrace();
		}

		return tweets;
	}

	/**
	 * Prints the tweets.
	 *
	 * @param tweets the tweets
	 */
	public static void printTweets(JSONArray tweets) {
		for (int numTweet = 0; numTweet < tweets.length(); numTweet++) {
			System.out.println(tweets.getJSONObject(numTweet).getString("text"));
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		
		printTweets(getTweets("everis"));
		
	}

}
