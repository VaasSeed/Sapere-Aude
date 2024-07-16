package models;

import java.io.Serializable;

public class BuyDigitalBookBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public BuyDigitalBookBean() {
		
	}
	
	public int setIdAcquisto(int idAcquisto) {
		return this.idAcquisto = idAcquisto;
	}
	
	public int getIdAcquisto() {
		return idAcquisto;
	}
	
	public String getDataAcquisto() {
		return dataAcquisto;
	}
	
	public void setDataAcquisto(String dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}
	
	public String getTipoOpera() {
		return tipoOpera;
	}
	
	public void setTipoOpera(String tipoOpera) {
		this.tipoOpera = tipoOpera;
	}
	
	public String getISBNOpera() {
		return ISBNOpera;
	}
	
	public void setISBNOpera(String ISBNOpera) {
		this.ISBNOpera = ISBNOpera;
	}
	
	public String getNomeOpera() {
		return nomeOpera;
	}
	
	public void setNomeOpera(String nomeOpera) {
		this.nomeOpera = nomeOpera;
	}
	
	public int getOrdine() {
		return ordine;
	}
	
	public void setOrdine(int ordine) {
		this.ordine = ordine;
	}
	
	public double getCosto() {
		return costo;
	}
	
	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	private int idAcquisto;
	private String dataAcquisto;
	private String tipoOpera;
	private String ISBNOpera;
	private String nomeOpera;
	private int ordine;
	private double costo;
} 