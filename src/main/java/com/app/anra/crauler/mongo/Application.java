package com.app.anra.crauler.mongo;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.app.anra.crauler.jsoup.JsoupCrawlerInfoEmpleo;
import com.app.anra.crauler.model.VOs.Oferta;

public class Application {

	private static ApplicationContext ctx;

	
	
	public static void main(String[] args) throws IOException {
		
		

		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		ArrayList<Oferta> ofertasInfoEmpleo = JsoupCrawlerInfoEmpleo.getAllPagesInfoEmpleo();
		
		
		MongoFunctions.insertarEnBD(ofertasInfoEmpleo, mongoOperation);
////		
		ArrayList<Oferta> listUser = MongoFunctions.findAll(mongoOperation);
		System.out.println("4. Number of user = " + listUser.size());
		
		MongoFunctions.dropDB(mongoOperation);

	}
}
