package models;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UserBean() {
		
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDataDiNascita() {
		return dataDiNascita;
	}
	
	public void setDataDiNascita(String bDate) {
		this.dataDiNascita = bDate;
	}
	
	public boolean isAmministratore() {
		return amministratore;
	}
	
	public void setAmministratore(boolean amministratore) {
		this.amministratore = amministratore;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private String dataDiNascita;
	private boolean amministratore;
	private boolean valid;
}