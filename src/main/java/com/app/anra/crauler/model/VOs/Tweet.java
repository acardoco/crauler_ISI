package com.app.anra.crauler.model.VOs;

public class Tweet {
	
	private String texto;
	private int valoracion;
	
	public Tweet(String texto) {

		this.texto = texto;
		this.valoracion = 404;
	}

	public String getTexto() {
		return texto;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getTexto() + "\n"; 
	}
}
