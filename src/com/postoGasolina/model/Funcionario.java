package com.postoGasolina.model;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Funcionario {
	
	private int id_funcionario;
	private Pessoa pessoa;
	private Cargo cargo;
	private Endereco endereco;
	private String status;
	private String email;
	private LocalDate data_admissao;
	private LocalDate data_demissao;
	private String observacao;
	private ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
		
	public Funcionario(int id_funcionario, Pessoa pessoa, Cargo cargo, Endereco endereco, String status, String email,
			LocalDate data_admissao, LocalDate data_demissao, String observacao,
			ObservableList<Telefone> lista_telefones) {
		this.id_funcionario = id_funcionario;
		this.pessoa = pessoa;
		this.cargo = cargo;
		this.endereco = endereco;
		this.status = status;
		this.email = email;
		this.data_admissao = data_admissao;
		this.data_demissao = data_demissao;
		this.observacao = observacao;
		this.lista_telefones = lista_telefones;
	}
	
	@Override
	public String toString() {
		return pessoa.getNome();
	}
	
	public int getId_funcionario() {
		return id_funcionario;
	}
	public void setId_funcionario(int id_funcionario) {
		this.id_funcionario = id_funcionario;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getData_admissao() {
		return data_admissao;
	}
	public void setData_admissao(LocalDate data_admissao) {
		this.data_admissao = data_admissao;
	}
	public LocalDate getData_demissao() {
		return data_demissao;
	}
	public void setData_demissao(LocalDate data_demissao) {
		this.data_demissao = data_demissao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public ObservableList<Telefone> getLista_telefones() {
		return lista_telefones;
	}
	public void setLista_telefones(ObservableList<Telefone> lista_telefones) {
		this.lista_telefones = lista_telefones;
	}
	
	
}
