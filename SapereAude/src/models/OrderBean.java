package models;

import java.io.Serializable;

public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public OrderBean() {
		
	}
	
	public int getIdOrdine() {
		return idOrdine;
	}
	
	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public String getDataOrdine() {
		return dataOrdine;
	}
	
	public void setDataOrdine(String dataOrdine) {
		this.dataOrdine = dataOrdine;
	}
	
	public double getImportoTotale() {
		return importoTotale;
	}
	
	public void setImportoTotale(double importoTotale) {
		this.importoTotale = importoTotale;
	}
	
	public int getStato() {
		return stato;
	}
	
	public void setStato(int stato) {
		this.stato = stato;
	}
	
	public String getUtente() {
		return utente;
	}
	
	public void setUtente(String utente) {
		this.utente = utente;
	}
	
	public String getMetodoPagamento() {
		return metodoPagamento;
	}
	
	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
	
	private int idOrdine;
	private String dataOrdine;
	private double importoTotale;
	private int stato;
	private String utente;
	private String metodoPagamento;
} 