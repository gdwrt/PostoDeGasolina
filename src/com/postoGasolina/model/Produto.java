package com.postoGasolina.model;

import java.math.BigDecimal;

public class Produto {
	private int id_produto;
	private String codigo_produto;
	private Categoria categoria;
	private Unidade_medida unidade_medida;
	private String descricao;
	private BigDecimal estoque_disponivel;
	private BigDecimal preco_venda;
	private BigDecimal estoque_max;
	private BigDecimal estoque_min;
	private boolean nao_controlar_estoque;
	
	public Produto(int id_produto, String codigo_produto, Categoria categoria, Unidade_medida unidade_medida,
			String descricao, BigDecimal estoque_disponivel, BigDecimal preco_venda, BigDecimal estoque_max,
			BigDecimal estoque_min, boolean nao_controlar_estoque) {
		super();
		this.id_produto = id_produto;
		this.codigo_produto = codigo_produto;
		this.categoria = categoria;
		this.unidade_medida = unidade_medida;
		this.descricao = descricao;
		this.estoque_disponivel = estoque_disponivel;
		this.preco_venda = preco_venda;
		this.estoque_max = estoque_max;
		this.estoque_min = estoque_min;
		this.nao_controlar_estoque = nao_controlar_estoque;
	}

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public String getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(String codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Unidade_medida getUnidade_medida() {
		return unidade_medida;
	}

	public void setUnidade_medida(Unidade_medida unidade_medida) {
		this.unidade_medida = unidade_medida;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getEstoque_disponivel() {
		return estoque_disponivel;
	}

	public void setEstoque_disponivel(BigDecimal estoque_disponivel) {
		this.estoque_disponivel = estoque_disponivel;
	}

	public BigDecimal getPreco_venda() {
		return preco_venda;
	}

	public void setPreco_venda(BigDecimal preco_venda) {
		this.preco_venda = preco_venda;
	}

	public BigDecimal getEstoque_max() {
		return estoque_max;
	}

	public void setEstoque_max(BigDecimal estoque_max) {
		this.estoque_max = estoque_max;
	}

	public BigDecimal getEstoque_min() {
		return estoque_min;
	}

	public void setEstoque_min(BigDecimal estoque_min) {
		this.estoque_min = estoque_min;
	}

	public boolean isNao_controlar_estoque() {
		return nao_controlar_estoque;
	}

	public void setNao_controlar_estoque(boolean nao_controlar_estoque) {
		this.nao_controlar_estoque = nao_controlar_estoque;
	}
}
