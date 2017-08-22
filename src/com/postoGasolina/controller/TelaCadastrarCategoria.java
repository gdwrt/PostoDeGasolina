package com.postoGasolina.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.postoGasolina.dao.CategoriaDao;
import com.postoGasolina.model.Categoria;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class TelaCadastrarCategoria implements Initializable {

	@FXML
	private JFXTextField campoNome;

	@FXML
	private JFXListView<Categoria> listViewCategoria;

	@FXML
	private ImageView btnAdcionarCategoria;

	@FXML
	private ImageView btnRemoverCategoria;
	@FXML
	private BorderPane borderPane;
	
	JFXSnackbar s;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		carregarAcoes();
		carregarLista();
	}

	void carregarAcoes() {
		btnAdcionarCategoria.setOnMouseClicked(event -> {
			if (!campoNome.getText().isEmpty()) {
				try {
					new CategoriaDao().cadastrar(new Categoria(0, campoNome.getText()));

					carregarLista();
					limparCampo();

					s = new JFXSnackbar(borderPane);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					s.show("Categoria cadastrada com sucesso", 4000);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				s = new JFXSnackbar(borderPane);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				s.show("Campos obrigatórios não informado", 4000);
			}

		});
		btnRemoverCategoria.setOnMouseClicked(event -> {
			if (listViewCategoria.getSelectionModel().getSelectedIndex() != -1) {
				try {
					int id = listViewCategoria.getSelectionModel().getSelectedItem().getId_categoria();
					new CategoriaDao().remover(id);

					carregarLista();
					limparCampo();

					s = new JFXSnackbar(borderPane);
		//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					s.show("Categoria removida com sucesso", 4000);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					s = new JFXSnackbar(borderPane);
    		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
    				s.show("Categoria sendo utilizada", 4000);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					s = new JFXSnackbar(borderPane);
    		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
    				s.show("Categoria sendo utilizada", 4000);
				}
			} else {
				s = new JFXSnackbar(borderPane);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				s.show("Selecione categoria na tabela", 4000);
			}
		});

	}

	void carregarLista() {
		try {
			listViewCategoria.setItems(new CategoriaDao().listar());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void limparCampo() {
		campoNome.setText("");
	}
}
