package com.postoGasolina.model;

public class Bico {
	private int id_bico;
	private int id_bomba;
	private String descricao;
	
	public Bico(int id_bico, int id_bomba, String descricao) {
		this.id_bico = id_bico;
		this.id_bomba = id_bomba;
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

	public int getId_bico() {
		return id_bico;
	}

	public void setId_bico(int id_bico) {
		this.id_bico = id_bico;
	}

	public int getId_bomba() {
		return id_bomba;
	}

	public void setId_bomba(int id_bomba) {
		this.id_bomba = id_bomba;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
