package com.mra.satpro.dto;

public class PlanEstudiosDTO {






	int id;
	String materia;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public int getNopreguntas() {
		return nopreguntas;
	}

	public void setNopreguntas(int nopreguntas) {
		this.nopreguntas = nopreguntas;
	}

	public int getAciertosobtenidos() {
		return aciertosobtenidos;
	}

	public void setAciertosobtenidos(int aciertosobtenidos) {
		this.aciertosobtenidos = aciertosobtenidos;
	}

	int nopreguntas;
	int aciertosobtenidos;



}
