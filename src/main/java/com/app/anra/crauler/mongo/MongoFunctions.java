package com.app.anra.crauler.mongo;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.anra.crauler.model.VOs.Empresa;
import com.app.anra.crauler.model.VOs.Oferta;

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
	 */
	static void mapReduce(MongoOperations mongoOperation, Object a, Object b, Object c, Object d) {
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
