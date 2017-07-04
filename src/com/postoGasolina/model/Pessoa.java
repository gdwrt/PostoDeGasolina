package com.postoGasolina.model;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class Pessoa{
		// Dados pessoais
		private int id_pessoa;
		private String nome;
		private LocalDate data_nascimento;
		private char sexo;
		private String estado_civil;
		private String rg;
		private String cpf;
		
		public Pessoa(int id_pessoa, String nome, String cpf){
			this.id_pessoa = id_pessoa;
			this.nome = WordUtils.capitalize(nome);
			this.cpf = cpf;
		}
		public Pessoa(int id_pessoa, String nome, LocalDate data_nascimento, char sexo, String estado_civil, String rg,
				String cpf) {
			this.id_pessoa = id_pessoa;
			this.nome = WordUtils.capitalize(nome);
			this.data_nascimento = data_nascimento;
			this.sexo = sexo;
			this.estado_civil = estado_civil;
			this.rg = rg;
			this.cpf = cpf;
		}
		
		@Override
		public String toString() {
			return "Pessoa [id_pessoa=" + id_pessoa + ", nome=" + nome + ", data_nascimento=" + data_nascimento
					+ ", sexo=" + sexo + ", estado_civil=" + estado_civil + ", rg=" + rg + ", cpf=" + cpf + "]";
		}
		
		public int getId_pessoa() {
			return id_pessoa;
		}
		public void setId_pessoa(int id_pessoa) {
			this.id_pessoa = id_pessoa;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public LocalDate getData_nascimento() {
			return data_nascimento;
		}
		public void setData_nascimento(LocalDate data_nascimento) {
			this.data_nascimento = data_nascimento;
		}
		public char getSexo() {
			return sexo;
		}
		public void setSexo(char sexo) {
			this.sexo = sexo;
		}
		public String getEstado_civil() {
			return estado_civil;
		}
		public void setEstado_civil(String estado_civil) {
			this.estado_civil = estado_civil;
		}
		public String getRg() {
			return rg;
		}
		public void setRg(String rg) {
			this.rg = rg;
		}
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
}
