package models;

import java.io.Serializable;

public class CreditCardBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CreditCardBean() {
		
	}
	
	public String getNumeroCarta() {
		return numeroCarta;
	}
	
	public void setNumeroCarta(String numeroCarta) {
		this.numeroCarta = numeroCarta;
	}
	
	public String getCodiceSicurezza() {
		return codiceSicurezza;
	}
	
	public void setCodiceSicurezza(String codiceSicurezza) {
		this.codiceSicurezza = codiceSicurezza;
	}
	
	public String getScadenza() {
		return scadenza;
	}
	
	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}
	
	public String getIntestatario() {
		return intestatario;
	}
	
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	private String numeroCarta;
	private String codiceSicurezza;
	private String scadenza;
	private String intestatario;
	private String account;
} 