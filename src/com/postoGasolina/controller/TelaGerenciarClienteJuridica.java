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
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.ClienteJuridicaDao;
import com.postoGasolina.model.BuscaCnpj;
import com.postoGasolina.model.Cliente_juridica;
import com.postoGasolina.model.Endereco;
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

public class TelaGerenciarClienteJuridica implements Initializable {

	@FXML
	private JFXTextField campoNome;

	@FXML
	private JFXDatePicker comboBoxDataSituacao;

	@FXML
	private JFXTextField campoCnpj;

	@FXML
	private JFXTextField campoSituacao;

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
	private JFXToggleButton btnPessoaJuridica;

	@FXML
	private JFXTextField campoIe;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	@FXML
	private JFXTreeTableView<ClienteJuridicoClass> treeTableViewClienteJuridica;

	private int idCliente;

	private String telefone1 = "";

	private ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();

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
	void OnActionBtnPessoaJuridica(ActionEvent event) {
		if (btnPessoaJuridica.isSelected()) {
			TelaGerenciarClienteController.t.setText("1");
		} else {
			TelaGerenciarClienteController.t.setText("0");
		}
	}

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() != -1) {
			String[] ids = treeTableViewClienteJuridica.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idCliente = Integer.parseInt(ids[0]);
			try {
				new ClienteJuridicaDao().remover(idCliente);
				carregarTabela();
				limparCampos();
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Cliente removido com sucesso", 4000); 
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Cliente sendo utilizado", 4000);
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
	//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona cliente na tabela", 4000);
		}
	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparCampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (comboBoxDataSituacao.getValue() != null && comboBoxEstado.getValue() != null
						&& !campoCnpj.getText().isEmpty() && !campoNome.getText().isEmpty()
						&& !campoCep.getText().isEmpty()) {

					try {
						new ClienteJuridicaDao().cadastrar(new Cliente_juridica(0,
								new Endereco(0, campoCep.getText().replaceAll("[.]", ""), campoEndereco.getText(), campoNumero.getText(),
										campoComplemento.getText(), campoBairro.getText(), campoCidade.getText(),
										comboBoxEstado.getValue()),
								campoNome.getText(), campoCnpj.getText(), campoSituacao.getText(),
								comboBoxDataSituacao.getValue(), campoIe.getText(), campoEmail.getText(),
								textAreaInformacao.getText(), lista_telefones));
						
						carregarTabela();
						limparCampos();
						
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Cliente cadastrado com sucesso", 4000); 
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
					snackBar.show("Campos obrigatórios nao informado", 4000); 
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewClienteJuridica.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idCliente = Integer.parseInt(ids[0]);
			int idEndereco = Integer.parseInt(ids[1]);

			if (comboBoxDataSituacao.getValue() != null && comboBoxEstado.getValue() != null
					&& !campoCnpj.getText().isEmpty() && !campoNome.getText().isEmpty()
					&& !campoCep.getText().isEmpty()) {

				try {
					new ClienteJuridicaDao().alterar(new Cliente_juridica(idCliente,
							new Endereco(idEndereco, campoCep.getText().replaceAll("[.]", ""), campoEndereco.getText(), campoNumero.getText(),
									campoComplemento.getText(), campoBairro.getText(), campoCidade.getText(),
									comboBoxEstado.getValue()),
							campoNome.getText(), campoCnpj.getText(), campoSituacao.getText(),
							comboBoxDataSituacao.getValue(), campoIe.getText(), campoEmail.getText(),
							textAreaInformacao.getText(), lista_telefones));
					
					carregarTabela();
					limparCampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
		//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Cliente alterado com sucesso", 4000); 
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
				snackBar.show("Cliente alterado com sucesso", 4000); 
			}

		}

	}

	private void visualizarDados() {
		// TODO Auto-generated method stub

		treeTableViewClienteJuridica.setOnMouseClicked(event -> {
			if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() != -1) {
				String[] ids = treeTableViewClienteJuridica.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idCliente = Integer.parseInt(ids[0]);

				try {

					new ClienteJuridicaDao().listar().forEach(cliente -> {

						if (cliente.getId_cliente_juridica() == idCliente) {
							campoBairro.setText(cliente.getEndereco().getBairro());
							campoCep.setText(cliente.getEndereco().getCep());
							campoCidade.setText(cliente.getEndereco().getCidade());
							campoComplemento.setText(cliente.getEndereco().getComplemento());
							campoCnpj.setText(cliente.getCnpj());
							campoEmail.setText(cliente.getEmail());
							campoEndereco.setText(cliente.getEndereco().getEndereco());
							campoSituacao.setText(cliente.getSituacao());
							campoNome.setText(cliente.getNome());
							campoNumero.setText(cliente.getEndereco().getNumero());
							campoTelefone.setText("");
							comboBoxDataSituacao.setValue(cliente.getData_situacao());
							textAreaInformacao.setText(cliente.getObservacao());
							comboBoxEstado.setValue(cliente.getEndereco().getEstado()); 

							// lista_telefones.clear();
							lista_telefones = cliente.getListaTelefone();
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
		JFXTreeTableColumn<ClienteJuridicoClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(55);
		JFXTreeTableColumn<ClienteJuridicoClass, String> colunaNome = new JFXTreeTableColumn<>("NOME");
		colunaNome.setPrefWidth(208);
		JFXTreeTableColumn<ClienteJuridicoClass, String> colunaCnpj = new JFXTreeTableColumn<>("CNPJ");
		colunaCnpj.setPrefWidth(165);
		JFXTreeTableColumn<ClienteJuridicoClass, String> colunaDataSituacao = new JFXTreeTableColumn<>("Situação");
		colunaDataSituacao.setPrefWidth(124);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteJuridicoClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().idClienteJuridica;
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteJuridicoClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().nome;
			else
				return colunaNome.getComputedValue(param);
		});
		colunaCnpj.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteJuridicoClass, String> param) -> {
			if (colunaCnpj.validateValue(param))
				return param.getValue().getValue().cnpj;
			else
				return colunaCnpj.getComputedValue(param);
		});
		colunaDataSituacao
				.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteJuridicoClass, String> param) -> {
					if (colunaDataSituacao.validateValue(param))
						return param.getValue().getValue().situacao;
					else
						return colunaDataSituacao.getComputedValue(param);
				});

		ObservableList<ClienteJuridicoClass> lista_clientes = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		try {
			new ClienteJuridicaDao().listar().forEach(cliente -> {
				lista_clientes.add(new ClienteJuridicoClass(cliente.getId_cliente_juridica(), cliente.getNome(),
						cliente.getCnpj(), cliente.getEndereco().getId_endereco(), cliente.getSituacao()));
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewClienteJuridica
				.setRoot(new RecursiveTreeItem<ClienteJuridicoClass>(lista_clientes, RecursiveTreeObject::getChildren));

		treeTableViewClienteJuridica.getColumns().setAll(colunaId, colunaNome, colunaCnpj, colunaDataSituacao);
		treeTableViewClienteJuridica.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewClienteJuridica.setPredicate(
					person -> person.getValue().idClienteJuridica.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().nome.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().cnpj.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().situacao.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		visualizarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class ClienteJuridicoClass extends RecursiveTreeObject<ClienteJuridicoClass> {

		StringProperty idClienteJuridica;
		StringProperty nome;
		StringProperty cnpj;
		StringProperty idEndereco;
		StringProperty situacao;

		public ClienteJuridicoClass(int idClienteJuridica, String nome, String cnpj, int idEndereco, String situacao) {
			super();
			this.idClienteJuridica = new SimpleStringProperty(String.valueOf(idClienteJuridica));
			this.nome = new SimpleStringProperty(nome);
			this.cnpj = new SimpleStringProperty(cnpj);
			this.idEndereco = new SimpleStringProperty(String.valueOf(idEndereco));
			this.situacao = new SimpleStringProperty(situacao);
		}

		// retorna o id do funcionario com isso posso alterar e remover qualquer
		// funcionario do banco de dados
		@Override
		public String toString() {
			return idClienteJuridica.getValue() + "/" + idEndereco.getValue();
		}
	}

	void atualizarTabela() {

		try {
			treeTableViewClienteJuridica = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewClienteJuridica);
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
					if(!cpf){
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("CNPJ Inválido", 6000);
						campoCnpj.setText("");
					}else{
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
								campoSituacao.setText(empresa.getSituacao());
								campoNome.setText(empresa.getNome());
								campoNumero.setText(empresa.getNumero());
								campoTelefone.setText("");
								final DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								final LocalDate dataFormatada = LocalDate.parse(empresa.getDataAbertura(), formataData);
								comboBoxDataSituacao.setValue(dataFormatada);
								textAreaInformacao.setText(empresa.getTAtividadePrincipal());
								try {

									telefone1 ="";
									for(String telefone : empresa.getListaTelefones()){

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

		if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() == -1) {
			if (!campoCelular.getText().isEmpty()) {
				lista_telefones.add(new Telefone(0, campoCelular.getText()));
				campoCelular.setText("");
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Número cadastrado com sucesso", 4000); 
			}
			if (!campoTelefone.getText().isEmpty()) {
				lista_telefones.add(new Telefone(0, campoTelefone.getText()));
				campoTelefone.setText("");
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Número cadastrado com sucesso", 4000); 
			}

		} else if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewClienteJuridica.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idCliente = Integer.parseInt(ids[0]);

			if (!campoCelular.getText().isEmpty()) {
				try {
					lista_telefones.add(new Telefone(idCliente, campoCelular.getText()));
					new ClienteJuridicaDao().adicionarTelefone(new Telefone(idCliente, campoCelular.getText()));
					campoCelular.setText("");
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Número cadastrado com sucesso", 4000); 
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
					lista_telefones.add(new Telefone(idCliente, campoTelefone.getText()));
					new ClienteJuridicaDao().adicionarTelefone(new Telefone(idCliente, campoTelefone.getText()));
					campoTelefone.setText("");
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Número cadastrado com sucesso", 4000); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Campos obrigatórios não informado", 4000); 
		}

		listViewTelefones.setItems(lista_telefones);

	}

	@FXML
	void btnRemoverTelefone(MouseEvent event) {
		try {
			if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() == -1 && listViewTelefones.getSelectionModel().getSelectedIndex() != -1) {
				for (int i = 0; i < lista_telefones.size(); ++i) {
					if (lista_telefones.get(i).getTelefone()
							.equals(listViewTelefones.getSelectionModel().getSelectedItem().toString())) {
						lista_telefones.remove(i);
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Número removido com sucesso", 4000); 
					}
				}

			} else if (treeTableViewClienteJuridica.getSelectionModel().getSelectedIndex() != -1 && listViewTelefones.getSelectionModel().getSelectedIndex() != -1) {

				String[] ids = treeTableViewClienteJuridica.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idCliente = Integer.parseInt(ids[0]);
				String telefone = listViewTelefones.getSelectionModel().getSelectedItem().getTelefone();

				for (int i = 0; i < lista_telefones.size(); ++i) {
					if (lista_telefones.get(i).getTelefone()
							.equals(listViewTelefones.getSelectionModel().getSelectedItem().toString())) {
						lista_telefones.remove(i);
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Número removido com sucesso", 4000); 
					}
				}
				try {
					new ClienteJuridicaDao().excluirTelefone(new Telefone(idCliente, telefone));
				} catch (SQLException e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}

			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Seleciona número na tabela", 4000); 
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
		campoSituacao.setText("");
		campoEmail.setText("");
		campoEndereco.setText("");
		campoNome.setText("");
		campoNumero.setText("");
		campoPesquisar.setText("");
		campoTelefone.setText("");
		comboBoxDataSituacao.setValue(null);
		comboBoxEstado.setValue(null);
		textAreaInformacao.setText("");
		campoCelular.setText("");
		campoCnpj.setText("");
	}
}
