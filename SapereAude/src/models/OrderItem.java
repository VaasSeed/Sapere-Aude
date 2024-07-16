package models;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public OrderItem() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getTipoOpera() {
		return tipoOpera;
	}
	
	public void setTipoOpera(String tipoOpera) {
		this.tipoOpera = tipoOpera;
	}
	
	
	public String getOperazione() {
		return operazione;
	}
	
	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	
	public double getCosto() {
		return costo;
	}
	
	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	int id;
	private String ISBNOpera;
	private String nomeOpera;
	private String tipoOpera;
	private String operazione;
	private double costo;
	private String foto;
}