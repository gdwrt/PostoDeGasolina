package com.postoGasolina.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSnackbar;
import com.postoGasolina.controller.TelaVendaController.RecebePedido;
import com.postoGasolina.dao.VendaDao;
import com.postoGasolina.model.Fluxo_caixa2;
import com.postoGasolina.model.Pedido_venda;
import com.postoGasolina.util.NumeroTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TelaFinalizarVenda implements Initializable {

	@FXML
	private JFXCheckBox checkBoxDinheiro;

	@FXML
	private JFXCheckBox checkBoxCredito;

	@FXML
	private JFXCheckBox checkBoxDebito;

	@FXML
	private JFXButton btnFinalizer;

	@FXML
	private ImageView imageAdicionar;
	@FXML
	private BorderPane borderPane;

	private NumeroTextField campoTotalVenda = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	@FXML
	private GridPane gridPaneBottom;
	
	private JFXSnackbar snackBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		String style = getClass().getResource("/com/postoGasolina/style/TelaVenda.css").toExternalForm();

		campoTotalVenda.getStylesheets().add(style);
		campoTotalVenda.getStyleClass().add("forma-campo");
		campoTotalVenda.setUnFocusColor(Color.TRANSPARENT);
		campoTotalVenda.setFocusColor(Color.TRANSPARENT);
		campoTotalVenda.setAlignment(Pos.CENTER);
		campoTotalVenda.setEditable(false);
		campoTotalVenda.setFocusTraversable(false);
		gridPaneBottom.add(campoTotalVenda, 0, 3);
		gridPaneBottom.setMargin(campoTotalVenda, new Insets(0, 0, 20, 0));
		
		campoTotalVenda.setNumber(RecebePedido.getTotal_pagar());

	}

	@FXML
	void OnActionCheckBoxDebito(ActionEvent event) {
		checkBoxCredito.setSelected(false);
		checkBoxDinheiro.setSelected(false);
	}

	@FXML
	void OnActioncheckBoxCredito(ActionEvent event) {
		checkBoxDebito.setSelected(false);
		checkBoxDinheiro.setSelected(false);
	}

	@FXML
	void OnAtionCheckBoxDinheiro(ActionEvent event) {
		checkBoxCredito.setSelected(false);
		checkBoxDebito.setSelected(false);
	}

	@FXML
	void btnFinalizar(ActionEvent event) {
		if ( (checkBoxCredito.isSelected() || checkBoxDebito.isSelected() || checkBoxDinheiro.isSelected() ) && RecebePedido.getFuncionario() != null
				&& !RecebePedido.getItens_pedido().isEmpty()) {
			try {
				new VendaDao().finalizar(new Pedido_venda(0, RecebePedido.getFuncionario(), RecebePedido.getCliente(),
						new Fluxo_caixa2(), RecebePedido.getTotal_pagar(), RecebePedido.getDesconto(),
						checkBoxCredito.isSelected() ? "credito" : checkBoxDebito.isSelected() ? "debito" : "dinheiro",
						RecebePedido.getItens_pedido()));
				
				RecebePedido.setLimparVenda(1);
				
				TelaVendaController.mensagem.setText("1");
				
				Stage stage = (Stage) btnFinalizer.getScene().getWindow();
				stage.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else {
			snackBar = new JFXSnackbar(borderPane);
	//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Campos obrigatórios não informado", 4000);
		}
	}

}
