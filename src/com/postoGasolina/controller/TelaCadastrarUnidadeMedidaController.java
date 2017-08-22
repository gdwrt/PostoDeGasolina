package com.postoGasolina.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.postoGasolina.dao.UnidadeMedidaDao;
import com.postoGasolina.model.Unidade_medida;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class TelaCadastrarUnidadeMedidaController implements Initializable {
	
	@FXML
    private JFXTextField campoNome;

    @FXML
    private JFXListView<Unidade_medida> listViewUnidadeMedida;

    @FXML
    private ImageView btnAdcionarUnidadeMedida;

    @FXML
    private ImageView btnRemoverUnidadeMedida;
    
    @FXML
    private BorderPane borderPane;
    
    JFXSnackbar snackBar;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		carregarAcoes();
		carregarLista();
	}
    
    void carregarAcoes(){
    	btnAdcionarUnidadeMedida.setOnMouseClicked(event ->{
    		
    		if(!campoNome.getText().isEmpty()){
    			try {
    				new UnidadeMedidaDao().cadastrar(new Unidade_medida(0, campoNome.getText()));
    				
    				carregarLista();
    				limparCampo();

    				snackBar = new JFXSnackbar(borderPane);
    		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
    				snackBar.show("Unidade de medida cadastrada com sucesso", 4000); 
    				
    			} catch (ClassNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		} else {
				snackBar = new JFXSnackbar(borderPane);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Campos obrigatórios não informado", 4000); 
    		}
    		
    		
    	});
    	btnRemoverUnidadeMedida.setOnMouseClicked(event ->{
    		if(listViewUnidadeMedida.getSelectionModel().getSelectedIndex() != -1){

        		try {
        			int id = listViewUnidadeMedida.getSelectionModel().getSelectedItem().getId_unidade_medida();
    				new UnidadeMedidaDao().remover(id);
    				
    				carregarLista();
    				limparCampo();
    				
    				snackBar = new JFXSnackbar(borderPane);
    			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
    				snackBar.show("Uni. de medida removida com sucesso", 4000); 
    				
    			} catch (ClassNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				
    				snackBar = new JFXSnackbar(borderPane);
    	//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
    				snackBar.show("Uni. medida sendo utilizada", 4000);
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				snackBar = new JFXSnackbar(borderPane);
    		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
    				snackBar.show("Uni. medida sendo utilizada", 4000);
    			}
        	
    		}else {
    			snackBar = new JFXSnackbar(borderPane);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Selecione Uni. medida na tabela", 4000); 
    		}
    	});
    		 
    	
    }
    void carregarLista(){
    	try {
			listViewUnidadeMedida.setItems(new UnidadeMedidaDao().listar());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    void limparCampo(){
    	campoNome.setText("");
    }
}
