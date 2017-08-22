package com.postoGasolina.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import com.jfoenix.validation.RequiredFieldValidator;
import com.postoGasolina.dao.CargoDao;
import com.postoGasolina.dao.FuncionarioDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Cargo;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Funcionario;
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
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class TelaGerenciarFuncionariosController implements Initializable {

	@FXML
	private JFXTextField campoNome;

	@FXML
	private ComboBox<String> comboBoxSexo;

	@FXML
	private ComboBox<String> comboBoxEstatadoCivil;

	@FXML
	private JFXDatePicker comboBoxDataNascimento;

	@FXML
	private JFXTextField campoCpf;

	@FXML
	private JFXTextField campoRg;

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
	private JFXDatePicker comboBoxDataAdmissao;

	@FXML
	private JFXDatePicker comboBoxDataDemissao;

	@FXML
	private ComboBox<String> comboBoxStatus;

	@FXML
	private ComboBox<Cargo> comboBoxCargo;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	@FXML
	private ImageView btnCargo;

	@FXML
	private JFXTreeTableView<FuncionarioClass> treeTableViewFncionario;

	private byte result;
	private FuncionarioDao sqlFuncionario;

	private int idFuncionario;
	
	private CPF cpf;
	
	private JFXSnackbar snackBar;

	// telefone
	private ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		formataCampos();
		preencherComboBox();
		carregarTabela();
		validacaoCampos();
	}

	private void visualizarDados() {
		// TODO Auto-generated method stub

		treeTableViewFncionario.setOnMouseClicked(event -> {
			if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() != -1) {
				String[] ids = treeTableViewFncionario.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idFuncionario = Integer.parseInt(ids[0]);

				try {

					sqlFuncionario.listar().forEach(funcionario -> {

						if (funcionario.getId_funcionario() == idFuncionario) {
							campoBairro.setText(funcionario.getEndereco().getBairro());
							campoCep.setText(funcionario.getEndereco().getCep());
							campoCidade.setText(funcionario.getEndereco().getCidade());
							campoComplemento.setText(funcionario.getEndereco().getComplemento());
							campoCpf.setText(funcionario.getPessoa().getCpf());
							campoEmail.setText(funcionario.getEmail());
							campoEndereco.setText(funcionario.getEndereco().getEndereco());
							campoNome.setText(funcionario.getPessoa().getNome());
							campoNumero.setText(funcionario.getEndereco().getNumero());
							campoRg.setText(funcionario.getPessoa().getRg());
							campoTelefone.setText("");
							comboBoxDataNascimento.setValue(funcionario.getPessoa().getData_nascimento());
							comboBoxEstado.setValue(funcionario.getEndereco().getEstado());
							comboBoxEstatadoCivil.setValue(funcionario.getPessoa().getEstado_civil());
							comboBoxSexo.setValue(funcionario.getPessoa().getSexo() == 'M' ? "Masculino" : "Feminino");
							textAreaInformacao.setText(funcionario.getObservacao());
							comboBoxStatus.setValue(funcionario.getStatus());
							comboBoxCargo.getSelectionModel().select(funcionario.getCargo());
							comboBoxDataAdmissao.setValue(funcionario.getData_admissao());
							comboBoxDataDemissao.setValue(funcionario.getData_demissao());

							// lista_telefones.clear();
							lista_telefones = funcionario.getLista_telefones();
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
		if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() != -1) {
			String[] ids = treeTableViewFncionario.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idFuncionario = Integer.parseInt(ids[0]);
			try {
				sqlFuncionario.remover(idFuncionario);
				carregarTabela();
				limparCampos();

				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Funcionário removido com sucesso", 4000);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Funcionário sendo utilizado", 4000);
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Selecione um funcionário na tabela", 4000);
		}

	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparCampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (comboBoxDataNascimento.getValue() != null && comboBoxEstado.getValue() != null
						&& comboBoxEstatadoCivil.getValue() != null && comboBoxSexo.getValue() != null
						&& !campoNome.getText().isEmpty() && !campoCep.getText().isEmpty()
						&& !campoCpf.getText().isEmpty() && comboBoxDataAdmissao.getValue() != null
						&& comboBoxStatus.getValue() != null && comboBoxCargo.getValue() != null) {

					sqlFuncionario.cadastrar(new Funcionario(0,
							new Pessoa(0, campoNome.getText(), comboBoxDataNascimento.getValue(),
									comboBoxSexo.getValue().charAt(0), comboBoxEstatadoCivil.getValue(),
									campoRg.getText(), campoCpf.getText()),

							new Cargo(comboBoxCargo.getValue().getId_cargo(), comboBoxCargo.getValue().getNome()),
							new Endereco(0, campoCep.getText(), campoEndereco.getText(), campoNumero.getText(),
									campoComplemento.getText(), campoBairro.getText(), campoCidade.getText(),
									comboBoxEstado.getValue()),

							comboBoxStatus.getValue(), campoEmail.getText(), comboBoxDataAdmissao.getValue(),
							comboBoxDataDemissao.getValue(), textAreaInformacao.getText(), lista_telefones));

					carregarTabela();
					limparCampos();

					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Funcionário cadastrado com sucesso", 4000);

				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Campos obrigatórios não informado", 4000);
				}

			} catch (Exception e) {
				// TODO: handle exception
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Não foi possivel efetuar o cadastro, tente novamente", 4000);
				e.printStackTrace();
			}

		} else if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewFncionario.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idFuncionario = Integer.parseInt(ids[0]);
			int idPessoa = Integer.parseInt(ids[1]);
			int idEndereco = Integer.parseInt(ids[2]);

			if (comboBoxDataNascimento.getValue() != null && comboBoxEstado.getValue() != null
					&& comboBoxEstatadoCivil.getValue() != null && comboBoxSexo.getValue() != null
					&& !campoNome.getText().isEmpty() && !campoCep.getText().isEmpty()
					&& !campoCpf.getText().isEmpty()) {

				try {
					sqlFuncionario.alterar(new Funcionario(idFuncionario,
							new Pessoa(idPessoa, campoNome.getText(), comboBoxDataNascimento.getValue(),
									comboBoxSexo.getValue().charAt(0), comboBoxEstatadoCivil.getValue(),
									campoRg.getText(), campoCpf.getText()),
							new Cargo(comboBoxCargo.getValue().getId_cargo(), comboBoxCargo.getValue().getNome()),

							new Endereco(idEndereco, campoCep.getText(), campoEndereco.getText(), campoNumero.getText(),
									campoComplemento.getText(), campoBairro.getText(), campoCidade.getText(),
									comboBoxEstado.getValue()),

							comboBoxStatus.getValue(), campoEmail.getText(), comboBoxDataAdmissao.getValue(),
							comboBoxDataDemissao.getValue(), textAreaInformacao.getText(), lista_telefones));

					carregarTabela();
					limparCampos();

					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Funcionário alterado com sucesso", 4000);

				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Não foi possivel efetuar o cadastro, tente novamente", 4000);
					e.printStackTrace();
				}

			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Campos Obrigatórios não informado", 4000);
			}

		}
	}

	void formataCampos() {

		// comboBoxDataNascimento.setBackground();
		comboBoxDataNascimento.setDefaultColor(Color.valueOf("#fcf0f0"));
	}

	public void preencherComboBox() {
		comboBoxSexo.getItems().addAll("Masculino", "Feminino");
		comboBoxEstado.getItems().addAll("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT",
				"PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO");
		comboBoxEstatadoCivil.getItems().addAll("Solteiro", "Casado", "Divorciado", "Separado", "Viúvo");

		try {
			comboBoxCargo.setItems(new CargoDao().listar());

			// IMPORTANTE ATUALIZA COMBOBOX
			comboBoxCargo.focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (comboBoxCargo.isFocused()) {
					try {
						comboBoxCargo.setItems(new CargoDao().listar());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		comboBoxStatus.getItems().addAll("Ativo", "Inativo");

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
		comboBoxDataAdmissao.setValue(null);
		comboBoxDataDemissao.setValue(null);
		campoNome.setText("");
		campoNumero.setText("");
		campoPesquisar.setText("");
		campoRg.setText("");
		campoTelefone.setText("");
		comboBoxDataNascimento.setValue(null);
		comboBoxEstado.setValue(null);
		comboBoxEstatadoCivil.setValue(null);
		comboBoxSexo.setValue(null);
		textAreaInformacao.setText("");
		comboBoxStatus.setValue(null);
		campoCelular.setText("");
		comboBoxCargo.setValue(null);

	}

	void carregarTabela() {

		atualizarTabela();

		sqlFuncionario = new FuncionarioDao();
		// Criando as colunas da tabela
		JFXTreeTableColumn<FuncionarioClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(90);
		JFXTreeTableColumn<FuncionarioClass, String> colunaNome = new JFXTreeTableColumn<>("NOME");
		colunaNome.setPrefWidth(230);
		JFXTreeTableColumn<FuncionarioClass, String> colunaCpf = new JFXTreeTableColumn<>("CPF");
		colunaCpf.setPrefWidth(140);
		JFXTreeTableColumn<FuncionarioClass, String> colunaStatus = new JFXTreeTableColumn<>("STATUS");
		colunaStatus.setPrefWidth(90);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<FuncionarioClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id;
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<FuncionarioClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().nome;
			else
				return colunaNome.getComputedValue(param);
		});
		colunaCpf.setCellValueFactory((TreeTableColumn.CellDataFeatures<FuncionarioClass, String> param) -> {
			if (colunaCpf.validateValue(param))
				return param.getValue().getValue().cpf;
			else
				return colunaCpf.getComputedValue(param);
		});
		colunaStatus.setCellValueFactory((TreeTableColumn.CellDataFeatures<FuncionarioClass, String> param) -> {
			if (colunaStatus.validateValue(param))
				return param.getValue().getValue().status;
			else
				return colunaCpf.getComputedValue(param);
		});

		ObservableList<FuncionarioClass> lista_funcionarios = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		try {
			sqlFuncionario.listar().forEach(funcionario -> {
				lista_funcionarios.add(new FuncionarioClass(String.valueOf(funcionario.getId_funcionario()),
						funcionario.getPessoa().getNome(), funcionario.getPessoa().getCpf(),
						String.valueOf(funcionario.getPessoa().getId_pessoa()),
						String.valueOf(funcionario.getEndereco().getId_endereco()), funcionario.getStatus()));
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewFncionario
				.setRoot(new RecursiveTreeItem<FuncionarioClass>(lista_funcionarios, RecursiveTreeObject::getChildren));

		treeTableViewFncionario.getColumns().setAll(colunaId, colunaNome, colunaCpf, colunaStatus);
		treeTableViewFncionario.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewFncionario
					.setPredicate(person -> person.getValue().id.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().nome.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().cpf.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().status.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		visualizarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class FuncionarioClass extends RecursiveTreeObject<FuncionarioClass> {

		StringProperty id;
		StringProperty nome;
		StringProperty cpf;
		StringProperty idPessoa;
		StringProperty idEndereco;
		StringProperty status;
		// StringProperty telefones[];

		public FuncionarioClass(String idClienteFisica, String nome, String cpf, String idPessoa, String idEndereco,
				String status) {
			super();
			this.id = new SimpleStringProperty(idClienteFisica);
			this.nome = new SimpleStringProperty(nome);
			this.cpf = new SimpleStringProperty(cpf);
			this.idEndereco = new SimpleStringProperty(idEndereco);
			this.idPessoa = new SimpleStringProperty(idPessoa);
			this.status = new SimpleStringProperty(status);
			// this.telefones = telefones;
		}

		// retorna o id do funcionario com isso posso alterar e remover qualquer
		// funcionario do banco de dados
		@Override
		public String toString() {
			return id.getValue() + "/" + idPessoa.getValue() + "/" + idEndereco.getValue();
		}
	}

	void atualizarTabela() {

		try {
			treeTableViewFncionario = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewFncionario);
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
				e.printStackTrace();

			}

		});

		btnCargo.setOnMouseClicked(event -> {
			Main m = new Main();
			m.carregarTelaCargo();
		});

	}

	@FXML
	void btnAdicionarTelefone(MouseEvent event) {

		if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() == -1) {
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
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Número adicionado com sucesso", 4000);
			}

		} else if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewFncionario.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idFuncionario = Integer.parseInt(ids[0]);

			if (!campoCelular.getText().isEmpty()) {
				try {
					lista_telefones.add(new Telefone(idFuncionario, campoCelular.getText()));
					sqlFuncionario.adicionarTelefone(new Telefone(idFuncionario, campoCelular.getText()));
					campoCelular.setText("");
					snackBar = new JFXSnackbar(borderPaneTabela);
		//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
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
					lista_telefones.add(new Telefone(idFuncionario, campoTelefone.getText()));
					sqlFuncionario.adicionarTelefone(new Telefone(idFuncionario, campoTelefone.getText()));
					campoTelefone.setText("");

					snackBar = new JFXSnackbar(borderPaneTabela);
					String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Número adicionado com sucesso", 4000);
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
			if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() == -1
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

			} else if (treeTableViewFncionario.getSelectionModel().getSelectedIndex() != -1
					&& listViewTelefones.getSelectionModel().getSelectedIndex() != -1) {

				String[] ids = treeTableViewFncionario.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idFuncionario = Integer.parseInt(ids[0]);
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
					sqlFuncionario.excluirTelefone(new Telefone(idFuncionario, telefone));
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

}
