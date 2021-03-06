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

import com.app.anra.crauler.model.VOs.Empresa;
import com.app.anra.crauler.model.VOs.InfoJobsVO;
import com.app.anra.crauler.model.VOs.Oferta;

// TODO: Auto-generated Javadoc
/**
 * The Class infoJobsAPIClientApache.
 */
public class infoJobsAPIClientApache {

	/** The info jobs offers URL. */
	private static String infoJobsOffersURL = "/api/1/offer?page=";

	/** The num pages. */
	private static int numPages = 20; // MAX = 114;

	/** The info jobs company URL. */
	private static String infoJobsCompanyURL = "/api/1/profile/";

	/** The username. */
	private static String username = "5657566713114d71b71516ba9395d4ae";

	/** The password. */
	private static String password = "cmo+EdVRuqQyEwGQRV4H5J5+lITBAoTPLHr+rwLmRu5EoD7WHJ";

	/**
	 * Gets the ofertas and empresas.
	 *
	 * @return the ofertas and empresas
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static InfoJobsVO getOfertasAndEmpresas() throws ClientProtocolException, IOException {

		// nueva clase
		InfoJobsVO empresasOfertas= new InfoJobsVO();

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

		try {

			for (int page = 1; page <= numPages; page++) {

				offersRequest = new HttpGet(infoJobsOffersURL + page);
				response = client.execute(targetHost, offersRequest);
				ofertasJSON = (new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"))
						.getJSONArray("offers"));

				System.out.println(" ----- Recogiendo ofertas de página " + page);

				for (int numOferta = 0; numOferta < ofertasJSON.length(); numOferta++) {
					ofertaJSON = ofertasJSON.getJSONObject(numOferta);

					Oferta oferta = new Oferta();
					oferta.setNombre_oferta(ofertaJSON.getString("title"));
					oferta.setNombre_empresa(ofertaJSON.getJSONObject("author").getString("name"));
					oferta.setLocalizacion(ofertaJSON.getJSONObject("province").getString("value"));
					oferta.setExperiencia(ofertaJSON.getJSONObject("experienceMin").getString("value"));
					oferta.setContrato(ofertaJSON.getJSONObject("contractType").getString("value"));
					oferta.setSalario(getSalario(ofertaJSON));
					oferta.setJornada(ofertaJSON.getJSONObject("workDay").getString("value"));
					oferta.setFechaInfoJobs(ofertaJSON.getString("published"));
					empresasOfertas.addOferta(oferta);

					companyRequest = new HttpGet(infoJobsCompanyURL + ofertaJSON.getJSONObject("author").get("id"));
					response = client.execute(targetHost, companyRequest);
					empresaJSON = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));

					Empresa empresa = new Empresa();
					empresa.setName(empresaJSON.getString("name"));
					empresa.setDescription(empresaJSON.getString("description"));
					empresa.setType(empresaJSON.getJSONObject("typeIndustry").getString("value"));
					empresa.setNumEmployers(empresaJSON.getInt("numberWorkers"));
					empresa.setLocation(empresaJSON.getJSONObject("province").getString("value"));
		
					if(!empresasOfertas.getEmpresas().contains(empresa) && 
							!empresa.getName().toLowerCase().contains("empresa")){
						empresasOfertas.addEmpresa(empresa);
					}
					
				}

			}

		} finally {
			client.close();
		}

		return empresasOfertas;
	}
	
	private static String getSalario(JSONObject oferta){
		
		String salarioTotal = "";
		String salarioMin = oferta.getJSONObject("salaryMin").getString("value");
		String salarioMax = oferta.getJSONObject("salaryMax").getString("value");
		
		if(!salarioMin.isEmpty()) salarioTotal = salarioMin;
		if(!salarioMax.isEmpty()){
			if (salarioMin.isEmpty()) salarioTotal = salarioMax;
			else salarioTotal = salarioTotal + " - " + salarioMax;
		}
		salarioTotal = salarioTotal + " " + oferta.getJSONObject("salaryPeriod").getString("value") ;
		
		return (!salarioTotal.trim().isEmpty() ? salarioTotal.toLowerCase().replace(" €", "€") : null);
	}

}
