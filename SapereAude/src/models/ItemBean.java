package models;

import java.io.InputStream;
import java.io.Serializable;

public class ItemBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ItemBean() {
		
	}
	
	public String getISBN() {
		return isbn;
	}
	
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCasaEditrice() {
		return casaEditrice;
	}
	
	public void setCasaEditrice(String casaEditrice) {
		this.casaEditrice = casaEditrice;
	}
	
	public String getAutore() {
		return Autore;
	}
	
	public void setAutore(String Autore) {
		this.Autore = Autore;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public InputStream getFoto() {
		return foto;
	}
	
	public void setFoto(InputStream foto) {
		this.foto = foto;
	}
	
	private String isbn;
	private String nome;
	private String casaEditrice;
	private String Autore;
	private String categoria;
	private InputStream foto;
} 