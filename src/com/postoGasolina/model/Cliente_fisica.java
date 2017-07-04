package com.postoGasolina.model;

import java.sql.SQLException;

import com.postoGasolina.dao.ClienteFisicaDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente_fisica {

	private int id_cliente_fisica;
	private Pessoa pessoa;
	private Endereco endereco;
	private String pai;
	private String mae;
	private String email;
	private String informacao;
	private ObservableList<Telefone> listaTelefone = FXCollections.observableArrayList();
	
	public Cliente_fisica(int id_cliente_fisica,Pessoa pessoa, Endereco endereco){
		this.id_cliente_fisica = id_cliente_fisica;
		this.pessoa = pessoa;
		this.endereco = endereco;
	}
	public Cliente_fisica(int id_cliente_fisica, Pessoa pessoa, Endereco endereco, String pai, String mae, String email,
			String informacao, ObservableList<Telefone> listaTelefone) {
		this.id_cliente_fisica = id_cliente_fisica;
		this.pessoa = pessoa;
		this.endereco = endereco;
		this.pai = pai;
		this.mae = mae;
		this.email = email;
		this.informacao = informacao;
		this.listaTelefone = listaTelefone;
	}

	@Override
	public String toString() {
		return "Cliente_fisica [id_cliente_fisica=" + id_cliente_fisica + ", pessoa=" + pessoa + ", endereco="
				+ endereco + ", pai=" + pai + ", mae=" + mae + ", email=" + email + ", informacao=" + informacao + "]";
	}

	public int getId_cliente_fisica() {
		return id_cliente_fisica;
	}

	public void setId_cliente_fisica(int id_cliente_fisica) {
		this.id_cliente_fisica = id_cliente_fisica;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInformacao() {
		return informacao;
	}

	public void setInformacao(String informacao) {
		this.informacao = informacao;
	}

	public ObservableList<Telefone> getListaTelefone() {
		return listaTelefone;
	}

	public void setListaTelefone(ObservableList<Telefone> listaTelefone) {
		this.listaTelefone = listaTelefone;
	}

	public static byte cadastrar(Cliente_fisica clienteFisica) {
		// Validaçoes

		try {
			new ClienteFisicaDao().cadastrar(clienteFisica);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static byte alterar(Cliente_fisica clienteFisica) {
		// Validaçoes

		try {
			new ClienteFisicaDao().alterar(clienteFisica);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
