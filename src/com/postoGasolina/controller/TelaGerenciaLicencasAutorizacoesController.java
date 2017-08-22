package com.postoGasolina.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.FuncionarioDao;
import com.postoGasolina.dao.LicencaAutorizacaoDao;
import com.postoGasolina.dao.OrgaoGovernamentalDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Funcionario;
import com.postoGasolina.model.Licença_autorizacao;
import com.postoGasolina.model.Orgao_governamental;

import javafx.application.HostServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class TelaGerenciaLicencasAutorizacoesController implements Initializable {

	@FXML
	private JFXTextField campoDescrição;

	@FXML
	private JFXTextField campoQuemRealizouOrgao;

	@FXML
	private JFXTextArea textAreaInformacao;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXDatePicker comboBoxDataInicio;

	@FXML
	private JFXDatePicker comboBoxDataExpiracao;

	@FXML
	private ComboBox<String> comboBoxStatus;

	@FXML
	private ComboBox<Orgao_governamental> comboBoxOrgao;

	@FXML
	private ImageView btnAdicionarOrgao;

	@FXML
	private JFXButton btnEnviarLicenca;

	@FXML
	private JFXButton btnVisualizarLicenca;

	@FXML
	private ComboBox<Funcionario> comboboxFuncionario;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	private File diretorioArquivo;
	@FXML
	private Label informacaoArquivo;

	@FXML
	private Label linkComoRenovar;

	private JFXTreeTableView<LicencaAutorizacaoClass> treeTableViewOrgao;
	private int idLA;
	
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

	}

	@FXML
	void btnAdicionarOrgao(MouseEvent event) {
		new Main().carregarTelaOrgaoUtilitaria();
	}

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewOrgao.getSelectionModel().getSelectedIndex() != -1) {
			String[] ids = treeTableViewOrgao.getSelectionModel().getSelectedItem().getValue().toString().split("/");
			idLA = Integer.parseInt(ids[0]);
			try {
				new LicencaAutorizacaoDao().remover(idLA);
				carregarTabela();
				limparCampos();
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Licença/Autorização removido com sucesso", 4000);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona Licença/Autorização na tabela", 4000);
		}
	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparCampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewOrgao.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (comboBoxDataInicio.getValue() != null && comboBoxDataExpiracao.getValue() != null
						&& !campoDescrição.getText().isEmpty() && comboBoxStatus.getValue() != null
						&& comboboxFuncionario.getValue() != null && comboBoxOrgao.getValue() != null) {

					try {

						new LicencaAutorizacaoDao().cadastrar(new Licença_autorizacao(0, comboBoxOrgao.getValue(),
								comboboxFuncionario.getValue(), campoDescrição.getText(), comboBoxDataInicio.getValue(),
								comboBoxDataExpiracao.getValue(), comboBoxStatus.getValue(),
								campoQuemRealizouOrgao.getText(), textAreaInformacao.getText(), diretorioArquivo));

						carregarTabela();
						limparCampos();

						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Licença/Autorização cadastrado com sucesso", 4000);
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

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewOrgao.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewOrgao.getSelectionModel().getSelectedItem().getValue().toString().split("/");
			idLA = Integer.parseInt(ids[0]);

			if (comboBoxDataInicio.getValue() != null && comboBoxDataExpiracao.getValue() != null
					&& !campoDescrição.getText().isEmpty() && comboBoxStatus.getValue() != null
					&& comboboxFuncionario.getValue() != null && comboBoxOrgao.getValue() != null) {

				try {
					new LicencaAutorizacaoDao().alterar(new Licença_autorizacao(idLA, comboBoxOrgao.getValue(),
							comboboxFuncionario.getValue(), campoDescrição.getText(), comboBoxDataInicio.getValue(),
							comboBoxDataExpiracao.getValue(), comboBoxStatus.getValue(),
							campoQuemRealizouOrgao.getText(), textAreaInformacao.getText(), diretorioArquivo));

					carregarTabela();
					carregarTabela();
					limparCampos();

					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Licença/Autorização alterado com sucesso", 4000);
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

	private void visualizarDados() {
		// TODO Auto-generated method stub

		treeTableViewOrgao.setOnMouseClicked(event -> {
			if (treeTableViewOrgao.getSelectionModel().getSelectedIndex() != -1) {
				String[] ids = treeTableViewOrgao.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idLA = Integer.parseInt(ids[0]);

				try {

					new LicencaAutorizacaoDao().listar().forEach(la -> {

						if (la.getId_licenca_autorizacao() == idLA) {
							comboBoxDataExpiracao.setValue(la.getData_expiracao());
							comboBoxDataInicio.setValue(la.getData_inicio());
							comboboxFuncionario.setValue(la.getFuncionario());
							comboBoxOrgao.setValue(la.getOrgao());
							comboBoxStatus.setValue(la.getStatus());
							campoDescrição.setText(la.getDescricao());
							campoQuemRealizouOrgao.setText(la.getDescricao_responsavel_renovacao());
							textAreaInformacao.setText(la.getInformacao_adicional());
							diretorioArquivo = la.getArquivo_pdf();
							informacaoArquivo.setText(diretorioArquivo.getName());
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
		JFXTreeTableColumn<LicencaAutorizacaoClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(55);
		JFXTreeTableColumn<LicencaAutorizacaoClass, String> colunaDescricao = new JFXTreeTableColumn<>("Descrição");
		colunaDescricao.setPrefWidth(180);
		JFXTreeTableColumn<LicencaAutorizacaoClass, String> colunaOrgao = new JFXTreeTableColumn<>("Orgão");
		colunaOrgao.setPrefWidth(120);
		JFXTreeTableColumn<LicencaAutorizacaoClass, String> colunaDataExpiracao = new JFXTreeTableColumn<>(
				"Data Expiração");
		colunaDataExpiracao.setPrefWidth(110);
		JFXTreeTableColumn<LicencaAutorizacaoClass, String> colunaStatus = new JFXTreeTableColumn<>("Status");
		colunaStatus.setPrefWidth(80);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<LicencaAutorizacaoClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id;
			else
				return colunaId.getComputedValue(param);
		});
		colunaDescricao
				.setCellValueFactory((TreeTableColumn.CellDataFeatures<LicencaAutorizacaoClass, String> param) -> {
					if (colunaDescricao.validateValue(param))
						return param.getValue().getValue().descricao;
					else
						return colunaDescricao.getComputedValue(param);
				});
		colunaOrgao.setCellValueFactory((TreeTableColumn.CellDataFeatures<LicencaAutorizacaoClass, String> param) -> {
			if (colunaOrgao.validateValue(param))
				return param.getValue().getValue().orgao;
			else
				return colunaOrgao.getComputedValue(param);
		});
		colunaStatus.setCellValueFactory((TreeTableColumn.CellDataFeatures<LicencaAutorizacaoClass, String> param) -> {
			if (colunaStatus.validateValue(param))
				return param.getValue().getValue().status;
			else
				return colunaStatus.getComputedValue(param);
		});

		colunaDataExpiracao
				.setCellValueFactory((TreeTableColumn.CellDataFeatures<LicencaAutorizacaoClass, String> param) -> {
					if (colunaDataExpiracao.validateValue(param))
						return param.getValue().getValue().dataExpiracao;
					else
						return colunaDataExpiracao.getComputedValue(param);
				});

		ObservableList<LicencaAutorizacaoClass> lista_la = FXCollections.observableArrayList();
		// carregando registros com os campos da coluna da classe anônima
		try {
			ObservableList<Licença_autorizacao> lista_laOrdenada = new LicencaAutorizacaoDao().listar();

			lista_laOrdenada.sort(new Comparator<Licença_autorizacao>() {

				@Override
				public int compare(Licença_autorizacao la1, Licença_autorizacao la2) {
					if (LocalDate.now().isAfter(la1.getData_expiracao())) {
						try {
							new LicencaAutorizacaoDao().atualizaVStatus(la1.getData_expiracao());
							la1.setStatus("Vencido");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return -1;
					}
					if (LocalDate.now().isBefore(la1.getData_expiracao())) {
						try {
							new LicencaAutorizacaoDao().atualizaAStatus(la1.getData_expiracao());
							la1.setStatus("Ativo");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return 1;
					}

					return 0;
				}
			});

			lista_laOrdenada.forEach(la -> {
				lista_la.add(new LicencaAutorizacaoClass(la.getId_licenca_autorizacao(), la.getDescricao(),
						la.getOrgao().getNome(), la.getData_expiracao(), la.getStatus()));
			});

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewOrgao
				.setRoot(new RecursiveTreeItem<LicencaAutorizacaoClass>(lista_la, RecursiveTreeObject::getChildren));

		treeTableViewOrgao.getColumns().setAll(colunaId, colunaDescricao, colunaOrgao, colunaDataExpiracao,
				colunaStatus);
		treeTableViewOrgao.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewOrgao
					.setPredicate(person -> person.getValue().id.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().descricao.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().orgao.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().dataExpiracao.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().status.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		visualizarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class LicencaAutorizacaoClass extends RecursiveTreeObject<LicencaAutorizacaoClass> {

		StringProperty id;
		StringProperty descricao;
		StringProperty orgao;
		StringProperty dataExpiracao;
		StringProperty status;

		public LicencaAutorizacaoClass(int id, String descricao, String orgao, LocalDate dataExpiracao, String status) {
			this.id = new SimpleStringProperty(String.valueOf(id));
			this.descricao = new SimpleStringProperty(descricao);
			this.orgao = new SimpleStringProperty(orgao);
			this.dataExpiracao = new SimpleStringProperty(
					dataExpiracao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			this.status = new SimpleStringProperty(status);
		}

		// retorna o id do funcionario com isso posso alterar e remover qualquer
		// funcionario do banco de dados
		@Override
		public String toString() {
			return id.getValue();
		}
	}

	void atualizarTabela() {

		try {
			treeTableViewOrgao = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewOrgao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void btnEnviarArquivoOnAction(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			// fc.setInitialDirectory(new File("filesPdf"));
			fc.getExtensionFilters().add(new ExtensionFilter("Arquivos PDF", "*.pdf"));
			Stage stage = (Stage) borderPaneTabela.getScene().getWindow();
			File arquivoSelecionado = fc.showOpenDialog(stage);
			if (arquivoSelecionado != null) {

				Path src = FileSystems.getDefault().getPath(arquivoSelecionado.getPath());
				Path novoDiretorio = FileSystems.getDefault().getPath("filesPdf/" + arquivoSelecionado.getName());

				try {
					Files.move(src, novoDiretorio, StandardCopyOption.ATOMIC_MOVE);
					diretorioArquivo = novoDiretorio.toFile();

					informacaoArquivo.setText(diretorioArquivo.getName());

					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Arquivo enviado com sucesso", 4000);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Esse arquivo é inválido", 4000);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	void btnVisualizarArquivoOnAction(ActionEvent event) {
		if (diretorioArquivo != null) {
			Stage stage = (Stage) borderPaneTabela.getScene().getWindow();
			HostServices hostServices = new Main().getHostServices();
			hostServices.showDocument(diretorioArquivo.getPath());
			
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Arquivo abriu com sucesso", 4000); 
			
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Não foi possivel abrir arquivo", 4000); 
		}
	}

	void preencherComboBox() {

		comboBoxStatus.getItems().addAll("Ativo", "Vencido");

		comboBoxOrgao.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxOrgao.isFocused()) {
				try {
					comboBoxOrgao.setItems(new OrgaoGovernamentalDao().listar());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		comboboxFuncionario.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboboxFuncionario.isFocused()) {
				try {
					comboboxFuncionario.setItems(new FuncionarioDao().listar());
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

	void limparCampos() {
		comboBoxDataExpiracao.setValue(null);
		comboBoxDataInicio.setValue(null);
		comboboxFuncionario.setValue(null);
		comboBoxOrgao.setValue(null);
		comboBoxStatus.setValue(null);
		campoDescrição.setText("");
		campoQuemRealizouOrgao.setText("");
		textAreaInformacao.setText("");
		diretorioArquivo = null;
		informacaoArquivo.setText("");

	}

	@FXML
	void linkComoRenovar(MouseEvent event) {
		try {
			Desktop.getDesktop().open(new File("filesPdf/ComoRenovar.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
