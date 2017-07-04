package com.postoGasolina.model;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.security.acl.WorldGroupImpl;

public class Cliente_juridica {
	private int id_cliente_juridica;
	private Endereco endereco;
	private String nome;
	private String cnpj;
	private String situacao;
	private LocalDate data_situacao;
	private String ie;
	private String email;
	private String observacao;
	
	private ObservableList<Telefone> listaTelefone = FXCollections.observableArrayList();

	public Cliente_juridica(int id_cliente_juridica, Endereco endereco, String nome, String cnpj, String situacao,
			LocalDate data_situacao, String ie, String email, String observacao,
			ObservableList<Telefone> listaTelefone) {
		this.id_cliente_juridica = id_cliente_juridica;
		this.endereco = endereco;
		this.nome = WordUtils.capitalize(nome.toLowerCase()); 
		this.cnpj = cnpj;
		this.situacao = situacao;
		this.data_situacao = data_situacao;
		this.ie = ie;
		this.email = email;
		this.observacao = observacao;
		this.listaTelefone = listaTelefone;
	}

	public int getId_cliente_juridica() {
		return id_cliente_juridica;
	}

	public void setId_cliente_juridica(int id_cliente_juridica) {
		this.id_cliente_juridica = id_cliente_juridica;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public LocalDate getData_situacao() {
		return data_situacao;
	}

	public void setData_situacao(LocalDate data_situacao) {
		this.data_situacao = data_situacao;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public ObservableList<Telefone> getListaTelefone() {
		return listaTelefone;
	}

	public void setListaTelefone(ObservableList<Telefone> listaTelefone) {
		this.listaTelefone = listaTelefone;
	}
}
