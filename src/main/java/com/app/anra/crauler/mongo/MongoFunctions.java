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
	static void calcularMedia(MongoOperations mongoOperation, List<Empresa> empresas) throws UnknownHostException {

		String map = "function () { var tweet = this; emit(tweet.valoracion, { count: 1, valoracionTotal: tweet.valoracion });};";

		String reduce = " function(key, values)  var result = {count: 0, valoracionTotal: 0 };  values.forEach(function(value)if (result.valoracionTotal != 404){result.count += value.count; result.valoracionTotal += value.valoracionTota});return result;}";

		String finalize = " function(key, value){" + " value.average = value.valoracionTotal / value.count;"
				+ "if (value.average <= 2 OR value.average >1)" + "	value.average = 2;"
				+ " if (value.average <= 1 OR value.average >0)" + " 	value.average = 1;"
				+ " if (value.average <= 0 OR value.average >-1)" + " 	value.average = 0;"
				+ "  if (value.average <= -1 OR value.average >-2)" + " 	value.average = -1;"
				+ "  if (value.average <= -2 OR value.average >-3)" + "  	value.average = 2;"
				+ "  if (value.average <= 404 OR value.average >2)" + " 	value.average = 404;   " + "  return value;"
				+ " }" + "}";

		MongoClient mongoClient = new MongoClient("localhost", 27017);

		DB db = mongoClient.getDB("craulerdb");
		;

		DBCollection empresasData = db.getCollection("empresas");

		for (Empresa e : empresas) {
			for (Tweet t : e.getTweets()) {
				MapReduceResults<Tweet> results = mongoOperation.mapReduce(empresasData.toString(), map, reduce, new MapReduceOptions()
						.outputCollection("map_reduce_java_test").outputTypeReplace().finalizeFunction(finalize),
						Tweet.class);
				for (Tweet valueObject : results) {
					System.out.println(valueObject);
				}
			}

		}

		// mongoOperation.mapReduce(a, b, c, d);
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
