package com.mra.satpro.models;

public class UsuarioNew {

    private String nick;
    private String mail;
    private String pwd;
    private int juegosGanados;
    private int identificadorComodin;
    private double dineroRegalado;
    private double dineroGanado;
    private double dineroComprado;
    private String cuentaClabe;
    private String uriPhoto;
    private String token;
    private String boleto;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getJuegosGanados() {
        return juegosGanados;
    }

    public void setJuegosGanados(int juegosGanados) {
        this.juegosGanados = juegosGanados;
    }

    public int getIdentificadorComodin() {
        return identificadorComodin;
    }

    public void setIdentificadorComodin(int identificadorComodin) {
        this.identificadorComodin = identificadorComodin;
    }

    public double getDineroRegalado() {
        return dineroRegalado;
    }

    public void setDineroRegalado(double dineroRegalado) {
        this.dineroRegalado = dineroRegalado;
    }

    public double getDineroGanado() {
        return dineroGanado;
    }

    public void setDineroGanado(double dineroGanado) {
        this.dineroGanado = dineroGanado;
    }

    public double getDineroComprado() {
        return dineroComprado;
    }

    public void setDineroComprado(double dineroComprado) {
        this.dineroComprado = dineroComprado;
    }

    public String getCuentaClabe() {
        return cuentaClabe;
    }

    public void setCuentaClabe(String cuentaClabe) {
        this.cuentaClabe = cuentaClabe;
    }

    public String getBoleto() {
        return boleto;
    }

    public void setBoleto(String boleto) {
        this.boleto = boleto;
    }

    public UsuarioNew() {
    }

    public UsuarioNew(double dineroRegalado) {
        this.dineroRegalado = dineroRegalado;
    }

    public UsuarioNew(double dineroRegalado, double dineroGanado, double dineroComprado, String cuentaClabe) {
        this.dineroRegalado = dineroRegalado;
        this.dineroGanado = dineroGanado;
        this.dineroComprado = dineroComprado;
        this.cuentaClabe = cuentaClabe;
    }

    public UsuarioNew(String nick, String mail, String pwd, int juegosGanados, int identificadorComodin,
                      double dineroRegalado, double dineroGanado, double dineroComprado, String cuentaClabe, String boleto) {
        this.nick = nick;
        this.mail = mail;
        this.pwd = pwd;
        this.juegosGanados = juegosGanados;
        this.identificadorComodin = identificadorComodin;
        this.dineroRegalado = dineroRegalado;
        this.dineroGanado = dineroGanado;
        this.dineroComprado = dineroComprado;
        this.cuentaClabe = cuentaClabe;
        this.boleto = boleto;
    }
}
