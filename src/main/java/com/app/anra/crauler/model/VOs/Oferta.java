package com.app.anra.crauler.model.VOs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;

// TODO: Auto-generated Javadoc
/**
 * The Class Oferta.
 */

@Document(collection = "ofertas")
public class Oferta {
	
	@Transient
	private static SimpleDateFormat SDF = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", 
			new Locale("es", "ES"));
	
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

	/** The experiencia. */
	private String experiencia;
	
	private String salario;
	
	private String jornada;
	
	private String contrato;
	
	private String fecha;

	public String getFecha() {
		return fecha;
	}


	public void setFechaInfoJobs(String fecha) {
		this.fecha = fecha.replace("T", " ").replace(".000Z", "");
	}
	
	public void setFechaInfoEmpleo(String fecha){
		
		Calendar cal = Calendar.getInstance();

		if(fecha != null){
		
			fecha = fecha.toLowerCase().replace("hace", "")
										.replace("más de", "")
										.replace("menos de", "")
										.replace("una", "1")
										.replace("un", "1")
										.trim();
			
			int num = -Integer.parseInt(fecha.split("\\s+")[0].trim());
		
			if(fecha.contains("horas") || fecha.contains("hora")) cal.add(Calendar.HOUR, num);
			else if (fecha.contains("días") || fecha.contains("día")) cal.add(Calendar.DATE, num);
		}
		
		this.fecha = SDF.format(cal.getTime());
	}


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
		if(jornada != null) if(jornada.trim().isEmpty()) jornada = null;
		this.jornada = jornada;
	}


	public String getContrato() {
		return contrato;
	}


	public void setContrato(String contrato) {
		if(contrato != null) if(contrato.trim().isEmpty()) contrato = null;
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
		if(experiencia != null) if(experiencia.trim().isEmpty()) experiencia = null;
		this.experiencia = experiencia;
	}

	@Override
	public String toString() {
		return "Oferta [id=" + id + ", nombre_oferta=" + nombre_oferta + ", nombre_empresa=" + nombre_empresa
				+ ", localizacion=" + localizacion + ", descrp_oferta=" + descrp_oferta + ", experiencia=" + experiencia
				+ ", salario=" + salario + ", jornada=" + jornada + ", contrato=" + contrato + "]";
	}

}