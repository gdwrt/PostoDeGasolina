package com.postoGasolina.model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import com.postoGasolina.dao.ClienteFisicaDao;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente_Gasto {

	private int id;
	private String cliente;
	private BigDecimal gastoMensal;
	private BigDecimal  gastoAnual;
	private BigDecimal gastoTotal;
	private LocalDate dataNascimento;
	private String tipo_cliente;
	private String email;
	
	public Cliente_Gasto(int id, String cliente, BigDecimal gastoMensal, BigDecimal gastoAnual, BigDecimal gastoTotal,
			LocalDate dataNascimento, String tipo_cliente, String email) {
		this.id = id;
		this.cliente = cliente;
		this.gastoMensal = gastoMensal;
		this.gastoAnual = gastoAnual;
		this.gastoTotal = gastoTotal;
		this.dataNascimento = dataNascimento;
		this.tipo_cliente = tipo_cliente;
		this.setEmail(email);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public BigDecimal getGastoMensal() {
		return gastoMensal;
	}
	public void setGastoMensal(BigDecimal gastoMensal) {
		this.gastoMensal = gastoMensal;
	}
	public BigDecimal getGastoAnual() {
		return gastoAnual;
	}
	public void setGastoAnual(BigDecimal gastoAnual) {
		this.gastoAnual = gastoAnual;
	}
	public BigDecimal getGastoTotal() {
		return gastoTotal;
	}
	public void setGastoTotal(BigDecimal gastoTotal) {
		this.gastoTotal = gastoTotal;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getTipo_cliente() {
		return tipo_cliente;
	}
	public void setTipo_cliente(String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
