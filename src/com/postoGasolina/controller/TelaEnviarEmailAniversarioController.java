package com.postoGasolina.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.EmailException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.postoGasolina.model.Email;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TelaEnviarEmailAniversarioController implements Initializable{

    @FXML
    private GridPane gridPaneBottom;

    @FXML
    private JFXButton btnEnviar;

    @FXML
    private ImageView imageAdicionar;

    @FXML
    private JFXTextField campoNome;

    @FXML
    private JFXTextArea textAreaMensagem;

    @FXML
    private BorderPane borderPane;
    
    private JFXSnackbar s;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		campoNome.setText(TelaGerenciarFidelizacaoClientesController.nomeCliente.getText());
		
		textAreaMensagem.getStyleClass().add("format-campo");
		textAreaMensagem.setUnFocusColor(Color.GRAY);
		textAreaMensagem.setFocusColor(Color.GRAY);
		textAreaMensagem.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		textAreaMensagem.setOpacity(0.95);
		textAreaMensagem.setFocusTraversable(false);
		
		campoNome.getStyleClass().add("format-campo");
		campoNome.setUnFocusColor(Color.GRAY);
		campoNome.setFocusColor(Color.GRAY);
		campoNome.setOpacity(0.95);
		campoNome.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		campoNome.setFocusTraversable(false);
		
	}
	@FXML
    void btnEnviar(ActionEvent event) {
		

		 imageAdicionar.setImage(new Image(new File("/com/postoGasolina/img/TelaVenda/indicator_32.gif").toString()));
		 if(!textAreaMensagem.getText().isEmpty()){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int result;
					try {
						result = new Email().enviarEmailAniversario(TelaGerenciarFidelizacaoClientesController.emailCliente.getText(),
								campoNome.getText(), textAreaMensagem.getText());
						if(result == 1){
							javafx.application.Platform.runLater(() -> {
								imageAdicionar.setImage(new Image(new File("/com/postoGasolina/img/TelaVenda/Mail.gif").toString()));
								s = new JFXSnackbar(borderPane);
					//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
								s.show("E-mail enviado com sucesso", 4000);
							});
							
						}
					} catch (EmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						javafx.application.Platform.runLater(() -> {
							imageAdicionar.setImage(new Image(new File("/com/postoGasolina/img/TelaVenda/Mail.gif").toString()));
							s = new JFXSnackbar(borderPane);
				//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
							s.show("Não foi possivel enviar e-mail", 4000);
						});
					}
					
				}
			}).start();
		} else {
			imageAdicionar.setImage(new Image(new File("/com/postoGasolina/img/TelaVenda/Mail.gif").toString()));
			s = new JFXSnackbar(borderPane);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			s.show("Campos obrigatórios não informado", 4000);
		}
		 
		//new Email().enviarEmail();
    }
}
