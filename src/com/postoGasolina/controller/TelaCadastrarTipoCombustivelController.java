package com.postoGasolina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.TipoCombustivelDao;
import com.postoGasolina.dao.UnidadeMedidaDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Tipo_combustivel;
import com.postoGasolina.model.Unidade_medida;
import com.postoGasolina.util.NumeroTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class TelaCadastrarTipoCombustivelController implements Initializable {
	@FXML
	private JFXTextField campoNome;

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
	private ComboBox<Unidade_medida> comboBoxUnidadeMedida;

	@FXML
	private ImageView btnUnidadeMedida;

	@FXML
	private JFXTreeTableView<TipoCombbustivelClass> treeTableViewTipoCombustivel;

	private int idTipoCombustivel;

	@FXML
	private final NumeroTextField campoQuantidade = new NumeroTextField(null);

	@FXML
	private final NumeroTextField campoEstoqueMinimo = new NumeroTextField(null);

	@FXML
	private final NumeroTextField campoEstoqueMaximo = new NumeroTextField(null);

	@FXML
	private GridPane gridPaneTipoCombustivel;
	
	private JFXSnackbar snackBar; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		carregarComboBox();
		formatarDecimal();
		carregarLabelFloat();

		carregarTabela();
		
	}

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewTipoCombustivel.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewTipoCombustivel.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idTipoCombustivel = Integer.parseInt(ids[0]);
			try {
				new TipoCombustivelDao().remover(idTipoCombustivel);
				carregarTabela();
				limparcampos();
				
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Tipo combustível removido com sucesso", 4000); 
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Tipo combustível sendo utilizado", 4000); 
				
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona tipo de combustível na tabela", 4000); 
		}

	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparcampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewTipoCombustivel.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (comboBoxUnidadeMedida.getValue() != null && campoQuantidade.getNumber() != null
						&& campoEstoqueMaximo.getNumber() != null && campoEstoqueMinimo.getNumber() != null
						&& !campoNome.getText().isEmpty()) {

					new TipoCombustivelDao().cadastrar(new Tipo_combustivel(0,
							new Unidade_medida(
									comboBoxUnidadeMedida.getSelectionModel().getSelectedItem().getId_unidade_medida(),
									comboBoxUnidadeMedida.getSelectionModel().getSelectedItem().getNome()),
							campoNome.getText(), campoQuantidade.getNumber(), campoEstoqueMaximo.getNumber(),
							campoEstoqueMinimo.getNumber()));

					carregarTabela();
					limparcampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Tipo combustível cadastrado com sucesso", 4000); 

				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000); 
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewTipoCombustivel.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewTipoCombustivel.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idTipoCombustivel = Integer.parseInt(ids[0]);

			if (comboBoxUnidadeMedida.getValue() != null && campoQuantidade.getNumber() != null
					&& campoEstoqueMaximo.getNumber() != null && campoEstoqueMinimo.getNumber() != null
					&& !campoNome.getText().isEmpty()) {

				try {
					new TipoCombustivelDao().alterar(new Tipo_combustivel(idTipoCombustivel,
							new Unidade_medida(
									comboBoxUnidadeMedida.getSelectionModel().getSelectedItem().getId_unidade_medida(),
									comboBoxUnidadeMedida.getSelectionModel().getSelectedItem().getNome()),
							campoNome.getText(), campoQuantidade.getNumber(), campoEstoqueMaximo.getNumber(),
							campoEstoqueMinimo.getNumber()));
					

					carregarTabela();
					limparcampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
					String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Tipo combustível alterado com sucesso", 4000); 
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
				String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Campos obrigatórios não informado", 4000); 
			}

		}
	}

	public void carregarDados() {
		// TODO Auto-generated method stub

		treeTableViewTipoCombustivel.setOnMouseClicked(event -> {

			if (treeTableViewTipoCombustivel.getSelectionModel().getSelectedIndex() != -1) {
				idTipoCombustivel = Integer.parseInt(String.valueOf(
						treeTableViewTipoCombustivel.getSelectionModel().getSelectedItem().getValue().toString()));

				try {

					new TipoCombustivelDao().listar().forEach(tipoCombustivel -> {

						if (tipoCombustivel.getId_tipo_combustivel() == idTipoCombustivel) {

							campoNome.setText(tipoCombustivel.getNome());
							campoQuantidade.setNumber(tipoCombustivel.getQuantidade());
							campoEstoqueMaximo.setNumber(tipoCombustivel.getEstoque_maximo());
							campoEstoqueMinimo.setNumber(tipoCombustivel.getEstoque_minimo());
							comboBoxUnidadeMedida.setValue(tipoCombustivel.getUnidade_medida());

						}
					});
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	void carregarComboBox() {
		btnUnidadeMedida.setOnMouseClicked(event -> {
			new Main().carregarTelaUnidadeMedida2();
		});
		;

		comboBoxUnidadeMedida.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxUnidadeMedida.isFocused()) {
				try {
					comboBoxUnidadeMedida.setItems(new UnidadeMedidaDao().listar());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


	}

	void formatarDecimal() {
		try {
			String style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionario.css")
					.toExternalForm();

			campoQuantidade.getStylesheets().add(style);
			campoQuantidade.getStyleClass().add("formata-campo");
			campoQuantidade.setUnFocusColor(Color.WHITE);
			campoQuantidade.setFocusColor(Color.WHITE);
			gridPaneTipoCombustivel.add(campoQuantidade, 0, 3);
			gridPaneTipoCombustivel.setMargin(campoQuantidade, new Insets(0, 0, 0, 200));

			campoEstoqueMinimo.getStylesheets().add(style);
			campoEstoqueMinimo.getStyleClass().add("formata-campo");
			campoEstoqueMinimo.setUnFocusColor(Color.WHITE);
			campoEstoqueMinimo.setFocusColor(Color.WHITE);
			gridPaneTipoCombustivel.add(campoEstoqueMinimo, 0, 4);
			gridPaneTipoCombustivel.setMargin(campoEstoqueMinimo, new Insets(0, 0, 0, 200));

			campoEstoqueMaximo.getStylesheets().add(style);
			campoEstoqueMaximo.getStyleClass().add("formata-campo");
			campoEstoqueMaximo.setUnFocusColor(Color.WHITE);
			campoEstoqueMaximo.setFocusColor(Color.WHITE);
			gridPaneTipoCombustivel.add(campoEstoqueMaximo, 0, 4);
			gridPaneTipoCombustivel.setMargin(campoEstoqueMaximo, new Insets(0, 180, 0, 5));

			gridPaneTipoCombustivel.getStylesheets().add(style);

			campoNome.setFocusTraversable(false);

			// gridPaneTipoCombustivel.add(campo,0,3);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	void carregarLabelFloat() {
		campoEstoqueMaximo.setLabelFloat(true);
		campoEstoqueMaximo.setPromptText("Estoque máximo *");
		campoEstoqueMinimo.setLabelFloat(true);
		campoEstoqueMinimo.setPromptText("Estoque mínimo *");
		campoNome.setLabelFloat(true);
		campoQuantidade.setLabelFloat(true);
		campoQuantidade.setPromptText("Quantidade *");
	}

	public void limparcampos() {
		campoEstoqueMaximo.setNumber(BigDecimal.ZERO);
		campoEstoqueMinimo.setNumber(BigDecimal.ZERO);
		campoNome.setText("");
		campoPesquisar.setText("");
		campoQuantidade.setNumber(BigDecimal.ZERO);
		comboBoxUnidadeMedida.setValue(null);
	}

	void carregarTabela() {

		atualizarTabela();

		// Criando as colunas da tabela
		JFXTreeTableColumn<TipoCombbustivelClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(50);
		JFXTreeTableColumn<TipoCombbustivelClass, String> colunaNome = new JFXTreeTableColumn<>("Descrição");
		colunaNome.setPrefWidth(200);
		JFXTreeTableColumn<TipoCombbustivelClass, String> colunaQuantidade = new JFXTreeTableColumn<>("Quantidade");
		colunaQuantidade.setPrefWidth(120);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoCombbustivelClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id;
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoCombbustivelClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().nome;
			else
				return colunaNome.getComputedValue(param);
		});
		colunaQuantidade
				.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoCombbustivelClass, String> param) -> {
					if (colunaQuantidade.validateValue(param))
						return param.getValue().getValue().quantidade;
					else
						return colunaQuantidade.getComputedValue(param);
				});

		ObservableList<TipoCombbustivelClass> lista_tiposCombustiveis = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		try {
			new TipoCombustivelDao().listar().forEach(tipoCombustivel -> {
				lista_tiposCombustiveis.add(new TipoCombbustivelClass(tipoCombustivel.getId_tipo_combustivel(),
						tipoCombustivel.getNome(), tipoCombustivel.getQuantidade()

				));
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewTipoCombustivel.setRoot(new RecursiveTreeItem<TipoCombbustivelClass>(lista_tiposCombustiveis,
				RecursiveTreeObject::getChildren));

		treeTableViewTipoCombustivel.getColumns().setAll(colunaId, colunaNome, colunaQuantidade);
		treeTableViewTipoCombustivel.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewTipoCombustivel
					.setPredicate(person -> person.getValue().id.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().nome.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().quantidade.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		carregarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class TipoCombbustivelClass extends RecursiveTreeObject<TipoCombbustivelClass> {

		StringProperty id;
		StringProperty nome;
		StringProperty quantidade;

		public TipoCombbustivelClass(int id, String nome, BigDecimal quantidade) {
			super();
			this.id = new SimpleStringProperty(String.valueOf(id));
			this.nome = new SimpleStringProperty(nome);
			this.quantidade = new SimpleStringProperty(String.valueOf(new NumeroTextField(quantidade).getText()));
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
			treeTableViewTipoCombustivel = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewTipoCombustivel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
