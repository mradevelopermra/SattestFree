package com.mra.satpro.dto;

public class ExamenDTO {





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	String fecha;
	double totalPreguntas;
	double aciertos;
	String tipoTest;

	public String getTipoTest() {
		return tipoTest;
	}

	public void setTipoTest(String tipoTest) {
		this.tipoTest = tipoTest;
	}
}
