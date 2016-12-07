package com.app.anra.crauler.model.VOs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// TODO: Auto-generated Javadoc
/**
 * The Class Oferta.
 */
/*
 * en obras...
 */
@Document(collection = "ofertas")
public class Oferta {
	
	/** The id. */
	@Id
	private String id;

	/** The nombre_oferta. */
	private String nombre_oferta;

	/** The nombre_empresa. */
	private String nombre_empresa;

	/** The localizacion. */
	private String localizacion;

	/** The descrp_oferta. */
	private String descrp_oferta;

	/** The responsabilidad. */
	private String responsabilidad;

	/** The tipo_jornada. */
	private String tipo_jornada;

	/** The experiencia. */
	private String experiencia;
	
	private String salario;
	
	private String jornada;
	
	private String contrato;

	/**
	 * Instantiates a new oferta.
	 */
	public Oferta() { }
	

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getSalario() {
		return salario;
	}


	public void setSalario(String salario) {
		this.salario = salario;
	}


	public String getJornada() {
		return jornada;
	}


	public void setJornada(String jornada) {
		this.jornada = jornada;
	}


	public String getContrato() {
		return contrato;
	}


	public void setContrato(String contrato) {
		this.contrato = contrato;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * Gets the nombre_oferta.
	 *
	 * @return the nombre_oferta
	 */
	public String getNombre_oferta() {
		return nombre_oferta;
	}

	/**
	 * Sets the nombre_oferta.
	 *
	 * @param nombre_oferta
	 *            the new nombre_oferta
	 */
	public void setNombre_oferta(String nombre_oferta) {
		this.nombre_oferta = nombre_oferta;
	}

	/**
	 * Gets the nombre_empresa.
	 *
	 * @return the nombre_empresa
	 */
	public String getNombre_empresa() {
		return nombre_empresa;
	}

	/**
	 * Sets the nombre_empresa.
	 *
	 * @param nombre_empresa
	 *            the new nombre_empresa
	 */
	public void setNombre_empresa(String nombre_empresa) {
		this.nombre_empresa = nombre_empresa;
	}

	/**
	 * Gets the localizacion.
	 *
	 * @return the localizacion
	 */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
	 * Sets the localizacion.
	 *
	 * @param localizacion
	 *            the new localizacion
	 */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	/**
	 * Gets the descrp_oferta.
	 *
	 * @return the descrp_oferta
	 */
	public String getDescrp_oferta() {
		return descrp_oferta;
	}

	/**
	 * Sets the descrp_oferta.
	 *
	 * @param descrp_oferta
	 *            the new descrp_oferta
	 */
	public void setDescrp_oferta(String descrp_oferta) {
		this.descrp_oferta = descrp_oferta;
	}

	/**
	 * Gets the responsabilidad.
	 *
	 * @return the responsabilidad
	 */
	public String getResponsabilidad() {
		return responsabilidad;
	}

	/**
	 * Sets the responsabilidad.
	 *
	 * @param responsabilidad
	 *            the new responsabilidad
	 */
	public void setResponsabilidad(String responsabilidad) {
		this.responsabilidad = responsabilidad;
	}

	/**
	 * Gets the tipo_jornada.
	 *
	 * @return the tipo_jornada
	 */
	public String getTipo_jornada() {
		return tipo_jornada;
	}

	/**
	 * Sets the tipo_jornada.
	 *
	 * @param tipo_jornada
	 *            the new tipo_jornada
	 */
	public void setTipo_jornada(String tipo_jornada) {
		this.tipo_jornada = tipo_jornada;
	}

	/**
	 * Gets the experiencia.
	 *
	 * @return the experiencia
	 */
	public String getExperiencia() {
		return experiencia;
	}

	/**
	 * Sets the experiencia.
	 *
	 * @param experiencia
	 *            the new experiencia
	 */
	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Oferta [nombre_oferta=" + nombre_oferta + ", nombre_empresa=" + nombre_empresa + ", localizacion="
				+ localizacion + ", descrp_oferta=" + descrp_oferta + ", responsabilidad=" + responsabilidad
				+ ", tipo_jornada=" + tipo_jornada + ", experiencia=" + experiencia + "]";
	}

}
