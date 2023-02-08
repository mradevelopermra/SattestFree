package com.mra.satpro.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Jugada {

    private String jugadorUnoId;
    private String jugadorDosId;
    private List<String> celdasSeleccionadas;
    private boolean turnoJugadorUno;
    private String ganadorId;
    private Date creaFechaPartida;
    private String abandonaPartida;
    private String pregunta;
    private String respuesta;
    public byte[] urlFoto;
    public byte[] urlFotoTwo;
    private String UriPhoto;
    private String token;
    private String mensajeDos;

    public String getMensajeDos() {
        return mensajeDos;
    }

    public void setMensajeDos(String mensajeDos) {
        this.mensajeDos = mensajeDos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUriPhoto() {
        return UriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        UriPhoto = uriPhoto;
    }

    public byte[] getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(byte[] urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private int gift;
    private int giftDos;

    public int getGiftDos() {
        return giftDos;
    }

    public void setGiftDos(int giftDos) {
        this.giftDos = giftDos;
    }

    public int getGift() {
        return gift;
    }

    public void setGift(int gift) {
        this.gift = gift;
    }

    private String mensaje;

    public Jugada() {
    }

    public Jugada(String jugadorUnoId, String question, String answer) {
        this.jugadorUnoId = jugadorUnoId;
        this.jugadorDosId = "";
        this.celdasSeleccionadas = new ArrayList<>();
        for(int i=0; i<41; i++) {
            this.celdasSeleccionadas.add(new String(""));
        }
        this.turnoJugadorUno = true;
        this.creaFechaPartida = new Date();
        this.ganadorId = "";
        this.abandonaPartida = "";
        this.pregunta = question;
        this.respuesta = answer;
    }

    public Jugada(String jugadorUnoId, String question, String answer, String token) {
        this.token = token;
        this.jugadorUnoId = jugadorUnoId;
        this.jugadorDosId = "";
        this.celdasSeleccionadas = new ArrayList<>();
        for(int i=0; i<41; i++) {
            this.celdasSeleccionadas.add(new String(""));
        }
        this.turnoJugadorUno = true;
        this.creaFechaPartida = new Date();
        this.ganadorId = "";
        this.abandonaPartida = "";
        this.pregunta = question;
        this.respuesta = answer;
    }

    public String getJugadorUnoId() {
        return jugadorUnoId;
    }

    public void setJugadorUnoId(String jugadorUnoId) {
        this.jugadorUnoId = jugadorUnoId;
    }

    public String getJugadorDosId() {
        return jugadorDosId;
    }

    public void setJugadorDosId(String jugadorDosId) {
        this.jugadorDosId = jugadorDosId;
    }

    public List<String> getCeldasSeleccionadas() {
        return celdasSeleccionadas;
    }

    public void setCeldasSeleccionadas(List<String> celdasSeleccionadas) {
        this.celdasSeleccionadas = celdasSeleccionadas;
    }

    public boolean isTurnoJugadorUno() {
        return turnoJugadorUno;
    }

    public void setTurnoJugadorUno(boolean turnoJugadorUno) {
        this.turnoJugadorUno = turnoJugadorUno;
    }

    public String getGanadorId() {
        return ganadorId;
    }

    public void setGanadorId(String ganadorId) {
        this.ganadorId = ganadorId;
    }

    public Date getCreaFechaPartida() {
        return creaFechaPartida;
    }

    public void setCreaFechaPartida(Date creaFechaPartida) {
        this.creaFechaPartida = creaFechaPartida;
    }

    public String getAbandonaPartida() {
        return abandonaPartida;
    }

    public void setAbandonaPartida(String abandonaPartida) {
        this.abandonaPartida = abandonaPartida;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
