package com.app.anra.crauler.model.VOs;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Twitter.
 */
public class InfoJobsVO {

	/** The ofertas. */
	private ArrayList<Oferta> ofertas = new ArrayList<Oferta>();

	/** The empresas. */
	private ArrayList<Empresa> empresas = new ArrayList<Empresa>();;

	/**
	 * Instantiates a new twitter.
	 *
	 * @param ofertas the ofertas
	 * @param empresas the empresas
	 */
	public InfoJobsVO(ArrayList<Oferta> ofertas, ArrayList<Empresa> empresas) {
		super();
		this.ofertas = ofertas;
		this.empresas = empresas;
	}

	/**
	 * Instantiates a new twitter.
	 */
	public InfoJobsVO() {
	}

	/**
	 * Gets the ofertas.
	 *
	 * @return the ofertas
	 */
	public ArrayList<Oferta> getOfertas() {
		return ofertas;
	}

	/**
	 * Sets the ofertas.
	 *
	 * @param ofertas the new ofertas
	 */
	public void setOfertas(ArrayList<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	/**
	 * Gets the empresas.
	 *
	 * @return the empresas
	 */
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}

	/**
	 * Sets the empresas.
	 *
	 * @param empresas the new empresas
	 */
	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	/**
	 * Adds the oferta.
	 *
	 * @param oferta the oferta
	 */
	public void addOferta(Oferta oferta){
		this.ofertas.add(oferta);
	}
	
	/**
	 * Adds the empresa.
	 *
	 * @param empresa the empresa
	 */
	public void addEmpresa(Empresa empresa){
		this.empresas.add(empresa);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Twitter [ofertas=" + ofertas + ", empresas=" + empresas + "]";
	}

}
