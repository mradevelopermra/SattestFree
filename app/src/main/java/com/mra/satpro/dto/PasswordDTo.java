package com.mra.satpro.dto;

public class PasswordDTo {


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public String getEstatus() {
		return Estatus;
	}
	public void setEstatus(String estatus) {
		Estatus = estatus;
	}
	int id;
	String Nombre;
	String Password;
	String Mail;
	String Estatus;


}
