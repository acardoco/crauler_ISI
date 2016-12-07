package com.app.anra.crauler.mongo;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.app.anra.crauler.jsoup.JsoupCrawlerInfoEmpleo;
import com.app.anra.crauler.model.VOs.Empresa;
import com.app.anra.crauler.model.VOs.InfoJobsVO;
import com.app.anra.crauler.model.VOs.Oferta;
import com.app.anra.crauler.restclient.TwitterRestClient;
import com.app.anra.crauler.restclient.infoJobsAPIClientApache;
import com.app.anra.crauler.restclient.meaningCloudRestClient;
import com.app.anra.crauler.util.Util;

public class Application {

	private static ApplicationContext ctx;

	public static void main(String[] args) throws IOException {

		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		// Obtenemos ofertas de InfoJOBS e InfoEMPLEO.
		ArrayList<Oferta> ofertas = JsoupCrawlerInfoEmpleo.getAllPagesInfoEmpleo();
		InfoJobsVO ofertasYempresas = infoJobsAPIClientApache.getOfertasAndEmpresas();
		// Para no tener dos variables, juntamos las ofertas de InfoJOBS con las
		// de InfoEMPLEO.
		ofertas.addAll(ofertasYempresas.getOfertas());

		// Para cada empresa, obtenemos la lista de tweets (= 15), y
		// actualizamos las valoraciones de cada tweet
		ArrayList<Empresa> empresas = ofertasYempresas.getEmpresas();
		for (Empresa e : empresas) {

			if (e.getNumEmployers() > 5000) {
				e.setTweets(TwitterRestClient.getTweets((Util.cleanName(e.getName()))));
				//AQUI NO CAMBIA LAS VALORACIONES
				if(e.getTweets() != null) e.setTweets(meaningCloudRestClient.getValoraciones(e.getTweets()));
		}
		
		}

		
		// Insertamos en MongoDB los resultados.
		MongoFunctions.insertarEnBD(ofertas, mongoOperation);
		MongoFunctions.insertarEmpresasEnBD(empresas, mongoOperation);
		

		// Aplicamos MapReduce.
		MongoFunctions.calcularMedia();
		MongoFunctions.calcularLocalizaciones();

		ArrayList<Oferta> listUser = MongoFunctions.findAll(mongoOperation);
		System.out.println("4. Number of user = " + listUser.size());

//		MongoFunctions.dropDB(mongoOperation);

	}
}
