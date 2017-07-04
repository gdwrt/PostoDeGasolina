package com.postoGasolina.model;

import java.io.File;
import java.time.LocalDate;

public class Licença_autorizacao {
	private int id_licenca_autorizacao;
	private Orgao_governamental orgao;
	private Funcionario funcionario;
	private String descricao;
	private LocalDate data_inicio;
	private LocalDate data_expiracao;
	private String status;
	private String descricao_responsavel_renovacao;
	private String informacao_adicional;
	private File arquivo_pdf;
	
	public Licença_autorizacao(int id_licenca_autorizacao, Orgao_governamental orgao, Funcionario funcionario,
			String descricao, LocalDate data_inicio, LocalDate data_expiracao, String status,
			String descricao_responsavel_renovacao, String informacao_adicional, File arquivo_pdf) {

		this.id_licenca_autorizacao = id_licenca_autorizacao;
		this.orgao = orgao;
		this.funcionario = funcionario;
		this.descricao = descricao;
		this.data_inicio = data_inicio;
		this.data_expiracao = data_expiracao;
		this.status = status;
		this.descricao_responsavel_renovacao = descricao_responsavel_renovacao;
		this.informacao_adicional = informacao_adicional;
		this.arquivo_pdf = arquivo_pdf;
	}
	
	public int getId_licenca_autorizacao() {
		return id_licenca_autorizacao;
	}
	public void setId_licenca_autorizacao(int id_licenca_autorizacao) {
		this.id_licenca_autorizacao = id_licenca_autorizacao;
	}
	public Orgao_governamental getOrgao() {
		return orgao;
	}
	public void setOrgao(Orgao_governamental orgao) {
		this.orgao = orgao;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getData_inicio() {
		return data_inicio;
	}
	public void setData_inicio(LocalDate data_inicio) {
		this.data_inicio = data_inicio;
	}
	public LocalDate getData_expiracao() {
		return data_expiracao;
	}
	public void setData_expiracao(LocalDate data_expiracao) {
		this.data_expiracao = data_expiracao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescricao_responsavel_renovacao() {
		return descricao_responsavel_renovacao;
	}
	public void setDescricao_responsavel_renovacao(String descricao_responsavel_renovacao) {
		this.descricao_responsavel_renovacao = descricao_responsavel_renovacao;
	}
	public String getInformacao_adicional() {
		return informacao_adicional;
	}
	public void setInformacao_adicional(String informacao_adicional) {
		this.informacao_adicional = informacao_adicional;
	}
	public File getArquivo_pdf() {
		return arquivo_pdf;
	}
	public void setArquivo_pdf(File arquivo_pdf) {
		this.arquivo_pdf = arquivo_pdf;
	}
}
