package com.postoGasolina.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.postoGasolina.controller.TelaVendaController.RecebePedido;
import com.postoGasolina.util.NumeroTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TelaCadastrarDesconto implements Initializable {

	@FXML
	private GridPane gridPanePrincipal;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnAplicar;

	private NumeroTextField campoSubTotal = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
	private NumeroTextField campoDescontoReais = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
	private NumeroTextField campoDescontoPorcentagem = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getPercentInstance());
	private NumeroTextField campoTotalFinalApagar = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		carregarComponents();
		carregarEventos();
	}

	public BigDecimal pegarValorTotalComDesconto() {
		return campoTotalFinalApagar.getNumber();
	}

	@FXML
	void btnExcluir(ActionEvent event) {
		try {
			Stage stage = (Stage) btnVoltar.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		try {
			TelaVendaController.RecebePedido.setDesconto(RecebePedido.getDesconto()
					.add(campoSubTotal.getNumber().subtract(campoTotalFinalApagar.getNumber())));
			TelaVendaController.mensagem.setText("3");
			Stage stage = (Stage) btnVoltar.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	void carregarComponents() {
		String style = getClass().getResource("/com/postoGasolina/style/TelaVenda.css").toExternalForm();

		campoSubTotal.getStylesheets().add(style);
		campoSubTotal.getStyleClass().add("formata-campo");
		campoSubTotal.setUnFocusColor(Color.WHITE);
		campoSubTotal.setFocusColor(Color.WHITE);
		campoSubTotal.setEditable(false);
		campoSubTotal.setFocusTraversable(false);
		campoSubTotal.setLabelFloat(true);
		campoSubTotal.setPromptText("Sub Total");
		gridPanePrincipal.add(campoSubTotal, 0, 0);
		gridPanePrincipal.setMargin(campoSubTotal, new Insets(0, 0, 0, 0));

		campoDescontoReais.getStylesheets().add(style);
		campoDescontoReais.getStyleClass().add("formata-campo");
		campoDescontoReais.setUnFocusColor(Color.WHITE);
		campoDescontoReais.setFocusColor(Color.WHITE);
		campoDescontoReais.setFocusTraversable(false);
		campoDescontoReais.setLabelFloat(true);
		campoDescontoReais.setPromptText("Desconto R$");
		gridPanePrincipal.add(campoDescontoReais, 0, 1);
		gridPanePrincipal.setMargin(campoDescontoReais, new Insets(0, 180, 0, 0));

		campoDescontoPorcentagem.getStylesheets().add(style);
		campoDescontoPorcentagem.getStyleClass().add("formata-campo");
		campoDescontoPorcentagem.setUnFocusColor(Color.WHITE);
		campoDescontoPorcentagem.setFocusColor(Color.WHITE);
		campoDescontoPorcentagem.setFocusTraversable(false);
		campoDescontoPorcentagem.setLabelFloat(true);
		campoDescontoPorcentagem.setPromptText("Desconto %");
		gridPanePrincipal.add(campoDescontoPorcentagem, 0, 1);
		gridPanePrincipal.setMargin(campoDescontoPorcentagem, new Insets(0, 0, 0, 150));

		campoTotalFinalApagar.getStylesheets().add(style);
		campoTotalFinalApagar.getStyleClass().add("formata-campo");
		campoTotalFinalApagar.setUnFocusColor(Color.WHITE);
		campoTotalFinalApagar.setFocusColor(Color.WHITE);
		campoTotalFinalApagar.setFocusTraversable(false);
		campoTotalFinalApagar.setLabelFloat(true);
		campoTotalFinalApagar.setEditable(false);
		campoTotalFinalApagar.setPromptText("Total Final a Pagar");
		gridPanePrincipal.add(campoTotalFinalApagar, 0, 2);
		gridPanePrincipal.setMargin(campoTotalFinalApagar, new Insets(0, 0, 0, 0));

	}

	private void carregarEventos() {
		// TODO Auto-generated method stub
		campoSubTotal.setNumber(TelaVendaController.RecebePedido.getTotal_pagar());
		campoTotalFinalApagar.setNumber(campoSubTotal.getNumber());

		campoDescontoPorcentagem.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				campoDescontoReais.setNumber(BigDecimal.ZERO);
				try {
					if (!newValue.replaceAll("[% ]", "").isEmpty()) {

						final NumeroTextField qtdAtualizado = new NumeroTextField(
								new BigDecimal(newValue.replaceAll("[% ]", "").replaceAll("[,.]", "")),
								NumberFormat.getPercentInstance());
						campoTotalFinalApagar.setNumber(campoSubTotal.getNumber().subtract(campoSubTotal.getNumber()
								.multiply(qtdAtualizado.getNumber().divide(new BigDecimal("100")))));
					} else {
						campoTotalFinalApagar.setNumber(campoSubTotal.getNumber());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		campoDescontoReais.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				campoDescontoPorcentagem.setNumber(BigDecimal.ZERO);
				try {
					if (!newValue.replaceAll("[R$ ]", "").isEmpty()) {
						final NumeroTextField qtdAtualizado = new NumeroTextField(
								new BigDecimal(newValue.replaceAll("[R$ ]", "")));
						campoTotalFinalApagar.setNumber(campoSubTotal.getNumber().subtract(qtdAtualizado.getNumber()));
					} else {
						campoTotalFinalApagar.setNumber(campoSubTotal.getNumber());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

}
