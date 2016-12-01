package com.app.anra.crauler.restclient;

import java.io.IOException;

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

public class GenericRestClientAuth {

	public static void getConnection(String username, String password, String API, int port, String method, String CollObject) throws ClientProtocolException, IOException {
		HttpHost targetHost = new HttpHost(API, port, "https");
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(username, password));

		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		// create a GET method that queries some API operation
		HttpGet request = new HttpGet(method);

		try {
			// execute the operation
			HttpResponse response = client.execute(targetHost, request);

			// print the status and the contents of the response
			System.out.println(response.getStatusLine());

			JSONObject jsonObj = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
			JSONArray array = jsonObj.getJSONArray(CollObject);

		} finally {
			// release any connection resources used by the method
			client.close();
		}
	}

}
