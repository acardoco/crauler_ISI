package com.app.anra.crauler.mongo;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.anra.crauler.model.VOs.Empresa;
import com.app.anra.crauler.model.VOs.Oferta;
import com.app.anra.crauler.model.VOs.Tweet;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

// TODO: Auto-generated Javadoc
/**
 * The Interface MongoFunctions.
 */
public interface MongoFunctions extends MongoRepository<Oferta, String> {

	/**
	 * Insertar en BD.
	 *
	 * @param ofertasInfoEmpleo
	 *            the ofertas info empleo
	 * @param mongoOperation
	 *            the mongo operation
	 */
	public static void insertarEnBD(ArrayList<Oferta> ofertasInfoEmpleo, MongoOperations mongoOperation) {

		for (Oferta oferta : ofertasInfoEmpleo)
			mongoOperation.save(oferta);
	}

	public static void insertarEmpresasEnBD(ArrayList<Empresa> empresas, MongoOperations mongoOperation) {

		for (Empresa e : empresas)
			if (e.getTweets() != null)
				mongoOperation.save(e);
	}

	/**
	 * Map reduce.
	 *
	 * @param mongoOperation
	 *            the mongo operation
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @param c
	 *            the c
	 * @param d
	 *            the d
	 * @throws UnknownHostException
	 */
	static void calcularLocalizaciones() throws UnknownHostException {

		String map = "function () { emit(this.localizacion, {count: 1}); }";
		String reduce = " function(key, values) { var result = 0; values.forEach(function(value){ result++ }); "
				+ "return result; }";

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("craulerdb");
		DBCollection ofertas = db.getCollection("ofertas");

		MapReduceCommand cmd = new MapReduceCommand(ofertas, map, reduce, null, MapReduceCommand.OutputType.INLINE, null);
		MapReduceOutput out = ofertas.mapReduce(cmd);
		
		for (DBObject o : out.results()) {
			System.out.println(o.toString());
		}
	}
	
	static void calcularMedia() throws UnknownHostException {

		String map = "function () { var empresa = this; empresa.tweets.forEach(function(item){ "
				+ "emit(empresa.name, item.valoracion); });}";
		
		String reduce = "function(key, values) { var count = 0; var media = 0; values.forEach(function(value){ "
				+ "if (value != 'NONE'){ count++; if(value == 'P+') media = media + 5; if(value == 'P') media = media + 4;"
						+ "if(value == 'NEU') media = media + 3; if(value == 'N') media = media + 2;"
								+ "if(value == 'N+') media = media + 1;}}); if (count != 0) media = Math.round(media/count);"
								+ "if(media == 1) return 'N+'; if(media == 2) return 'N'; if(media == 3) return 'NEU';"
								+ "if(media == 4) return 'P'; if(media == 5) return 'P+'; else return 'NONE'}";

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("craulerdb");
		DBCollection empresas = db.getCollection("empresas");

		MapReduceCommand cmd = new MapReduceCommand(empresas, map, reduce, null, MapReduceCommand.OutputType.INLINE, null);

		MapReduceOutput out = empresas.mapReduce(cmd);
		    for (DBObject o : out.results()) {
		        System.out.println(o.toString());
		}
	}

	/**
	 * Find all.
	 *
	 * @param mongoOperation
	 *            the mongo operation
	 * @return the array list
	 */
	static ArrayList<Oferta> findAll(MongoOperations mongoOperation) {

		return (ArrayList<Oferta>) mongoOperation.findAll(Oferta.class);
	}

	/**
	 * Drop DB.
	 *
	 * @param mongoOperation
	 *            the mongo operation
	 */
	static void dropDB(MongoOperations mongoOperation) {
		mongoOperation.dropCollection(Oferta.class);

	}

}
