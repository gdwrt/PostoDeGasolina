package com.postoGasolina.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.BombaDao;
import com.postoGasolina.model.Bico;
import com.postoGasolina.model.Bomba;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TelaCadastrarBombaController implements Initializable {

	@FXML
	private GridPane gridPaneTipoCombustivel;

	@FXML
	private JFXTextField campoBomba;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	@FXML
	private JFXListView<Bico> listViewBico;

	@FXML
	private JFXTextField campoBico;

	@FXML
	private ImageView btnAdicionarBico;

	@FXML
	private ImageView btnExcluirBico;

	private int idBomba;
	private int idBico;

	@FXML
	private JFXTreeTableView<BombaClass> treeTableViewBomba;

	ObservableList<Bico> lista_bico = FXCollections.observableArrayList();
	
	private JFXSnackbar snackBar; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		carregarEventos();
		carregarTabela();

	}

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewBomba.getSelectionModel().getSelectedIndex() != -1) {
			idBomba = Integer.parseInt(treeTableViewBomba.getSelectionModel().getSelectedItem().getValue().toString());
			try {
				new BombaDao().remover(idBomba);
				carregarTabela();
				limparcampos();
				snackBar = new JFXSnackbar(borderPaneTabela);
				//String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Bomba removida com sucesso", 4000); 
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Bomba sendo utilizada", 4000); 
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona bomba na tabela", 4000); 
		}

	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparcampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewBomba.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (!campoBomba.getText().isEmpty()) {

					new BombaDao().cadastrar(new Bomba(0, campoBomba.getText(), lista_bico));
					carregarTabela();
					limparcampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Bomba cadastrada com sucesso", 4000); 

				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000); 
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewBomba.getSelectionModel().getSelectedIndex() != -1) {
			idBomba = Integer.parseInt(treeTableViewBomba.getSelectionModel().getSelectedItem().getValue().toString());

			try {

				if (!campoBomba.getText().isEmpty()) {

					new BombaDao().alterar(new Bomba(idBomba, campoBomba.getText(), lista_bico));
					carregarTabela();
					limparcampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Bomba alterada com sucesso", 4000); 

				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000); 		
					}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public void carregarDados() {
		// TODO Auto-generated method stub

		treeTableViewBomba.setOnMouseClicked(event -> {
			if (treeTableViewBomba.getSelectionModel().getSelectedIndex() != -1) {
				idBomba = Integer.parseInt(
						String.valueOf(treeTableViewBomba.getSelectionModel().getSelectedItem().getValue().toString()));

				try {

					new BombaDao().listar().forEach(bomba -> {

						if (bomba.getId_bomba() == idBomba) {

							campoBomba.setText(bomba.getDescricao());
							listViewBico.setItems(bomba.getLista_bicos());
							lista_bico = bomba.getLista_bicos();
						}
					});
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// }
	}

	void carregarEventos() {
		btnAdicionarBico.setOnMouseClicked(event -> {
			if (treeTableViewBomba.getSelectionModel().getSelectedIndex() == -1) {
				if (!campoBico.getText().isEmpty()) {
					lista_bico.add(new Bico(0, 0, campoBico.getText()));
					campoBico.setText("");
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Bico cadastrado com sucesso", 4000); 
				} else{
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000);
				}

			} else if (treeTableViewBomba.getSelectionModel().getSelectedIndex() != -1) {

				idBomba = Integer
						.parseInt(treeTableViewBomba.getSelectionModel().getSelectedItem().getValue().toString());

				if (!campoBico.getText().isEmpty()) {
					try {
						lista_bico.add(new Bico(0, idBomba, campoBico.getText()));
						new BombaDao().cadastrarBico(new Bico(0, idBomba, campoBico.getText()));
						campoBico.setText("");
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Bico cadastrado com sucesso", 4000); 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else{
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000);
				}
			}

			listViewBico.setItems(lista_bico);
		});
		btnExcluirBico.setOnMouseClicked(event -> {
			try {
				if (treeTableViewBomba.getSelectionModel().getSelectedIndex() == -1) {
					if (listViewBico.getSelectionModel().getSelectedIndex() != -1) {
						for (int i = 0; i < lista_bico.size(); ++i) {
							if (lista_bico.get(i).getDescricao()
									.equals(listViewBico.getSelectionModel().getSelectedItem().getDescricao())) {
								lista_bico.remove(i);
								
							
									snackBar = new JFXSnackbar(borderPaneTabela);
							//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
									snackBar.show("Bico removido com sucesso", 4000);
								
							}
						}
					}else {
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Seleciona bico na tabela", 4000); 
					}

				} else if (treeTableViewBomba.getSelectionModel().getSelectedIndex() != -1) {

					if (listViewBico.getSelectionModel().getSelectedIndex() != -1) {

						idBico = listViewBico.getSelectionModel().getSelectedItem().getId_bico();

						
						try {
							
							new BombaDao().excluirBico(idBico);
							for (int i = 0; i < lista_bico.size(); ++i) {
								if (lista_bico.get(i).getId_bico() == listViewBico.getSelectionModel().getSelectedItem()
										.getId_bico()) {
									lista_bico.remove(i);
									
								}
							}
							snackBar = new JFXSnackbar(borderPaneTabela);
					//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
							snackBar.show("Bico removido com sucesso", 4000);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							snackBar = new JFXSnackbar(borderPaneTabela);
				//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
							snackBar.show("Bico sendo utilizada", 4000); 
						}
					}else {
						snackBar = new JFXSnackbar(borderPaneTabela);
				//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Seleciona bico na tabela", 4000); 
					}

				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Seleciona bico na tabela", 4000); 
				}

				listViewBico.setItems(lista_bico);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		});

	}

	public void limparcampos() {
		campoBico.setText("");
		campoBomba.setText("");
		campoPesquisar.setText("");
		limparListView();
	}

	void carregarTabela() {

		atualizarTabela();

		// Criando as colunas da tabela
		JFXTreeTableColumn<BombaClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(50);
		JFXTreeTableColumn<BombaClass, String> colunaNome = new JFXTreeTableColumn<>("DESCRIÇÃO");
		colunaNome.setPrefWidth(100);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<BombaClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id;
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<BombaClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().descricao;
			else
				return colunaNome.getComputedValue(param);
		});

		ObservableList<BombaClass> lista_bombas = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		try {
			new BombaDao().listar().forEach(bomba -> {
				lista_bombas.add(new BombaClass(bomba.getId_bomba(), bomba.getDescricao()));
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewBomba.setRoot(new RecursiveTreeItem<BombaClass>(lista_bombas, RecursiveTreeObject::getChildren));

		treeTableViewBomba.getColumns().setAll(colunaId, colunaNome);
		treeTableViewBomba.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewBomba
					.setPredicate(person -> person.getValue().id.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().descricao.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		carregarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class BombaClass extends RecursiveTreeObject<BombaClass> {

		StringProperty id;
		StringProperty descricao;

		public BombaClass(int id, String nome) {
			super();
			this.id = new SimpleStringProperty(String.valueOf(id));
			this.descricao = new SimpleStringProperty(nome);
		}

		// retorna o id do funcionario com isso posso alterar e remover qualquer
		// funcionario do banco de dados
		@Override
		public String toString() {
			return String.valueOf(id.getValue());
		}
	}

	void atualizarTabela() {

		try {
			treeTableViewBomba = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewBomba);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void limparListView() {
		lista_bico.clear();
		listViewBico.setItems(lista_bico);
	}
}
