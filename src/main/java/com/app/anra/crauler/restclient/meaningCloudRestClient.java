package com.app.anra.crauler.restclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.app.anra.crauler.model.VOs.Tweet;

public class meaningCloudRestClient {

	/** The Meaning Cloud URL. */
	private static String meaningCloudURL = "/reputation-1.0";

	/** The key. */
	private static String key = "c4adcae27d5f82325f0271e8c97d2d05";

	
	public static void getValoraciones(List<Tweet> tweets){
		
		HttpHost targetHost = new HttpHost("api.meaningcloud.com", 80, "http");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		HttpPost post = new HttpPost(meaningCloudURL);
		List<BasicNameValuePair> parametersList = new ArrayList<BasicNameValuePair>();
		parametersList.add( new BasicNameValuePair("key", key));
		parametersList.add( new BasicNameValuePair("txt", "Everis me parece una puta mierda"));
		parametersList.add( new BasicNameValuePair("model", "BusinessRep_es"));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(parametersList));
			post.addHeader("content-type", "application/x-www-form-urlencoded");

			HttpResponse accessResponse = client.execute(targetHost, post);
			JSONObject valoration = new JSONObject(EntityUtils.toString(accessResponse.getEntity(), "UTF-8"));
			System.out.println(valoration.toString());
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		getValoraciones(new ArrayList<Tweet>());

	}

}
