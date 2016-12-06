package com.app.anra.crauler.model.VOs;

public class Tweet {
	
	private String texto;
	private String valoracion;
	
	public Tweet(String texto) {

		this.texto = texto;
		this.valoracion = "NONE";
	}

	public String getTexto() {
		return texto;
	}

	public String getValoracion() {
		return valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getTexto() + "\n"; 
	}
}
