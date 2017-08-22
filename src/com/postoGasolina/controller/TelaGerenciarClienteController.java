package com.postoGasolina.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import com.jfoenix.validation.RequiredFieldValidator;
import com.postoGasolina.dao.ClienteFisicaDao;
import com.postoGasolina.model.Cliente_fisica;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Pessoa;
import com.postoGasolina.model.Telefone;
import com.postoGasolina.model.WebServiceCep;
import com.postoGasolina.util.CPF;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class TelaGerenciarClienteController implements Initializable {

	@FXML
	private JFXTextField campoNome;

	@FXML
	private JFXDatePicker comboBoxDataNascimento;

	@FXML
	private JFXTextField campoCpf;

	@FXML
	private JFXTextField campoRg;

	@FXML
	private JFXTextField campoCep;

	@FXML
	private JFXTextField campoPai;

	@FXML
	private JFXTextField campoCidade;

	@FXML
	private JFXTextArea textAreaInformacao;

	@FXML
	private JFXTextField campoEmail;

	@FXML
	private JFXTextField campoMae;

	@FXML
	private JFXTextField campoEndereco;

	@FXML
	private JFXTextField campoNumero;

	@FXML
	private JFXTextField campoComplemento;

	@FXML
	private JFXTextField campoBairro;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private ImageView btnAdicionarTelefone;

	@FXML
	private ImageView btnRemoverTelefone;

	@FXML
	private JFXTextField campoTelefone;

	@FXML
	private JFXTextField campoCelular;

	@FXML
	private ComboBox<String> comboBoxSexo;

	@FXML
	private ComboBox<String> comboBoxEstatadoCivil;

	@FXML
	private ComboBox<String> comboBoxEstado;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXListView<Telefone> listViewTelefones;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXTreeTableView<ClienteFisicaClass> treeTableViewClienteFisica;

	@FXML
	private JFXTextField campoPesquisar;

	@FXML
	private BorderPane borderPaneTabela;

	private byte result;
	private ClienteFisicaDao sqlClienteFisica;

	private int idCliente;
	
	@FXML
    private JFXToggleButton btnPessoaJuridica;

	// telefone
	private ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
	
	private JFXSnackbar snackBar;
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		formataCampos();
		preencherComboBox();
		
		validacaoCampos();
		try{
			carregarTabela();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}

	private void visualizarDados() {
		// TODO Auto-generated method stub

		treeTableViewClienteFisica.setOnMouseClicked(event -> {
			if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() != -1) {
				String[] ids = treeTableViewClienteFisica.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idCliente = Integer.parseInt(ids[0]);

				try {

					sqlClienteFisica.listar().forEach(cliente -> {

						if (cliente.getId_cliente_fisica() == idCliente) {
							campoBairro.setText(cliente.getEndereco().getBairro());
							campoCep.setText(cliente.getEndereco().getCep());
							campoCidade.setText(cliente.getEndereco().getCidade());
							campoComplemento.setText(cliente.getEndereco().getComplemento());
							campoCpf.setText(cliente.getPessoa().getCpf());
							campoEmail.setText(cliente.getEmail());
							campoEndereco.setText(cliente.getEndereco().getEndereco());
							campoMae.setText(cliente.getMae());
							campoNome.setText(cliente.getPessoa().getNome());
							campoNumero.setText(cliente.getEndereco().getNumero());
							campoPai.setText(cliente.getPai());
							campoRg.setText(cliente.getPessoa().getRg());
							campoTelefone.setText("");
							comboBoxDataNascimento.setValue(cliente.getPessoa().getData_nascimento());
							comboBoxEstado.setValue(cliente.getEndereco().getEstado());
							comboBoxEstatadoCivil.setValue(cliente.getPessoa().getEstado_civil());
							comboBoxSexo.setValue(cliente.getPessoa().getSexo() == 'M' ? "Masculino" : "Feminino");
							textAreaInformacao.setText(cliente.getInformacao());

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

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() != -1) {
			ClienteFisicaDao a = new ClienteFisicaDao();
			String[] ids = treeTableViewClienteFisica.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idCliente = Integer.parseInt(ids[0]);
			try {
				a.remover(idCliente);
				carregarTabela();
				limparCampos();
				
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
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
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona Cliente na tabela", 4000); 
		}

	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparCampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (comboBoxDataNascimento.getValue() != null && comboBoxEstado.getValue() != null
						&& comboBoxEstatadoCivil.getValue() != null && comboBoxSexo.getValue() != null
						&& !campoNome.getText().isEmpty() && !campoCep.getText().isEmpty()
						&& !campoCpf.getText().isEmpty()) {

					Cliente_fisica.cadastrar(new Cliente_fisica(0,
							new Pessoa(0, campoNome.getText(), comboBoxDataNascimento.getValue(),
									comboBoxSexo.getValue().charAt(0), comboBoxEstatadoCivil.getValue(),
									campoRg.getText(), campoCpf.getText()),
							new Endereco(0, campoCep.getText(), campoEndereco.getText(), campoNumero.getText(),
									campoComplemento.getText(), campoBairro.getText(), campoCidade.getText(),
									comboBoxEstado.getValue()),
							campoPai.getText(), campoMae.getText(), campoEmail.getText(), textAreaInformacao.getText(),
							lista_telefones));

					carregarTabela();
					limparCampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Cliente cadastrado com sucesso", 4000); 

				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
		//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000); 
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewClienteFisica.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idCliente = Integer.parseInt(ids[0]);
			int idPessoa = Integer.parseInt(ids[1]);
			int idEndereco = Integer.parseInt(ids[2]);

			if (comboBoxDataNascimento.getValue() != null && comboBoxEstado.getValue() != null
					&& comboBoxEstatadoCivil.getValue() != null && comboBoxSexo.getValue() != null
					&& !campoNome.getText().isEmpty() && !campoCep.getText().isEmpty()
					&& !campoCpf.getText().isEmpty()) {

				Cliente_fisica.alterar(new Cliente_fisica(idCliente,
						new Pessoa(idPessoa, campoNome.getText(), comboBoxDataNascimento.getValue(),
								comboBoxSexo.getValue().charAt(0), comboBoxEstatadoCivil.getValue(), campoRg.getText(),
								campoCpf.getText()),
						new Endereco(idEndereco, campoCep.getText(), campoEndereco.getText(), campoNumero.getText(),
								campoComplemento.getText(), campoBairro.getText(), campoCidade.getText(),
								comboBoxEstado.getValue()),
						campoPai.getText(), campoMae.getText(), campoEmail.getText(), textAreaInformacao.getText(),
						lista_telefones));

				carregarTabela();
				limparCampos();
				
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Cliente alterado com sucesso", 4000); 

			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Campos obrigatórios não informado", 4000); 
			}

		}
	}

	void formataCampos() {

		// comboBoxDataNascimento.setBackground();
		comboBoxDataNascimento.setDefaultColor(Color.valueOf("#fcf0f0"));
	}

	void preencherComboBox() {
		comboBoxSexo.getItems().addAll("Masculino", "Feminino");
		comboBoxEstado.getItems().addAll("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT",
				"PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO");
		comboBoxEstatadoCivil.getItems().addAll("Solteiro", "Casado", "Divorciado", "Separado", "Viúvo");

	}

	void limparCampos() {
		lista_telefones.clear();
		campoBairro.setText("");
		campoCep.setText("");
		campoCidade.setText("");
		campoComplemento.setText("");
		campoCpf.setText("");
		campoEmail.setText("");
		campoEndereco.setText("");
		campoMae.setText("");
		campoNome.setText("");
		campoNumero.setText("");
		campoPai.setText("");
		campoPesquisar.setText("");
		campoRg.setText("");
		campoTelefone.setText("");
		comboBoxDataNascimento.setValue(null);
		comboBoxEstado.setValue(null);
		comboBoxEstatadoCivil.setValue(null);
		comboBoxSexo.setValue(null);
		textAreaInformacao.setText("");
		campoCelular.setText("");
	}

	void carregarTabela() {

		atualizarTabela();

		sqlClienteFisica = new ClienteFisicaDao();
		// Criando as colunas da tabela
		JFXTreeTableColumn<ClienteFisicaClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(55);
		JFXTreeTableColumn<ClienteFisicaClass, String> colunaNome = new JFXTreeTableColumn<>("NOME");
		colunaNome.setPrefWidth(208);
		JFXTreeTableColumn<ClienteFisicaClass, String> colunaCpf = new JFXTreeTableColumn<>("CPF");
		colunaCpf.setPrefWidth(130);
		JFXTreeTableColumn<ClienteFisicaClass, String> colunaDataNas = new JFXTreeTableColumn<>("DATA DE NASCIMENTO");
		colunaDataNas.setPrefWidth(165);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteFisicaClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().idClienteFisica;
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteFisicaClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().nome;
			else
				return colunaNome.getComputedValue(param);
		});
		colunaCpf.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteFisicaClass, String> param) -> {
			if (colunaCpf.validateValue(param))
				return param.getValue().getValue().cpf;
			else
				return colunaCpf.getComputedValue(param);
		});
		colunaDataNas.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteFisicaClass, String> param) -> {
			if (colunaDataNas.validateValue(param))
				return param.getValue().getValue().DataNascimento;
			else
				return colunaDataNas.getComputedValue(param);
		});

		ObservableList<ClienteFisicaClass> lista_clientes = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		try {
			sqlClienteFisica.listar().forEach(cliente -> {
				lista_clientes.add(new ClienteFisicaClass(String.valueOf(cliente.getId_cliente_fisica()),
						cliente.getPessoa().getNome(), cliente.getPessoa().getCpf(),
						String.valueOf(cliente.getPessoa().getId_pessoa()),
						String.valueOf(cliente.getEndereco().getId_endereco()),
						cliente.getPessoa().getData_nascimento() != null ? 
								cliente.getPessoa().getData_nascimento()
								.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Cadastro Incompleto"));
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewClienteFisica
				.setRoot(new RecursiveTreeItem<ClienteFisicaClass>(lista_clientes, RecursiveTreeObject::getChildren));

		treeTableViewClienteFisica.getColumns().setAll(colunaId, colunaNome, colunaCpf, colunaDataNas);
		treeTableViewClienteFisica.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewClienteFisica.setPredicate(
					person -> person.getValue().idClienteFisica.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().nome.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().cpf.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().DataNascimento.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		visualizarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class ClienteFisicaClass extends RecursiveTreeObject<ClienteFisicaClass> {

		StringProperty idClienteFisica;
		StringProperty nome;
		StringProperty cpf;
		StringProperty idPessoa;
		StringProperty idEndereco;
		StringProperty DataNascimento;

		public ClienteFisicaClass(String idClienteFisica, String nome, String cpf, String idPessoa, String idEndereco,
				String dataNascimento) {
			super();
			this.idClienteFisica = new SimpleStringProperty(idClienteFisica);
			this.nome = new SimpleStringProperty(nome);
			this.cpf = new SimpleStringProperty(cpf);
			this.idEndereco = new SimpleStringProperty(idEndereco);
			this.idPessoa = new SimpleStringProperty(idPessoa);
			this.DataNascimento = new SimpleStringProperty(dataNascimento);
		}

		// retorna o id do funcionario com isso posso alterar e remover qualquer
		// funcionario do banco de dados
		@Override
		public String toString() {
			return idClienteFisica.getValue() + "/" + idPessoa.getValue() + "/" + idEndereco.getValue();
		}
	}

	void atualizarTabela() {

		try {
			treeTableViewClienteFisica = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewClienteFisica);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void validacaoCampos() {
		RequiredFieldValidator validacao = new RequiredFieldValidator();
		campoCep.getValidators().add(validacao);
		campoCpf.getValidators().add(validacao);
		campoNome.getValidators().add(validacao);
		// comboBoxDataNascimento.getre
		// comboBoxEstado.getValidators().add(validacao);
		// comboBoxEstatadoCivil.getValidators().add(validacao);
		// comboBoxSexo.setValue(null);
		validacao.setMessage("Campo obrigatório");

		campoRg.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("##.###.###-#");
			ttf.setTf(campoRg);
			ttf.formatter();
		});

		campoCpf.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("###.###.###-##");
			ttf.setTf(campoCpf);
			ttf.formatter();
			try {
				if (campoCpf.getText().length() == 14 && campoCpf.getText().charAt(13) != ' ') {
					boolean cpf = new CPF(campoCpf.getText()).isCPF();
					if(!cpf){
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("CPF Inválido", 6000);
						campoCpf.setText(""); 
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

		if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() == -1) {
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

		} else if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewClienteFisica.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idCliente = Integer.parseInt(ids[0]);

			if (!campoCelular.getText().isEmpty()) {
				try {
					lista_telefones.add(new Telefone(idCliente, campoCelular.getText()));
					sqlClienteFisica.adicionarTelefone(new Telefone(idCliente, campoCelular.getText()));
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
					sqlClienteFisica.adicionarTelefone(new Telefone(idCliente, campoTelefone.getText()));
					campoTelefone.setText("");
					snackBar = new JFXSnackbar(borderPaneTabela);
		//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
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
			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Campos obrigatórios não informado", 4000); 
		}

		listViewTelefones.setItems(lista_telefones);

	}

	@FXML
	void btnRemoverTelefone(MouseEvent event) {
		try {
			if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() == -1 && listViewTelefones.getSelectionModel().getSelectedIndex() != -1) {
				for (int i = 0; i < lista_telefones.size(); ++i) {
					if (lista_telefones.get(i).getTelefone()
							.equals(listViewTelefones.getSelectionModel().getSelectedItem().toString())) {
						lista_telefones.remove(i);
						snackBar = new JFXSnackbar(borderPaneTabela);
						String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Número removido com sucesso", 4000); 
					}
				}

			} else if (treeTableViewClienteFisica.getSelectionModel().getSelectedIndex() != -1 && listViewTelefones.getSelectionModel().getSelectedIndex() != -1) {

				String[] ids = treeTableViewClienteFisica.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idCliente = Integer.parseInt(ids[0]);
				String telefone = listViewTelefones.getSelectionModel().getSelectedItem().getTelefone();

				for (int i = 0; i < lista_telefones.size(); ++i) {
					if (lista_telefones.get(i).getTelefone()
							.equals(listViewTelefones.getSelectionModel().getSelectedItem().toString())) {
						lista_telefones.remove(i);
						
						snackBar = new JFXSnackbar(borderPaneTabela);
				//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Número removido com sucesso", 4000); 
					}
				}
				try {
					sqlClienteFisica.excluirTelefone(new Telefone(idCliente, telefone));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Seleciona número na tabela", 4000); 
			}

			listViewTelefones.setItems(lista_telefones);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public final static TextField t = new TextField();
	@FXML
	void OnActionBtnPessoaJuridica(ActionEvent event) {
		if(btnPessoaJuridica.isSelected()){
			t.setText("1");
		}else{
			t.setText("0");
		}
	}

}
