package com.postoGasolina.model;

import java.math.BigDecimal;

public class Combustivel {
	private int id_combustivel;
	private Tipo_combustivel tipoCombustivel;
	private Bomba bomba;
	private Bico bico;
	private String descricao;
	private BigDecimal preco_venda;
	
	public Combustivel(int id_combustivel, Tipo_combustivel tipoCombustivel, Bomba bomba, Bico bico, String descricao,
			BigDecimal preco_venda) {
		super();
		this.id_combustivel = id_combustivel;
		this.tipoCombustivel = tipoCombustivel;
		this.bomba = bomba;
		this.bico = bico;
		this.descricao = descricao;
		this.preco_venda = preco_venda;
	}
	
	public int getId_combustivel() {
		return id_combustivel;
	}
	public void setId_combustivel(int id_combustivel) {
		this.id_combustivel = id_combustivel;
	}
	public Tipo_combustivel getTipoCombustivel() {
		return tipoCombustivel;
	}
	public void setTipoCombustivel(Tipo_combustivel tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}
	public Bomba getBomba() {
		return bomba;
	}
	public void setBomba(Bomba bomba) {
		this.bomba = bomba;
	}
	public Bico getBico() {
		return bico;
	}
	public void setBico(Bico bico) {
		this.bico = bico;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPreco_venda() {
		return preco_venda;
	}
	public void setPreco_venda(BigDecimal preco_venda) {
		this.preco_venda = preco_venda;
	}
	
		
	
}
