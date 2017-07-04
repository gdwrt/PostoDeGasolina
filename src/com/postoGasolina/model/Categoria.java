package com.postoGasolina.model;

public class Categoria {
	private int id_categoria;
	private String nome;
	
	public Categoria(int id_categoria, String nome) {
		super();
		this.id_categoria = id_categoria;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	public int getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
