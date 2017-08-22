package com.postoGasolina.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.postoGasolina.dao.CargoDao;
import com.postoGasolina.model.Cargo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class TelaCadastrarCargoController implements Initializable{

    @FXML
    private JFXTextField campoDescricao;

    @FXML
    private JFXListView<Cargo> listViewCargo;

    @FXML
    private ImageView btnAdcionarCargo;

    @FXML
    private ImageView btnRemoverCargo;
    
    private CargoDao sqlCargo = new CargoDao();
    
    @FXML
    private BorderPane borderPane;
    
    private JFXSnackbar snackBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		carregarAcoes();
		carregarLista();
	}
    
    void carregarAcoes(){
    	btnAdcionarCargo.setOnMouseClicked(event ->{
    		
    		try {
				
				
				if(!campoDescricao.getText().isEmpty()){
					sqlCargo.cadastrar(new Cargo(0, campoDescricao.getText()));
					
					carregarLista();
					limparCampo();
					
					snackBar = new JFXSnackbar(borderPane);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Cargo cadastrado com sucesso", 4000); 
				} else {
					snackBar = new JFXSnackbar(borderPane);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000); 
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    	});
    	btnRemoverCargo.setOnMouseClicked(event ->{
    		try {
    			
				
				if(listViewCargo.getSelectionModel().getSelectedIndex() != -1){
					int id = listViewCargo.getSelectionModel().getSelectedItem().getId_cargo();
					sqlCargo.remover(id);
					carregarLista();
					limparCampo();		
					
					snackBar = new JFXSnackbar(borderPane);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Cargo removido com sucesso", 4000); 
				} else {
					snackBar = new JFXSnackbar(borderPane);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Seleciona cargo na tabela", 4000); 
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPane);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Cargo sendo utilizado", 4000);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPane);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Cargo sendo utilizado", 4000);
			}
    	});
    	
    }
    void carregarLista(){
    	try {
			listViewCargo.setItems(sqlCargo.listar());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    void limparCampo(){
    	campoDescricao.setText("");
    }

}
