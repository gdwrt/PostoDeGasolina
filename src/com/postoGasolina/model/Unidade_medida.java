package com.postoGasolina.model;

public class Unidade_medida {
	private int id_unidade_medida;
	private String nome;
	
	public Unidade_medida(int id_unidade_medida, String nome) {
		this.id_unidade_medida = id_unidade_medida;
		this.nome = nome;
	}
	@Override
	public String toString() {
		return nome;
	}
	public int getId_unidade_medida() {
		return id_unidade_medida;
	}
	public void setId_unidade_medida(int id_unidade_medida) {
		this.id_unidade_medida = id_unidade_medida;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
}
