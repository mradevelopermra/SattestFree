package com.mra.satpro.models;

public class Vidas {
	private int id;
	private int numeroVidas;
	private String fecha;
	private String estatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroVidas() {
		return numeroVidas;
	}

	public void setNumeroVidas(int numeroVidas) {
		this.numeroVidas = numeroVidas;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

}
