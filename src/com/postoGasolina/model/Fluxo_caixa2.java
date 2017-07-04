package com.postoGasolina.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Fluxo_caixa2 {

	private int id_fluxo_caixa;
	private BigDecimal saldo_atual;
	private LocalDateTime data_hora_inicial;
	private LocalDateTime data_hora_final;
	private BigDecimal saldo_final; 
	private String status = "Aberto";

	public Fluxo_caixa2(){
		
	}
	public Fluxo_caixa2(int id_fluxo_caixa, BigDecimal saldo_atual, LocalDateTime data_hora_inicial,
			LocalDateTime data_hora_final, BigDecimal saldo_final) {
		super();
		this.id_fluxo_caixa = id_fluxo_caixa;
		this.saldo_atual = saldo_atual;
		this.data_hora_inicial = data_hora_inicial;
		this.data_hora_final = data_hora_final;
		this.saldo_final = saldo_final;
	}
	public int getId_fluxo_caixa() {
		return id_fluxo_caixa;
	}
	public void setId_fluxo_caixa(int id_fluxo_caixa) {
		this.id_fluxo_caixa = id_fluxo_caixa;
	}
	public BigDecimal getSaldo_atual() {
		return saldo_atual;
	}
	public void setSaldo_atual(BigDecimal saldo_atual) {
		this.saldo_atual = saldo_atual;
	}
	public LocalDateTime getData_hora_inicial() {
		return data_hora_inicial;
	}
	public void setData_hora_inicial(LocalDateTime data_hora_inicial) {
		this.data_hora_inicial = data_hora_inicial;
	}
	public LocalDateTime getData_hora_final() {
		return data_hora_final;
	}
	public void setData_hora_final(LocalDateTime data_hora_final) {
		this.data_hora_final = data_hora_final;
	}
	public BigDecimal getSaldo_final() {
		return saldo_final;
	}
	public void setSaldo_final(BigDecimal saldo_final) {
		this.saldo_final = saldo_final;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
