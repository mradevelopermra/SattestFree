package com.mra.satpro.models;

import java.util.Date;

public class EstudiaBoth {

    private int IdPregunta;
    private int IdRespuesta;
    private String abandonaPartida;
    private Date creaFechaPartida;
    private String ganadorId;
    private String jugadorDosId;
    private String jugadorUnoId;
    private boolean turnoJugadorUno;
    private String token;

    public EstudiaBoth() {
    }

    public EstudiaBoth(String jugadorUnoId, int question, int answer) {
        this.jugadorUnoId = jugadorUnoId;
        this.jugadorDosId = "";
        this.turnoJugadorUno = true;
        this.creaFechaPartida = new Date();
        this.ganadorId = "";
        this.abandonaPartida = "";
        this.IdPregunta = question;
        this.IdRespuesta = answer;
    }

    public EstudiaBoth(String jugadorUnoId, int question, int answer, String token) {
        this.token = token;
        this.jugadorUnoId = jugadorUnoId;
        this.jugadorDosId = "";
        this.turnoJugadorUno = true;
        this.creaFechaPartida = new Date();
        this.ganadorId = "";
        this.abandonaPartida = "";
        this.IdPregunta = question;
        this.IdRespuesta = answer;
    }


    public int getIdPregunta() {
        return IdPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        IdPregunta = idPregunta;
    }

    public int getIdRespuesta() {
        return IdRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        IdRespuesta = idRespuesta;
    }

    public String getAbandonaPartida() {
        return abandonaPartida;
    }

    public void setAbandonaPartida(String abandonaPartida) {
        this.abandonaPartida = abandonaPartida;
    }

    public Date getCreaFechaPartida() {
        return creaFechaPartida;
    }

    public void setCreaFechaPartida(Date creaFechaPartida) {
        this.creaFechaPartida = creaFechaPartida;
    }

    public String getGanadorId() {
        return ganadorId;
    }

    public void setGanadorId(String ganadorId) {
        this.ganadorId = ganadorId;
    }

    public String getJugadorDosId() {
        return jugadorDosId;
    }

    public void setJugadorDosId(String jugadorDosId) {
        this.jugadorDosId = jugadorDosId;
    }

    public String getJugadorUnoId() {
        return jugadorUnoId;
    }

    public void setJugadorUnoId(String jugadorUnoId) {
        this.jugadorUnoId = jugadorUnoId;
    }

    public boolean isTurnoJugadorUno() {
        return turnoJugadorUno;
    }

    public void setTurnoJugadorUno(boolean turnoJugadorUno) {
        this.turnoJugadorUno = turnoJugadorUno;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
