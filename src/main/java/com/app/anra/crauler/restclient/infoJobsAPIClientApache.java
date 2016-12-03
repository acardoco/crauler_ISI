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
import com.app.anra.crauler.model.VOs.Twitter;

// TODO: Auto-generated Javadoc
/**
 * The Class infoJobsAPIClientApache.
 */
public class infoJobsAPIClientApache {

	/** The info jobs offers URL. */
	private static String infoJobsOffersURL = "/api/1/offer?page=";

	/** The num pages. */
	private static int numPages = 1; // MAX = 114;

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
	public static Twitter getOfertasAndEmpresas() throws ClientProtocolException, IOException {

		// nueva clase
		Twitter twitterInfo = new Twitter();

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
					oferta.setNombre_oferta(ofertaJSON.get("title").toString());
					oferta.setNombre_empresa(ofertaJSON.getJSONObject("author").get("name").toString());
					oferta.setLocalizacion(ofertaJSON.getJSONObject("province").get("value").toString());
					oferta.setDescrp_oferta("descripción");
					oferta.setExperiencia(ofertaJSON.getJSONObject("experienceMin").get("value").toString());
					twitterInfo.addOferta(oferta);

					companyRequest = new HttpGet(infoJobsCompanyURL + ofertaJSON.getJSONObject("author").get("id"));
					response = client.execute(targetHost, companyRequest);
					empresaJSON = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));

					Empresa empresa = new Empresa();
					empresa.setName(empresaJSON.get("name").toString());
					empresa.setDescription(empresaJSON.get("description").toString());
					empresa.setType(empresaJSON.getJSONObject("typeIndustry").get("value").toString());
					empresa.setNumEmployers(Integer.parseInt(empresaJSON.get("numberWorkers").toString()));
					empresa.setLocation(empresaJSON.getJSONObject("country").get("value").toString());

					twitterInfo.addEmpresa(empresa);
				}

			}

		} finally {
			client.close();
		}

		return twitterInfo;

	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {

		Twitter pruebecita = getOfertasAndEmpresas();
		for (Oferta o : pruebecita.getOfertas()) {
			System.out.println(o.getNombre_oferta());
			System.out.println(o.getNombre_empresa());
			System.out.println(o.getLocalizacion());
			System.out.println(o.getExperiencia());
			System.out.println("-------------------------------");
		}

		for (Empresa e : pruebecita.getEmpresas()) {
			System.out.println(e.getName());
			System.out.println(e.getNumEmployers());
			System.out.println("-------------------------------");
		}
	}

}
