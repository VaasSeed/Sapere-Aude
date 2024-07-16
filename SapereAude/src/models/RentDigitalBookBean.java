package models;

import java.io.Serializable;

public class RentDigitalBookBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public RentDigitalBookBean() {
		
	}
	
	public int setIdNoleggio(int idNoleggio) {
		return this.idNoleggio = idNoleggio;
	}
	
	public int getIdNoleggio() {
		return idNoleggio;
	}
	
	public String getDataInizioNoleggio() {
		return dataInizioNoleggio;
	}
	
	public void setDataInizioNoleggio(String dataInizioNoleggio) {
		this.dataInizioNoleggio = dataInizioNoleggio;
	}
	
	public String getDataFineNoleggio() {
		return dataFineNoleggio;
	}
	
	public void setDataFineNoleggio(String dataFineNoleggio) {
		this.dataFineNoleggio = dataFineNoleggio;
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
	
	private int idNoleggio;
	private String dataInizioNoleggio;
	private String dataFineNoleggio;
	private String tipoOpera;
	private String ISBNOpera;
	private String nomeOpera;
	private int ordine;
	private double costo;
} 