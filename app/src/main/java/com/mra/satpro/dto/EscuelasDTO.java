package com.mra.satpro.dto;

public class EscuelasDTO {



	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEscuelaLicenciatura() {
		return escuelaLicenciatura;
	}

	public void setEscuelaLicenciatura(String escuelaLicenciatura) {
		this.escuelaLicenciatura = escuelaLicenciatura;
	}

	public int getAciertos() {
		return aciertos;
	}

	public void setAciertos(int aciertos) {
		this.aciertos = aciertos;
	}

	String escuelaLicenciatura;
	int aciertos;



}
