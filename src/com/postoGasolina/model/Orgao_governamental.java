package com.postoGasolina.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Orgao_governamental {

	private int id_orgao;
	private Endereco endereco;
	private String nome;
	private String sigla;
	private String observacao;
	private String email;
	private String cnpj;

	private ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
	
	@Override
	public String toString() {
		return nome;
	}

	public Orgao_governamental(int id_orgao, Endereco endereco, String nome, String sigla, String observacao,
			String email, String cnpj, ObservableList<Telefone> lista_telefones) {
		super();
		this.id_orgao = id_orgao;
		this.endereco = endereco;
		this.nome = nome;
		this.sigla = sigla;
		this.observacao = observacao;
		this.email = email;
		this.cnpj = cnpj;
		this.lista_telefones = lista_telefones;
	}

	public int getId_orgao() {
		return id_orgao;
	}
	public void setId_orgao(int id_orgao) {
		this.id_orgao = id_orgao;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public ObservableList<Telefone> getLista_telefones() {
		return lista_telefones;
	}

	public void setLista_telefones(ObservableList<Telefone> lista_telefones) {
		this.lista_telefones = lista_telefones;
	}
}
