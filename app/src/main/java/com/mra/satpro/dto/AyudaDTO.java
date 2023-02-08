package com.mra.satpro.dto;

public class AyudaDTO {





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreModulo() {
		return nombreModulo;
	}

	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getTotalPreguntas() {
		return totalPreguntas;
	}

	public void setTotalPreguntas(double totalPreguntas) {
		this.totalPreguntas = totalPreguntas;
	}

	public double getAciertos() {
		return aciertos;
	}

	public void setAciertos(double aciertos) {
		this.aciertos = aciertos;
	}

	int id;
	String nombreModulo;
	String fecha;
	double totalPreguntas;
	double aciertos;



}
