package com.app.anra.crauler.restclient;


import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//https://developer.infojobs.net/test-console/console.xhtml?operationEntityField=-offer&methodfield=GET&versionfield=1&hmethodfield=LIST
/*
 * NO FUNCIONA
 */
@Component
public class InfojobsAPIClient {

	static String infoJobsUrl = "https://api.infojobs.net/api/1/offer";
	static String username = "5657566713114d71b71516ba9395d4ae";
	static String password = "cmo+EdVRuqQyEwGQRV4H5J5+lITBAoTPLHr+rwLmRu5EoD7WHJ";
	
	static HttpHeaders createHeaders(String username, String password) {
		HttpHeaders cabezas = new HttpHeaders();
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		cabezas.set("Authorization", authHeader);

		return cabezas;
	}

	public static void main(String[] args) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<HttpHeaders> entity = new HttpEntity<HttpHeaders>((createHeaders(username, password)), headers);
		restTemplate.exchange(infoJobsUrl, HttpMethod.GET, entity, String.class);

		
		ResponseEntity<String> response = restTemplate.getForEntity(infoJobsUrl, String.class);

		System.out.println(response);
		

	}


}
