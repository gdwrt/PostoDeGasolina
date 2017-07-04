package com.postoGasolina.model;

public class Telefone {
	
	private int id_responsavel_telefone;
	private String telefone;
	
	public Telefone(int id_responsavel_telefone, String telefone) {
		this.id_responsavel_telefone = id_responsavel_telefone;
		this.telefone = telefone;
	}
	@Override
	public String toString() {
		return telefone;
	}
	public int getId_responsavel_telefone() {
		return id_responsavel_telefone;
	}
	public void setId_responsavel_telefone(int id_responsavel_telefone) {
		this.id_responsavel_telefone = id_responsavel_telefone;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
}
