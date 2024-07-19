package models;

import java.io.InputStream;
import java.io.Serializable;


public class AudioBookBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public AudioBookBean() {
		
	}
	
	public String getISBNOpera() {
		return ISBNOpera;
	}
	
	public void setISBNOpera(String ISBNOpera) {
		this.ISBNOpera = ISBNOpera;
	}
	
	public InputStream getAudioFile() {
		return audioFile;
	}
	
	public void setAudioFile(InputStream audioFile) {
		this.audioFile = audioFile;
	}
	
	public String getDurata() {
		return durata;
	}
	
	public void setDurata(String durata) {
		this.durata = durata;
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
	private InputStream audioFile;
	private String durata;
	private String lingua;
	private double costoAcquisto;
	private double costoNoleggio;
}