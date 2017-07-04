package com.postoGasolina.model;

import java.math.BigDecimal;

public class Item_pedido {
	private int idItem;
	private Produto_loja produto_loja;
	private int id_pedido;
	private BigDecimal preco_unitario;
	private BigDecimal quantidade;
	private String tipo_produto;
	private BigDecimal total;

	public Item_pedido(Produto_loja produto_loja, int id_pedido, BigDecimal preco_unitario, BigDecimal quantidade,
			String tipo_produto,BigDecimal total, int idItem) {
		super();
		this.produto_loja = produto_loja;
		this.id_pedido = id_pedido;
		this.preco_unitario = preco_unitario;
		this.quantidade = quantidade;
		this.tipo_produto = tipo_produto;
		this.total = total;
		this.idItem = idItem;
	}
	
	public Produto_loja getProduto_loja() {
		return produto_loja;
	}
	public void setProduto_loja(Produto_loja produto_loja) {
		this.produto_loja = produto_loja;
	}
	public int getPedido() {
		return id_pedido;
	}
	public void setPedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}
	public BigDecimal getPreco_unitario() {
		return preco_unitario;
	}
	public void setPreco_unitario(BigDecimal preco_unitario) {
		this.preco_unitario = preco_unitario;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public String getTipo_produto() {
		return tipo_produto;
	}
	public void setTipo_produto(String tipo_produto) {
		this.tipo_produto = tipo_produto;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
}
