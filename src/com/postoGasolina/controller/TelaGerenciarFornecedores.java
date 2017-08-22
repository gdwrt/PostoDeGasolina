package com.postoGasolina.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.FornecedoresDao;
import com.postoGasolina.model.BuscaCnpj;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Fornecedor;
import com.postoGasolina.model.Telefone;
import com.postoGasolina.model.WebServiceCep;
import com.postoGasolina.util.CNPJ;
import com.postoGasolina.util.TextFieldFormatter;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class TelaGerenciarFornecedores implements Initializable {
	@FXML
	private JFXTextField campoRazaoSocial;

	@FXML
	private JFXDatePicker comboBoxDataSituacao;

	@FXML
	private JFXTextField campoCnpj;

	@FXML
	private JFXTextField campoStatus;

	@FXML
	private JFXTextField campoEmail;

	@FXML
	private JFXTextField campoCep;

	@FXML
	private JFXTextField campoEndereco;

	@FXML
	private JFXTextField campoNumero;

	@FXML
	private JFXTextField campoComplemento;

	@FXML
	private JFXTextField campoCidade;

	@FXML
	private JFXTextField campoBairro;

	@FXML
	private JFXTextArea textAreaInformacao;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXTextField campoCelular;

	@FXML
	private JFXTextField campoTelefone;

	@FXML
	private ComboBox<String> comboBoxEstado;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXListView<Telefone> listViewTelefones;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private ImageView btnAdicionarTelefone;

	@FXML
	private ImageView btnRemoverTelefone;

	@FXML
	private JFXTextField campoIe;

	@FXML
	private JFXTextField campoSite;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	private JFXTreeTableView<FornecedorClass> treeTableViewFornecedores;
	private int idFornecedor;
	private ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
	private String telefone1 = "";
	private JFXSnackbar snackBar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		preencherComboBox();
		try {
			carregarTabela();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		validacaoCampos();

	}

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() != -1) {
			String[] ids = treeTableViewFornecedores.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idFornecedor = Integer.parseInt(ids[0]);
			try {
				new FornecedoresDao().remover(idFornecedor);
				carregarTabela();
				limparCampos();

				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Fornecedor removido com sucesso", 4000);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Não foi possivel remover fornecedor", 4000);
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona fornecedor na tabela", 4000);
		}
	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparCampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (comboBoxDataSituacao.getValue() != null && comboBoxEstado.getValue() != null
						&& !campoCnpj.getText().isEmpty() && !campoRazaoSocial.getText().isEmpty()
						&& !campoCep.getText().isEmpty()) {

					try {
						new FornecedoresDao().cadastrar(new Fornecedor(0,
								new Endereco(0, campoCep.getText().replaceAll("[.]", ""), campoEndereco.getText(),
										campoNumero.getText(), campoComplemento.getText(), campoBairro.getText(),
										campoCidade.getText(), comboBoxEstado.getValue()),
								campoRazaoSocial.getText(), campoCnpj.getText(), campoIe.getText(),
								campoEmail.getText(), campoSite.getText(), campoStatus.getText(),
								textAreaInformacao.getText(), comboBoxDataSituacao.getValue(), lista_telefones));

						carregarTabela();
						limparCampos();

						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Fornecedor cadastrado com sucesso", 4000);

					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewFornecedores.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idFornecedor = Integer.parseInt(ids[0]);
			int idEndereco = Integer.parseInt(ids[1]);

			if (comboBoxDataSituacao.getValue() != null && comboBoxEstado.getValue() != null
					&& !campoCnpj.getText().isEmpty() && !campoRazaoSocial.getText().isEmpty()
					&& !campoCep.getText().isEmpty()) {

				try {
					new FornecedoresDao().alterar(new Fornecedor(idFornecedor,
							new Endereco(idEndereco, campoCep.getText().replaceAll("[.]", ""), campoEndereco.getText(),
									campoNumero.getText(), campoComplemento.getText(), campoBairro.getText(),
									campoCidade.getText(), comboBoxEstado.getValue()),
							campoRazaoSocial.getText(), campoCnpj.getText(), campoIe.getText(), campoEmail.getText(),
							campoSite.getText(), campoStatus.getText(), textAreaInformacao.getText(),
							comboBoxDataSituacao.getValue(), lista_telefones));

					carregarTabela();
					limparCampos();

					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Fornecedor alterado com sucesso", 4000);

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Campos obrigatórios não informado", 4000);
			}

		}

	}

	private void visualizarDados() {
		// TODO Auto-generated method stub

		treeTableViewFornecedores.setOnMouseClicked(event -> {
			if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() != -1) {
				String[] ids = treeTableViewFornecedores.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idFornecedor = Integer.parseInt(ids[0]);

				try {

					new FornecedoresDao().listar().forEach(fornecedor -> {

						if (fornecedor.getId_fornecedor() == idFornecedor) {
							campoBairro.setText(fornecedor.getEndereco().getBairro());
							campoCep.setText(fornecedor.getEndereco().getCep());
							campoCidade.setText(fornecedor.getEndereco().getCidade());
							campoComplemento.setText(fornecedor.getEndereco().getComplemento());
							campoCnpj.setText(fornecedor.getCnpj());
							campoEmail.setText(fornecedor.getEmail());
							campoEndereco.setText(fornecedor.getEndereco().getEndereco());
							campoStatus.setText(fornecedor.getStatus());
							campoRazaoSocial.setText(fornecedor.getRazao_social());
							campoNumero.setText(fornecedor.getEndereco().getNumero());
							campoTelefone.setText("");
							comboBoxDataSituacao.setValue(fornecedor.getData_situacao());
							textAreaInformacao.setText(fornecedor.getObservacoes());
							comboBoxEstado.setValue(fornecedor.getEndereco().getEstado());
							campoSite.setText(fornecedor.getSite());

							// lista_telefones.clear();
							lista_telefones = fornecedor.getListaTelefone();
							listViewTelefones.setItems(lista_telefones);
						}
					});
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	void carregarTabela() {

		atualizarTabela();

		// Criando as colunas da tabela
		JFXTreeTableColumn<FornecedorClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(55);
		JFXTreeTableColumn<FornecedorClass, String> colunaNome = new JFXTreeTableColumn<>("RAZÃO SOCIAL");
		colunaNome.setPrefWidth(208);
		JFXTreeTableColumn<FornecedorClass, String> colunaCnpj = new JFXTreeTableColumn<>("CNPJ");
		colunaCnpj.setPrefWidth(165);
		JFXTreeTableColumn<FornecedorClass, String> colunaDataSituacao = new JFXTreeTableColumn<>("STATUS");
		colunaDataSituacao.setPrefWidth(124);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<FornecedorClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().idFornecedor;
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<FornecedorClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().nome;
			else
				return colunaNome.getComputedValue(param);
		});
		colunaCnpj.setCellValueFactory((TreeTableColumn.CellDataFeatures<FornecedorClass, String> param) -> {
			if (colunaCnpj.validateValue(param))
				return param.getValue().getValue().cnpj;
			else
				return colunaCnpj.getComputedValue(param);
		});
		colunaDataSituacao.setCellValueFactory((TreeTableColumn.CellDataFeatures<FornecedorClass, String> param) -> {
			if (colunaDataSituacao.validateValue(param))
				return param.getValue().getValue().status;
			else
				return colunaDataSituacao.getComputedValue(param);
		});

		ObservableList<FornecedorClass> lista_fornecedores = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		try {
			new FornecedoresDao().listar().forEach(cliente -> {
				lista_fornecedores.add(new FornecedorClass(cliente.getId_fornecedor(), cliente.getRazao_social(),
						cliente.getCnpj(), cliente.getEndereco().getId_endereco(), cliente.getStatus()));
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewFornecedores
				.setRoot(new RecursiveTreeItem<FornecedorClass>(lista_fornecedores, RecursiveTreeObject::getChildren));

		treeTableViewFornecedores.getColumns().setAll(colunaId, colunaNome, colunaCnpj, colunaDataSituacao);
		treeTableViewFornecedores.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewFornecedores.setPredicate(
					person -> person.getValue().idFornecedor.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().nome.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().cnpj.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().status.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		visualizarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class FornecedorClass extends RecursiveTreeObject<FornecedorClass> {

		StringProperty idFornecedor;
		StringProperty nome;
		StringProperty cnpj;
		StringProperty idEndereco;
		StringProperty status;

		public FornecedorClass(int idFornecedor, String nome, String cnpj, int idEndereco, String situacao) {
			super();
			this.idFornecedor = new SimpleStringProperty(String.valueOf(idFornecedor));
			this.nome = new SimpleStringProperty(nome);
			this.cnpj = new SimpleStringProperty(cnpj);
			this.idEndereco = new SimpleStringProperty(String.valueOf(idEndereco));
			this.status = new SimpleStringProperty(situacao);
		}

		// retorna o id do funcionario com isso posso alterar e remover qualquer
		// funcionario do banco de dados
		@Override
		public String toString() {
			return idFornecedor.getValue() + "/" + idEndereco.getValue();
		}
	}

	void atualizarTabela() {

		try {
			treeTableViewFornecedores = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewFornecedores);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void validacaoCampos() {

		campoCnpj.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("##.###.###/####-##");
			ttf.setTf(campoCnpj);
			ttf.formatter();

			try {
				if (campoCnpj.getText().length() == 18 && campoCnpj.getText().charAt(17) != ' ') {
					boolean cpf = new CNPJ(campoCnpj.getText()).isCNPJ();
					if (!cpf) {
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("CNPJ Inválido", 6000);
						campoCnpj.setText("");
					} else {
						new Thread(new Runnable() {
							public void run() {

								BuscaCnpj empresa = new BuscaCnpj(campoCnpj.getText());

								Platform.runLater(() -> {

									comboBoxEstado.setValue(empresa.getUf());
								});

								campoBairro.setText(empresa.getBairro());
								campoCep.setText(empresa.getCep());
								campoCidade.setText(empresa.getCidade());
								campoComplemento.setText(empresa.getComplemento());
								campoCnpj.setText(empresa.getCnpj());
								campoEmail.setText(empresa.getEmail());
								campoEndereco.setText(empresa.getEndereco());
								campoStatus.setText(empresa.getSituacao());
								campoRazaoSocial.setText(empresa.getNome());
								campoNumero.setText(empresa.getNumero());
								campoTelefone.setText("");
								final DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								final LocalDate dataFormatada = LocalDate.parse(empresa.getDataAbertura(), formataData);
								comboBoxDataSituacao.setValue(dataFormatada);
								textAreaInformacao.setText(empresa.getTAtividadePrincipal());

								try {

									telefone1 = "";
									for (String telefone : empresa.getListaTelefones()) {

										if (telefone.replaceAll(" ", "").equals(telefone1.replaceAll(" ", ""))) {
											lista_telefones.add(new Telefone(0, telefone));
											listViewTelefones.setItems(lista_telefones);
										}

										telefone1 = telefone;
									}
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}

							}
						}).start();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});

		campoCelular.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("(##)#####-####");
			ttf.setTf(campoCelular);
			ttf.formatter();
		});

		campoTelefone.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("(##)####-####");
			ttf.setTf(campoTelefone);
			ttf.formatter();
		});

		campoCep.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("#####-###");
			ttf.setTf(campoCep);
			ttf.formatter();

			try {
				if (campoCep.getText().length() == 9 && campoCep.getText().charAt(8) != ' ') {
					new Thread(new Runnable() {
						public void run() {
							WebServiceCep webServiceCep = WebServiceCep.searchCep(campoCep.getText());
							Platform.runLater(() -> comboBoxEstado.getSelectionModel()
									.select(!webServiceCep.getUf().isEmpty() ? webServiceCep.getUf() : null));
							campoBairro.setText(webServiceCep.getBairro());
							campoCidade.setText(webServiceCep.getCidade());
							campoEndereco.setText(webServiceCep.getLogradouroFull());
						}
					}).start();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		});

	}

	@FXML
	void btnAdicionarTelefone(MouseEvent event) {

		if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() == -1) {
			if (!campoCelular.getText().isEmpty()) {
				lista_telefones.add(new Telefone(0, campoCelular.getText()));
				campoCelular.setText("");

				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Número adicionado com sucesso", 4000);
			}
			if (!campoTelefone.getText().isEmpty()) {
				lista_telefones.add(new Telefone(0, campoTelefone.getText()));
				campoTelefone.setText("");

				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Número adicionado com sucesso", 4000);
			}

		} else if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewFornecedores.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idFornecedor = Integer.parseInt(ids[0]);

			if (!campoCelular.getText().isEmpty()) {
				try {
					lista_telefones.add(new Telefone(idFornecedor, campoCelular.getText()));
					new FornecedoresDao().adicionarTelefone(new Telefone(idFornecedor, campoCelular.getText()));
					campoCelular.setText("");

					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Número adicionado com sucesso", 4000);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!campoTelefone.getText().isEmpty()) {
				try {
					lista_telefones.add(new Telefone(idFornecedor, campoTelefone.getText()));
					new FornecedoresDao().adicionarTelefone(new Telefone(idFornecedor, campoTelefone.getText()));
					campoTelefone.setText("");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		listViewTelefones.setItems(lista_telefones);

	}

	@FXML
	void btnRemoverTelefone(MouseEvent event) {
		try {
			if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() == -1
					&& listViewTelefones.getSelectionModel().getSelectedIndex() != -1) {
				for (int i = 0; i < lista_telefones.size(); ++i) {
					if (lista_telefones.get(i).getTelefone()
							.equals(listViewTelefones.getSelectionModel().getSelectedItem().toString())) {
						lista_telefones.remove(i);
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Número removido com sucesso", 4000);
					}
				}

			} else if (treeTableViewFornecedores.getSelectionModel().getSelectedIndex() != -1
					&& listViewTelefones.getSelectionModel().getSelectedIndex() != -1) {

				String[] ids = treeTableViewFornecedores.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idFornecedor = Integer.parseInt(ids[0]);
				String telefone = listViewTelefones.getSelectionModel().getSelectedItem().getTelefone();

				for (int i = 0; i < lista_telefones.size(); ++i) {
					if (lista_telefones.get(i).getTelefone()
							.equals(listViewTelefones.getSelectionModel().getSelectedItem().toString())) {
						lista_telefones.remove(i);
						snackBar = new JFXSnackbar(borderPaneTabela);
		//				String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Número removido com sucesso", 4000);
					}
				}
				try {
					new FornecedoresDao().excluirTelefone(new Telefone(idFornecedor, telefone));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			listViewTelefones.setItems(lista_telefones);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	void preencherComboBox() {

		comboBoxEstado.getItems().addAll("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT",
				"PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO");

	}

	void limparCampos() {
		lista_telefones.clear();
		campoBairro.setText("");
		campoCep.setText("");
		campoCidade.setText("");
		campoComplemento.setText("");
		campoStatus.setText("");
		campoEmail.setText("");
		campoEndereco.setText("");
		campoRazaoSocial.setText("");
		campoNumero.setText("");
		campoPesquisar.setText("");
		campoTelefone.setText("");
		comboBoxDataSituacao.setValue(null);
		comboBoxEstado.setValue(null);
		textAreaInformacao.setText("");
		campoCelular.setText("");
		campoCnpj.setText("");
		campoSite.setText("");
	}
}
