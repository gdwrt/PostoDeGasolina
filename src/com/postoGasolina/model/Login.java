package com.postoGasolina.model;

import java.util.ArrayList;
import java.util.List;

public class Login {

	private int id_login;
	private Funcionario funcionario;
	private String email;
	private String senha;
	private boolean g_autorizacao_licenca;
	private boolean g_orgao;
	private boolean g_fornecedores;
	private boolean g_clientes;
	private boolean g_funcionario;
	private boolean g_fidelizacao;
	private boolean g_compra_produtos;
	private boolean g_venda_produtos;
	private boolean g_combustivel;
	private boolean g_produtos;
	private boolean g_caixa;
	private boolean g_permissoes;
	private String informacao_adicional;
	private String nivel_acesso;

	public Login(){};
	public Login(int id_login, Funcionario funcionario, String email, String senha, boolean g_autorizacao_licenca,
			boolean g_orgao, boolean g_fornecedores, boolean g_clientes, boolean g_funcionario, boolean g_fidelizacao,
			boolean g_compra_produtos, boolean g_venda_produtos, boolean g_combustivel, boolean g_produtos,
			boolean g_caixa, boolean g_permissoes, String informacao_adicional, String nivel_acesso) {
		
		this.id_login = id_login;
		this.funcionario = funcionario;
		this.email = email;
		this.senha = senha;
		this.g_autorizacao_licenca = g_autorizacao_licenca;
		this.g_orgao = g_orgao;
		this.g_fornecedores = g_fornecedores;
		this.g_clientes = g_clientes;
		this.g_funcionario = g_funcionario;
		this.g_fidelizacao = g_fidelizacao;
		this.g_compra_produtos = g_compra_produtos;
		this.g_venda_produtos = g_venda_produtos;
		this.g_combustivel = g_combustivel;
		this.g_produtos = g_produtos;
		this.g_caixa = g_caixa;
		this.g_permissoes = g_permissoes;
		this.informacao_adicional = informacao_adicional;
		this.nivel_acesso = nivel_acesso;
		
	}
	

	public int getId_login() {
		return id_login;
	}

	public void setId_login(int id_login) {
		this.id_login = id_login;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isG_autorizacao_licenca() {
		return g_autorizacao_licenca;
	}

	public void setG_autorizacao_licenca(boolean g_autorizacao_licenca) {
		this.g_autorizacao_licenca = g_autorizacao_licenca;
	}

	public boolean isG_orgao() {
		return g_orgao;
	}

	public void setG_orgao(boolean g_orgao) {
		this.g_orgao = g_orgao;
	}

	public boolean isG_fornecedores() {
		return g_fornecedores;
	}

	public void setG_fornecedores(boolean g_fornecedores) {
		this.g_fornecedores = g_fornecedores;
	}

	public boolean isG_clientes() {
		return g_clientes;
	}

	public void setG_clientes(boolean g_clientes) {
		this.g_clientes = g_clientes;
	}

	public boolean isG_funcionario() {
		return g_funcionario;
	}

	public void setG_funcionario(boolean g_funcionario) {
		this.g_funcionario = g_funcionario;
	}

	public boolean isG_fidelizacao() {
		return g_fidelizacao;
	}

	public void setG_fidelizacao(boolean g_fidelizacao) {
		this.g_fidelizacao = g_fidelizacao;
	}

	public boolean isG_compra_produtos() {
		return g_compra_produtos;
	}

	public void setG_compra_produtos(boolean g_compra_produtos) {
		this.g_compra_produtos = g_compra_produtos;
	}

	public boolean isG_venda_produtos() {
		return g_venda_produtos;
	}

	public void setG_venda_produtos(boolean g_venda_produtos) {
		this.g_venda_produtos = g_venda_produtos;
	}

	public boolean isG_combustivel() {
		return g_combustivel;
	}

	public void setG_combustivel(boolean g_combustivel) {
		this.g_combustivel = g_combustivel;
	}

	public boolean isG_produtos() {
		return g_produtos;
	}

	public void setG_produtos(boolean g_produtos) {
		this.g_produtos = g_produtos;
	}

	public boolean isG_caixa() {
		return g_caixa;
	}

	public void setG_caixa(boolean g_caixa) {
		this.g_caixa = g_caixa;
	}

	public boolean isG_permissoes() {
		return g_permissoes;
	}

	public void setG_permissoes(boolean g_permissoes) {
		this.g_permissoes = g_permissoes;
	}

	public String getInformacao_adicional() {
		return informacao_adicional;
	}

	public void setInformacao_adicional(String informacao_adicional) {
		this.informacao_adicional = informacao_adicional;
	}

	public String getNivel_acesso() {
		return nivel_acesso;
	}

	public void setNivel_acesso(String nivel_acesso) {
		this.nivel_acesso = nivel_acesso;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
