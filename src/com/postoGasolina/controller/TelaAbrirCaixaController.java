package com.postoGasolina.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTimePicker;
import com.postoGasolina.dao.CaixaDao;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.util.NumeroTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TelaAbrirCaixaController implements Initializable {

	@FXML
	private GridPane gridPaneBottom;

	@FXML
	private JFXTimePicker comboBoxHoraInicial;

	@FXML
	private JFXButton btnAbrir;

	@FXML
	private ImageView imageAdicionar;

	@FXML
	private JFXDatePicker comboBoxDataInicial;

	private NumeroTextField campoSaldoInicial = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	public final static TextField AbrirTela = new TextField();
	
	private JFXSnackbar snackBar; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		String style = getClass().getResource("/com/postoGasolina/style/Caixa.css").toExternalForm();

		campoSaldoInicial.getStylesheets().add(style);
		campoSaldoInicial.getStyleClass().add("formata-campo");
		campoSaldoInicial.setUnFocusColor(Color.BLACK);
		campoSaldoInicial.setFocusColor(Color.BLACK);
		campoSaldoInicial.setOpacity(0.53);
		campoSaldoInicial.setFocusTraversable(false);
		gridPaneBottom.add(campoSaldoInicial, 0, 1);
		gridPaneBottom.setMargin(campoSaldoInicial, new Insets(35, 30, 0, 30));

		comboBoxDataInicial.setValue(LocalDate.now());
		comboBoxHoraInicial.setValue(LocalTime.now());
	}

	@FXML
	void btnAbrir(ActionEvent event) {
		if (!campoSaldoInicial.getNumber().equals(BigDecimal.ZERO)) {

			try {
				Fluxo_caixa.setData_hora_inicial(
						LocalDateTime.of(comboBoxDataInicial.getValue(), comboBoxHoraInicial.getValue()));
				Fluxo_caixa.setSaldo_atual(campoSaldoInicial.getNumber());
				Fluxo_caixa.setStatus("Aberto");

				if (AbrirTela.getText().equals("4")) {
					AbrirTela.setText("1");

				} else if (AbrirTela.getText().equals("5")) {
					AbrirTela.setText("2");
				}
				//abriu caixa no banco de dados
				new CaixaDao().abrirCaixa();
				
				Stage stage = (Stage) gridPaneBottom.getScene().getWindow();
				stage.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// System.out.println(Fluxo_caixa.getData_hora_inicial().format(DateTimeFormatter.ofPattern("dd/MM/yyyy
			// HH:mm:ss")));
		} else {
			snackBar = new JFXSnackbar(gridPaneBottom);
			//String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Campos Obrigatórios não informado", 4000); 
		}
	}

}
