package com.postoGasolina.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Fluxo_caixa {

	private static int id_fluxo_caixa;
	private static BigDecimal saldo_atual;
	private static LocalDateTime data_hora_inicial;
	private static LocalDateTime data_hora_final;
	private static BigDecimal saldo_final; 
	private static String status = "Fechado";
	
	
	public static int getId_fluxo_caixa() {
		return id_fluxo_caixa;
	}
	public static void setId_fluxo_caixa(int id_fluxo_caixa) {
		Fluxo_caixa.id_fluxo_caixa = id_fluxo_caixa;
	}
	public static BigDecimal getSaldo_atual() {
		return saldo_atual;
	}
	public static void setSaldo_atual(BigDecimal saldo_atual) {
		Fluxo_caixa.saldo_atual = saldo_atual;
	}
	public static LocalDateTime getData_hora_inicial() {
		return data_hora_inicial;
	}
	public static void setData_hora_inicial(LocalDateTime data_hora_inicial) {
		Fluxo_caixa.data_hora_inicial = data_hora_inicial;
	}
	public static LocalDateTime getData_hora_final() {
		return data_hora_final;
	}
	public static void setData_hora_final(LocalDateTime data_hora_final) {
		Fluxo_caixa.data_hora_final = data_hora_final;
	}
	public static BigDecimal getSaldo_final() {
		return saldo_final;
	}
	public static void setSaldo_final(BigDecimal saldo_final) {
		Fluxo_caixa.saldo_final = saldo_final;
	}
	public static String getStatus() {
		return status;
	}
	public static void setStatus(String status) {
		Fluxo_caixa.status = status;
	}
}
