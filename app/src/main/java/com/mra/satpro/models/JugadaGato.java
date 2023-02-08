package com.mra.satpro.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JugadaGato {

    private String jugadorUnoId;
    private String jugadorDosId;
    private List<Integer> celdasSeleccionadas;
    private boolean turnoJugadorUno;
    private String ganadorId;
    private Date creaFechaPartida;
    private String abandonaPartida;
    private int gift;
    private int giftDos;
    private String mensaje;
    public byte[] urlFoto;
    public byte[] urlFotoTwo;
    private String UriPhoto;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGift() {
        return gift;
    }

    public void setGift(int gift) {
        this.gift = gift;
    }

    public int getGiftDos() {
        return giftDos;
    }

    public void setGiftDos(int giftDos) {
        this.giftDos = giftDos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public byte[] getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(byte[] urlFoto) {
        this.urlFoto = urlFoto;
    }

    public byte[] getUrlFotoTwo() {
        return urlFotoTwo;
    }

    public void setUrlFotoTwo(byte[] urlFotoTwo) {
        this.urlFotoTwo = urlFotoTwo;
    }

    public String getUriPhoto() {
        return UriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        UriPhoto = uriPhoto;
    }

    public JugadaGato() {
    }

    public JugadaGato(String jugadorUnoId, String token) {
        this.token = token;
        this.jugadorUnoId = jugadorUnoId;
        this.jugadorDosId = "";
        this.celdasSeleccionadas = new ArrayList<>();
        for(int i=0; i<9; i++) {
            this.celdasSeleccionadas.add(new Integer(0));
        }
        this.turnoJugadorUno = true;
        this.creaFechaPartida = new Date();
        this.ganadorId = "";
        this.abandonaPartida = "";
    }

    public JugadaGato(String jugadorUnoId) {
        this.jugadorUnoId = jugadorUnoId;
        this.jugadorDosId = "";
        this.celdasSeleccionadas = new ArrayList<>();
        for(int i=0; i<9; i++) {
            this.celdasSeleccionadas.add(new Integer(0));
        }
        this.turnoJugadorUno = true;
        this.creaFechaPartida = new Date();
        this.ganadorId = "";
        this.abandonaPartida = "";
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

    public List<Integer> getCeldasSeleccionadas() {
        return celdasSeleccionadas;
    }

    public void setCeldasSeleccionadas(List<Integer> celdasSeleccionadas) {
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
}
