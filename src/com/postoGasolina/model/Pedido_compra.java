package com.postoGasolina.model;

import java.math.BigDecimal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pedido_compra {
	private int id_pedido_compra;
	private Fornecedor fornecedor;
	private Fluxo_caixa2 fluxo_caixa;
	private String nome_responsavel;
	private BigDecimal total_pagar;
	private BigDecimal desconto;
	
	private ObservableList<Item_pedido> itens_pedido = FXCollections.observableArrayList();
	
	public Pedido_compra(int id_pedido_compra, Fornecedor fornecedor, Fluxo_caixa2 fluxo_caixa, String nome_responsavel,
			BigDecimal total_pagar, BigDecimal desconto, ObservableList<Item_pedido> itens_pedido) {
	
		this.id_pedido_compra = id_pedido_compra;
		this.fornecedor = fornecedor;
		this.fluxo_caixa = fluxo_caixa;
		this.nome_responsavel = nome_responsavel;
		this.total_pagar = total_pagar;
		this.desconto = desconto;
		this.itens_pedido = itens_pedido;
	}

	public int getId_pedido_compra() {
		return id_pedido_compra;
	}

	public void setId_pedido_compra(int id_pedido_compra) {
		this.id_pedido_compra = id_pedido_compra;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Fluxo_caixa2 getFluxo_caixa() {
		return fluxo_caixa;
	}

	public void setFluxo_caixa(Fluxo_caixa2 fluxo_caixa) {
		this.fluxo_caixa = fluxo_caixa;
	}

	public String getNome_responsavel() {
		return nome_responsavel;
	}

	public void setNome_responsavel(String nome_responsavel) {
		this.nome_responsavel = nome_responsavel;
	}

	public BigDecimal getTotal_pagar() {
		return total_pagar;
	}

	public void setTotal_pagar(BigDecimal total_pagar) {
		this.total_pagar = total_pagar;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public ObservableList<Item_pedido> getItens_pedido() {
		return itens_pedido;
	}

	public void setItens_pedido(ObservableList<Item_pedido> itens_pedido) {
		this.itens_pedido = itens_pedido;
	}
	
	
}
