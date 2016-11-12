package com.app.anra.crauler.restclient;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class infoJobsAPIClientApache {

	static String infoJobsUrl = "https://api.infojobs.net/api/1/offer";
	static String username = "5657566713114d71b71516ba9395d4ae";
	static String password = "cmo+EdVRuqQyEwGQRV4H5J5+lITBAoTPLHr+rwLmRu5EoD7WHJ";

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();   
		  
        HttpHost targetHost = new HttpHost("api.infojobs.net", 443, "https");   
  
        // Provide the credentials to be used for the host against which   
        // authentication is to be attempted.  
        client.getCredentialsProvider().setCredentials(  
            new AuthScope(targetHost.getHostName(), targetHost.getPort()),        
            new UsernamePasswordCredentials(username, password)  
        );  
          
        // create a GET method that queries some API operation  
        HttpGet request = new HttpGet("/api/1/offer");    
  
        try {  
            // execute the operation  
            HttpResponse response = client.execute(targetHost, request);  
              
            // print the status and the contents of the response  
            System.out.println(response.getStatusLine());  
            System.out.println(EntityUtils.toString(response.getEntity()));  
        } finally {  
            // release any connection resources used by the method  
            client.getConnectionManager().shutdown();  
        } 

	}

}
