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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TelaFecharCaixaController implements Initializable {

	@FXML
	private GridPane gridPaneBottom;

	@FXML
	private JFXTimePicker comboBoxHoraFinal;

	@FXML
	private JFXButton btnFechar;

	@FXML
	private ImageView imageAdicionar;

	@FXML
	private JFXDatePicker comboBoxDataFinal;

	private NumeroTextField campoSaldoFinal = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
	
	private JFXSnackbar s;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		String style = getClass().getResource("/com/postoGasolina/style/Caixa.css").toExternalForm();

		campoSaldoFinal.getStylesheets().add(style);
		campoSaldoFinal.getStyleClass().add("formata-campo");
		campoSaldoFinal.setUnFocusColor(Color.BLACK);
		campoSaldoFinal.setFocusColor(Color.BLACK);
		campoSaldoFinal.setOpacity(0.53);
		campoSaldoFinal.setFocusTraversable(false);
		gridPaneBottom.add(campoSaldoFinal, 0, 1);
		gridPaneBottom.setMargin(campoSaldoFinal, new Insets(35, 30, 0, 30));

		comboBoxDataFinal.setValue(LocalDate.now());
		comboBoxHoraFinal.setValue(LocalTime.now());
	}

	@FXML
	void btnFechar(ActionEvent event) {
		try {
			if (!campoSaldoFinal.getNumber().equals(BigDecimal.ZERO)) {

				Fluxo_caixa.setData_hora_final(LocalDateTime.of(comboBoxDataFinal.getValue(), comboBoxHoraFinal.getValue()));
				Fluxo_caixa.setSaldo_final(campoSaldoFinal.getNumber());
				Fluxo_caixa.setStatus("Fechado");
				
				//fechou caixa no banco de dados
				new CaixaDao().fecharCaixa();
				
				TelaGerenciarCaixaController.atualizaG.setText("1");
				
				Stage stage = (Stage) gridPaneBottom.getScene().getWindow();
				stage.close();
				//System.out.println(Fluxo_caixa.getData_hora_inicial().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			} else {
				s = new JFXSnackbar(gridPaneBottom);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				s.show("Campos Obrigatórios não informado", 4000); 
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
