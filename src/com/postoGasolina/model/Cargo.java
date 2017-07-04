package com.postoGasolina.model;

public class Cargo {
	
	private int id_cargo;
	private String nome;
	
	public Cargo(int id_cargo, String nome) {
		this.id_cargo = id_cargo;
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}

	public int getId_cargo() {
		return id_cargo;
	}
	public void setId_cargo(int id_cargo) {
		this.id_cargo = id_cargo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
}
