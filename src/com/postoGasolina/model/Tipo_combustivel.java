package com.postoGasolina.model;

import java.math.BigDecimal;

public class Tipo_combustivel {
	private int id_tipo_combustivel;
	private Unidade_medida unidade_medida;
	private String nome;
	private BigDecimal quantidade;
	private BigDecimal estoque_maximo;
	private BigDecimal estoque_minimo;
	
	public Tipo_combustivel(int id_tipo_combustivel, Unidade_medida unidade_medida, String nome, BigDecimal quantidade,
			BigDecimal estoque_maximo, BigDecimal estoque_minimo) {

		this.id_tipo_combustivel = id_tipo_combustivel;
		this.unidade_medida = unidade_medida;
		this.nome = nome;
		this.quantidade = quantidade;
		this.estoque_maximo = estoque_maximo;
		this.estoque_minimo = estoque_minimo;
	}
	
	
	
	@Override
	public String toString() {
		return  nome;
	}

	public int getId_tipo_combustivel() {
		return id_tipo_combustivel;
	}

	public void setId_tipo_combustivel(int id_tipo_combustivel) {
		this.id_tipo_combustivel = id_tipo_combustivel;
	}

	public Unidade_medida getUnidade_medida() {
		return unidade_medida;
	}

	public void setUnidade_medida(Unidade_medida unidade_medida) {
		this.unidade_medida = unidade_medida;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getEstoque_maximo() {
		return estoque_maximo;
	}

	public void setEstoque_maximo(BigDecimal estoque_maximo) {
		this.estoque_maximo = estoque_maximo;
	}

	public BigDecimal getEstoque_minimo() {
		return estoque_minimo;
	}

	public void setEstoque_minimo(BigDecimal estoque_minimo) {
		this.estoque_minimo = estoque_minimo;
	}
}
