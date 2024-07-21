package models;

import java.io.Serializable;

public class ReviewBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ReviewBean() {
		
	}
	
	public int getIdRecensione() {
		return idRecensione;
	}
	
	public void setIdRecensione(int idRecensione) {
		this.idRecensione = idRecensione;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public String getTesto() {
		return testo;
	}
	
	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getISBNOpera() {
		return ISBNOpera;
	}
	
	public void setISBNOpera(String ISBNOpera) {
		this.ISBNOpera = ISBNOpera;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	
	private int idRecensione;
	private String titolo;
	private String testo;
	private String data;
	private String ISBNOpera;
	private String account;
} 