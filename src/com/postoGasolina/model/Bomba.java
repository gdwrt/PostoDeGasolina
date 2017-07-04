package com.postoGasolina.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Bomba {
	private int id_bomba;
	private String descricao;
	private ObservableList<Bico> lista_bicos = FXCollections.observableArrayList();
	
	public Bomba(int id_bomba, String descricao, ObservableList<Bico> lista_bicos) {
		this.id_bomba = id_bomba;
		this.descricao = descricao;
		this.lista_bicos = lista_bicos;
	}
	
	
	
	@Override
	public String toString() {
		return descricao;
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
	public ObservableList<Bico> getLista_bicos() {
		return lista_bicos;
	}
	public void setLista_bicos(ObservableList<Bico> lista_bicos) {
		this.lista_bicos = lista_bicos;
	}	
}
