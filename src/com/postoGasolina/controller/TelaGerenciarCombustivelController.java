package com.postoGasolina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.BombaDao;
import com.postoGasolina.dao.CombustiveisDao;
import com.postoGasolina.dao.TipoCombustivelDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Bico;
import com.postoGasolina.model.Bomba;
import com.postoGasolina.model.Combustivel;
import com.postoGasolina.model.Tipo_combustivel;
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

public class TelaGerenciarCombustivelController implements Initializable {

	@FXML
	private ComboBox<Bomba> comboBoxBomba;

	@FXML
	private final NumeroTextField campoPreco = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	@FXML
	private ComboBox<Tipo_combustivel> comboboxTipoCombustivel;

	@FXML
	private ComboBox<Bico> combomBoxBico;

	@FXML
	private JFXTextField campoDescricao;

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
	private JFXTreeTableView<CombustivelClass> treeTableViewCombustivel;

	@FXML
	private ImageView btnTipoCombustivel;

	@FXML
	private ImageView btnBomba;

	@FXML
	private GridPane gridPaneTop;

	private int idCombustivel;
	
	private JFXSnackbar snackBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		preencherComboBox();
		carregarTabela();

	}

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewCombustivel.getSelectionModel().getSelectedIndex() != -1) {

			idCombustivel = Integer
					.parseInt(treeTableViewCombustivel.getSelectionModel().getSelectedItem().getValue().toString());
			try {
				new CombustiveisDao().remover(idCombustivel);
				carregarTabela();
				limparCampos();
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Combustível removido com sucesso", 4000);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Combustível sendo utilizado", 4000);
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona combustível na tabela", 4000);
		}
	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparCampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewCombustivel.getSelectionModel().getSelectedIndex() == -1) {
			try {
				if (comboBoxBomba.getValue() != null && comboboxTipoCombustivel.getValue() != null
						&& combomBoxBico.getValue() != null && !campoDescricao.getText().isEmpty()
						&& !campoPreco.getNumber().equals(BigDecimal.ZERO)) {

				boolean achouBico = false;

				try {
					for (Combustivel combustivel : new CombustiveisDao().listar()) {
						if (comboBoxBomba.getValue().getId_bomba() == combustivel.getBomba().getId_bomba()
								&& combomBoxBico.getValue().getId_bico() == combustivel.getBico().getId_bico()) {
							achouBico = true;
						}
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
					
				if (achouBico == false) {
					
						new CombustiveisDao().cadastrar(
								new Combustivel(0, comboboxTipoCombustivel.getSelectionModel().getSelectedItem(),
										comboBoxBomba.getSelectionModel().getSelectedItem(),
										combomBoxBico.getSelectionModel().getSelectedItem(), campoDescricao.getText(),
										campoPreco.getNumber()));

						carregarTabela();
						limparCampos();

						snackBar = new JFXSnackbar(borderPaneTabela);
				//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Combustível cadastrado com sucesso", 4000);

					} else {
						snackBar = new JFXSnackbar(borderPaneTabela);
				//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Bico sendo utilizado", 4000);
					}
				} else {
					
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informados", 4000);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewCombustivel.getSelectionModel().getSelectedIndex() != -1) {

			if (comboBoxBomba.getValue() != null && comboboxTipoCombustivel.getValue() != null
					&& combomBoxBico.getValue() != null && !campoDescricao.getText().isEmpty()
					&& !campoPreco.getNumber().equals(BigDecimal.ZERO)) {

			idCombustivel = Integer
					.parseInt(treeTableViewCombustivel.getSelectionModel().getSelectedItem().getValue().toString());

			boolean achouBico = false;
			
			/*
			try {
				for (Combustivel combustivel : new CombustiveisDao().listar()) {
					if (comboBoxBomba.getValue().getId_bomba() == combustivel.getBomba().getId_bomba()
							&& combomBoxBico.getValue().getId_bico() == combustivel.getBico().getId_bico()) {
						achouBico = true;
					}
				}
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/

			if (achouBico == false) {


					try {
						new CombustiveisDao().alterar(new Combustivel(idCombustivel,
								comboboxTipoCombustivel.getSelectionModel().getSelectedItem(),
								comboBoxBomba.getSelectionModel().getSelectedItem(),
								combomBoxBico.getSelectionModel().getSelectedItem(), campoDescricao.getText(),
								campoPreco.getNumber()));

						carregarTabela();
						limparCampos();

						snackBar = new JFXSnackbar(borderPaneTabela);
				//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Combustível alterado com sucesso", 4000);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					
					snackBar = new JFXSnackbar(borderPaneTabela);
				//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Bico sendo utilizado", 4000);
				}

			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Campos obrigatórios não informados", 4000); 
			}
		}
	}

	public void carregarDados() {
		// TODO Auto-generated method stub

		treeTableViewCombustivel.setOnMouseClicked(event -> {
			if (treeTableViewCombustivel.getSelectionModel().getSelectedIndex() != -1) {
				idCombustivel = Integer
						.parseInt(treeTableViewCombustivel.getSelectionModel().getSelectedItem().getValue().toString());

				try {

					new CombustiveisDao().listar().forEach(combustivel -> {

						if (combustivel.getId_combustivel() == idCombustivel) {

							campoDescricao.setText(combustivel.getDescricao());
							campoPreco.setNumber(combustivel.getPreco_venda());
							comboBoxBomba.setValue(combustivel.getBomba());
							comboboxTipoCombustivel.setValue(combustivel.getTipoCombustivel());
							combomBoxBico.setValue(combustivel.getBico());

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

	void carregarTabela() {

		atualizarTabela();

		// Criando as colunas da tabela
		JFXTreeTableColumn<CombustivelClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(50);
		JFXTreeTableColumn<CombustivelClass, String> colunaTipoCombustivel = new JFXTreeTableColumn<>(
				"Tipo de combustíveis");
		colunaTipoCombustivel.setPrefWidth(185);
		JFXTreeTableColumn<CombustivelClass, String> colunaEstoqueDisponivel = new JFXTreeTableColumn<>(
				"Est. disponível");
		colunaEstoqueDisponivel.setPrefWidth(110);
		JFXTreeTableColumn<CombustivelClass, String> colunaPreco = new JFXTreeTableColumn<>("Preço");
		colunaPreco.setPrefWidth(105);
		JFXTreeTableColumn<CombustivelClass, String> colunaBomba = new JFXTreeTableColumn<>("Bomba");
		colunaBomba.setPrefWidth(84);
		JFXTreeTableColumn<CombustivelClass, String> colunaBico = new JFXTreeTableColumn<>("Bico");
		colunaBico.setPrefWidth(84);
		JFXTreeTableColumn<CombustivelClass, String> colunaUnidadeMedida = new JFXTreeTableColumn<>("Uni. medida");
		colunaUnidadeMedida.setPrefWidth(95);
		JFXTreeTableColumn<CombustivelClass, String> colunaEstoqueMinimo = new JFXTreeTableColumn<>("Est. min");
		colunaEstoqueMinimo.setPrefWidth(110);
		JFXTreeTableColumn<CombustivelClass, String> colunaEstoqueMaximo = new JFXTreeTableColumn<>("Est. max");
		colunaEstoqueMaximo.setPrefWidth(110);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id;
			else
				return colunaId.getComputedValue(param);
		});
		colunaTipoCombustivel
				.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
					if (colunaTipoCombustivel.validateValue(param))
						return param.getValue().getValue().tipoCombustivel;
					else
						return colunaTipoCombustivel.getComputedValue(param);
				});
		colunaEstoqueDisponivel
				.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
					if (colunaEstoqueDisponivel.validateValue(param))
						return param.getValue().getValue().estoqueDisponivel;
					else
						return colunaEstoqueDisponivel.getComputedValue(param);
				});
		colunaPreco.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
			if (colunaPreco.validateValue(param))
				return param.getValue().getValue().preco;
			else
				return colunaPreco.getComputedValue(param);
		});
		colunaBomba.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
			if (colunaBomba.validateValue(param))
				return param.getValue().getValue().bomba;
			else
				return colunaBomba.getComputedValue(param);
		});
		colunaBico.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
			if (colunaBico.validateValue(param))
				return param.getValue().getValue().bico;
			else
				return colunaBico.getComputedValue(param);
		});
		colunaUnidadeMedida.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
			if (colunaUnidadeMedida.validateValue(param))
				return param.getValue().getValue().unidadeMedida;
			else
				return colunaUnidadeMedida.getComputedValue(param);
		});
		colunaEstoqueMinimo.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
			if (colunaEstoqueMinimo.validateValue(param))
				return param.getValue().getValue().estoqueMinimo;
			else
				return colunaEstoqueMinimo.getComputedValue(param);
		});
		colunaEstoqueMaximo.setCellValueFactory((TreeTableColumn.CellDataFeatures<CombustivelClass, String> param) -> {
			if (colunaEstoqueMaximo.validateValue(param))
				return param.getValue().getValue().estoqueMaximo;
			else
				return colunaEstoqueMaximo.getComputedValue(param);
		});

		ObservableList<CombustivelClass> lista_combustivel = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		/*
		 * try {
		 * 
		 * 
		 * sqlClienteFisica.listar().forEach(cliente -> { lista_clientes.add(new
		 * CombustivelClass(String.valueOf(cliente.getId_cliente_fisica()),
		 * cliente.getPessoa().getNome(), cliente.getPessoa().getCpf(),
		 * String.valueOf(cliente.getPessoa().getId_pessoa()),
		 * String.valueOf(cliente.getEndereco().getId_endereco()))); });
		 * 
		 * 
		 * 
		 * } catch (ClassNotFoundException | SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			new CombustiveisDao().listar().forEach(combustivel -> {
				lista_combustivel.add(new CombustivelClass(combustivel.getId_combustivel(),
						combustivel.getTipoCombustivel().getNome(), combustivel.getTipoCombustivel().getQuantidade(),
						combustivel.getPreco_venda(), combustivel.getBomba().getDescricao(),
						combustivel.getBico().getDescricao(),
						combustivel.getTipoCombustivel().getUnidade_medida().getNome(),
						combustivel.getTipoCombustivel().getEstoque_minimo(),
						combustivel.getTipoCombustivel().getEstoque_maximo()));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		treeTableViewCombustivel
				.setRoot(new RecursiveTreeItem<CombustivelClass>(lista_combustivel, RecursiveTreeObject::getChildren));

		treeTableViewCombustivel.getColumns().setAll(colunaId, colunaTipoCombustivel, colunaEstoqueDisponivel,
				colunaPreco, colunaBomba, colunaBico, colunaUnidadeMedida, colunaEstoqueMinimo, colunaEstoqueMaximo);
		treeTableViewCombustivel.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewCombustivel
					.setPredicate(person -> person.getValue().id.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().tipoCombustivel.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().estoqueDisponivel.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().preco.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().bomba.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().bico.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().unidadeMedida.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().estoqueMinimo.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().estoqueMaximo.get().toLowerCase().contains(pesquisa.toLowerCase())

			);
		});
		carregarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class CombustivelClass extends RecursiveTreeObject<CombustivelClass> {

		StringProperty id;
		StringProperty tipoCombustivel;
		StringProperty estoqueDisponivel;
		StringProperty preco;
		StringProperty bomba;
		StringProperty bico;
		StringProperty unidadeMedida;
		StringProperty estoqueMinimo;
		StringProperty estoqueMaximo;

		// StringProperty telefones[];

		@Override
		public String toString() {
			return String.valueOf(id.getValue());
		}

		public CombustivelClass(int id, String tipoCombustivel, BigDecimal estoqueDisponivel, BigDecimal preco,
				String bomba, String bico, String unidadeMedida, BigDecimal estoqueMinimo, BigDecimal estoqueMaximo) {

			this.id = new SimpleStringProperty(String.valueOf(id));
			this.tipoCombustivel = new SimpleStringProperty(tipoCombustivel);
			this.estoqueDisponivel = new SimpleStringProperty(
					String.valueOf(new NumeroTextField(estoqueDisponivel).getText()));
			this.preco = new SimpleStringProperty(String.valueOf(
					new NumeroTextField(preco, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));
			this.bomba = new SimpleStringProperty(bomba);
			this.bico = new SimpleStringProperty(bico);
			this.unidadeMedida = new SimpleStringProperty(String.valueOf(unidadeMedida));
			this.estoqueMinimo = new SimpleStringProperty(String.valueOf(new NumeroTextField(estoqueMinimo).getText()));
			this.estoqueMaximo = new SimpleStringProperty(String.valueOf(new NumeroTextField(estoqueMaximo).getText()));
		}

	}

	void atualizarTabela() {

		try {
			treeTableViewCombustivel = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewCombustivel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void preencherComboBox() {
		btnTipoCombustivel.setOnMouseClicked(event -> {
			new Main().carregarTipoCombustivel();
		});
		btnBomba.setOnMouseClicked(event -> {
			new Main().carregarTelaCadastrarBomba();
		});

		// IMPORTANTE ATUALIZA COMBOBOX
		comboBoxBomba.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxBomba.isFocused()) {
				try {
					comboBoxBomba.setItems(new BombaDao().listar());
					combomBoxBico.setValue(null);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// IMPORTANTE ATUALIZA COMBOBOX
		combomBoxBico.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (combomBoxBico.isFocused()) {
				try {
						new BombaDao().listar().forEach(bomba -> {
							if (comboBoxBomba.getSelectionModel().getSelectedItem().getId_bomba() == bomba
									.getId_bomba()) {
								combomBoxBico.setItems(bomba.getLista_bicos());
							}
						});
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// IMPORTANTE ATUALIZA COMBOBOX
		comboboxTipoCombustivel.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboboxTipoCombustivel.isFocused()) {
				try {
					comboboxTipoCombustivel.setItems(new TipoCombustivelDao().listar());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		String style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionario.css").toExternalForm();

		campoPreco.getStylesheets().add(style);
		campoPreco.getStyleClass().add("formata-campo");
		campoPreco.setUnFocusColor(Color.WHITE);
		campoPreco.setFocusColor(Color.WHITE);
		gridPaneTop.add(campoPreco, 1, 3);
		gridPaneTop.setMargin(campoPreco, new Insets(0, 10, 21, 390));
	}

	void limparCampos() {
		campoDescricao.setText("");
		campoPreco.setNumber(BigDecimal.ZERO);
		comboBoxBomba.setValue(null);
		comboboxTipoCombustivel.setValue(null);
		combomBoxBico.setValue(null);
		campoPesquisar.setText("");
	}

}