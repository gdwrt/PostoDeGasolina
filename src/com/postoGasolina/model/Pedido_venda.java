package com.postoGasolina.model;

import java.math.BigDecimal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pedido_venda {
	
	private int id_pedido_venda;
	private Funcionario funcionario;
	private Cliente cliente;
	private Fluxo_caixa2 fluxoCaixa;
	private BigDecimal total_pagar;
	private BigDecimal desconto;
	private String forma_pagamento;
	private ObservableList<Item_pedido> itens_pedido = FXCollections.observableArrayList();
	
	public Pedido_venda(int id_pedido_venda, Funcionario funcionario, Cliente cliente, Fluxo_caixa2 fluxoCaixa,
			BigDecimal total_pagar, BigDecimal desconto, String forma_pagamento,
			ObservableList<Item_pedido> itens_pedido) {
		this.id_pedido_venda = id_pedido_venda;
		this.funcionario = funcionario;
		this.cliente = cliente;
		this.fluxoCaixa = fluxoCaixa;
		this.total_pagar = total_pagar;
		this.desconto = desconto;
		this.forma_pagamento = forma_pagamento;
		this.itens_pedido = itens_pedido;
	}
	
	public int getId_pedido_venda() {
		return id_pedido_venda;
	}
	public void setId_pedido_venda(int id_pedido_venda) {
		this.id_pedido_venda = id_pedido_venda;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Fluxo_caixa2 getFluxoCaixa() {
		return fluxoCaixa;
	}
	public void setFluxoCaixa(Fluxo_caixa2 fluxoCaixa) {
		this.fluxoCaixa = fluxoCaixa;
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
	public String getForma_pagamento() {
		return forma_pagamento;
	}
	public void setForma_pagamento(String forma_pagamento) {
		this.forma_pagamento = forma_pagamento;
	}
	public ObservableList<Item_pedido> getItens_pedido() {
		return itens_pedido;
	}
	public void setItens_pedido(ObservableList<Item_pedido> itens_pedido) {
		this.itens_pedido = itens_pedido;
	}
	
	
	
}
