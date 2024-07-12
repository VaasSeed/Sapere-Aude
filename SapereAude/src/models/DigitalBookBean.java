package models;

import java.io.Serializable;

public class DigitalBookBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public DigitalBookBean() {
		
	}
	
	public String getISBNOpera() {
		return ISBNOpera;
	}
	
	public void setISBNOpera(String ISBNOpera) {
		this.ISBNOpera = ISBNOpera;
	}
	
	/*public bytes[] getBookFile() {
		return bookFile;
	}
	
	public void setBookFile(bytes[] bookFile) {
		this.bookFile = bookFile;
	}*/
	
	public int getNumPagine() {
		return numPagine;
	}
	
	public void setNumPagine(int numPagine) {
		this.numPagine = numPagine;
	}
	
	public String getLingua() {
		return lingua;
	}
	
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	
	
	public double getCostoAcquisto() {
		return costoAcquisto;
	}
	
	public void setCostoAcquisto(double costoAcquisto) {
		this.costoAcquisto = costoAcquisto;
	}
	
	public double getCostoNoleggio() {
		return costoNoleggio;
	}
	
	public void setCostoNoleggio(double costoNoleggio) {
		this.costoNoleggio = costoNoleggio;
	}
	
	
	private String ISBNOpera;
//	private bytes[] bookFile;
	private int numPagine;
	private String lingua;
	private double costoAcquisto;
	private double costoNoleggio;
}