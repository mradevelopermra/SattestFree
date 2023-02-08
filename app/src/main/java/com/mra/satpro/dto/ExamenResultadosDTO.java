package com.mra.satpro.dto;

public class ExamenResultadosDTO {





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getPreguntaImagen() {
		return preguntaImagen;
	}

	public void setPreguntaImagen(String preguntaImagen) {
		this.preguntaImagen = preguntaImagen;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getCorrecta() {
		return correcta;
	}

	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltipImagen() {
		return tooltipImagen;
	}

	public void setTooltipImagen(String tooltipImagen) {
		this.tooltipImagen = tooltipImagen;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getAciertos() {
		return aciertos;
	}

	public void setAciertos(double aciertos) {
		this.aciertos = aciertos;
	}


	int id;
	String pregunta;
	String preguntaImagen;
	String respuesta;
	String correcta;
	String tooltip;
	String tooltipImagen;
	String materia;
	String fecha;
	double aciertos;



}
