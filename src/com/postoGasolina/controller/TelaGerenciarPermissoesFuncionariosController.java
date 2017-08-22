package com.postoGasolina.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.FuncionarioDao;
import com.postoGasolina.dao.PermissoesDao;
import com.postoGasolina.model.Funcionario;
import com.postoGasolina.model.Login;

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
import javafx.scene.layout.BorderPane;

public class TelaGerenciarPermissoesFuncionariosController implements Initializable{

	@FXML
    private ComboBox<String> comboBoxNivelAcesso;

    @FXML
    private ComboBox<Funcionario> comboBoxFuncionario;

    @FXML
    private JFXTextField campoEmail;

    @FXML
    private JFXTextArea textAreaInformacao;

    @FXML
    private JFXButton btnNovo;

    @FXML
    private JFXButton btnExcluir;

    @FXML
    private JFXButton btnSalvar;

    @FXML
    private JFXPasswordField campoSenha;

    @FXML
    private  JFXToggleButton tbGerenciarClientes; 

    @FXML
    private  JFXToggleButton tbGerenciarFuncionarios;

    @FXML
    private  JFXToggleButton tbGerenciarCaixa;

    @FXML
    private  JFXToggleButton tbGerenciarFornecedores;

    @FXML
    private  JFXToggleButton tbGerenciarFidelizacao;

    @FXML
    private  JFXToggleButton tbGerenciarCompra;

    @FXML
    private  JFXToggleButton tbGerenciarAutorizacoes;

    @FXML
    private  JFXToggleButton tbGerenciarOrgao;

    @FXML
    private  JFXToggleButton tbGerenciarProdutos;

    @FXML
    private  JFXToggleButton tbGerenciarCombustiveis;

    @FXML
    private  JFXToggleButton tbGerenciarVenda;

    @FXML
    private  JFXToggleButton tbGerenciarPermissoes;

    @FXML
    private BorderPane borderPaneTabela;

    @FXML
    private JFXTextField campoPesquisar;
    
    @FXML
    private JFXTreeTableView<PermissaoClass>treeTableViewLogins;
 
    private int idLogin = 0;
    
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
	void btnExcluir(ActionEvent event) {
		if (treeTableViewLogins.getSelectionModel().getSelectedIndex() != -1) {
			String[] ids = treeTableViewLogins.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idLogin = Integer.parseInt(ids[0]);
			try {
				new PermissoesDao().remover(idLogin);
				carregarTabela();
				limparCampos();
				
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Permissão removido com sucesso", 4000); 
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona permissão na tabela", 4000); 
		}
	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparCampos();
		carregarTabela();
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewLogins.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (comboBoxNivelAcesso.getValue() != null && comboBoxFuncionario.getValue() != null 
						&& !campoEmail.getText().isEmpty() && !campoSenha.getText().isEmpty()){
						
					try {
						
						new PermissoesDao().cadastrar(new Login(0, comboBoxFuncionario.getValue(), campoEmail.getText(), 
								campoSenha.getText(), tbGerenciarAutorizacoes.isSelected(), tbGerenciarOrgao.isSelected(),
								tbGerenciarFornecedores.isSelected(), tbGerenciarClientes.isSelected(),
								tbGerenciarFuncionarios.isSelected(), tbGerenciarFidelizacao.isSelected(),
								tbGerenciarCompra.isSelected(), tbGerenciarVenda.isSelected(), 
								tbGerenciarCombustiveis.isSelected(), tbGerenciarProdutos.isSelected(),
								tbGerenciarCaixa.isSelected(), tbGerenciarPermissoes.isSelected(),
								textAreaInformacao.getText(), 
								comboBoxNivelAcesso.getValue()));
						carregarTabela();
						limparCampos();
						
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Permissão cadastrado com sucesso", 4000); 
						
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

		} else if (treeTableViewLogins.getSelectionModel().getSelectedIndex() != -1) {

			String[] ids = treeTableViewLogins.getSelectionModel().getSelectedItem().getValue().toString()
					.split("/");
			idLogin = Integer.parseInt(ids[0]);

			if (comboBoxNivelAcesso.getValue() != null && comboBoxFuncionario.getValue() != null 
					&& !campoEmail.getText().isEmpty() && !campoSenha.getText().isEmpty()){

				try {
					new PermissoesDao().alterar(new Login(idLogin, comboBoxFuncionario.getValue(), campoEmail.getText(), 
							campoSenha.getText(), tbGerenciarAutorizacoes.isSelected(), tbGerenciarOrgao.isSelected(),
							tbGerenciarFornecedores.isSelected(), tbGerenciarClientes.isSelected(),
							tbGerenciarFuncionarios.isSelected(), tbGerenciarFidelizacao.isSelected(),
							tbGerenciarCompra.isSelected(), tbGerenciarVenda.isSelected(), 
							tbGerenciarCombustiveis.isSelected(), tbGerenciarProdutos.isSelected(),
							tbGerenciarCaixa.isSelected(), tbGerenciarPermissoes.isSelected(),
							textAreaInformacao.getText(), 
							comboBoxNivelAcesso.getValue()));
					
					carregarTabela();
					limparCampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Permissão alterado com sucesso", 4000); 
					
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

		treeTableViewLogins.setOnMouseClicked(event -> {
			if (treeTableViewLogins.getSelectionModel().getSelectedIndex() != -1) {
				String[] ids = treeTableViewLogins.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				idLogin = Integer.parseInt(ids[0]);

				try {

					new PermissoesDao().listar().forEach(permissao -> { 

						if (permissao.getId_login() == idLogin) {
					
							campoEmail.setText(permissao.getEmail());
							textAreaInformacao.setText(permissao.getInformacao_adicional());
							campoSenha.setText(permissao.getSenha());
							comboBoxFuncionario.setValue(permissao.getFuncionario());
							comboBoxNivelAcesso.setValue(permissao.getNivel_acesso());
							tbGerenciarAutorizacoes.setSelected(permissao.isG_autorizacao_licenca());;
							tbGerenciarCaixa.setSelected(permissao.isG_caixa());
							tbGerenciarClientes.setSelected(permissao.isG_clientes());
							tbGerenciarCombustiveis.setSelected(permissao.isG_combustivel());
							tbGerenciarCompra.setSelected(permissao.isG_compra_produtos());
							tbGerenciarFidelizacao.setSelected(permissao.isG_fidelizacao());
							tbGerenciarFornecedores.setSelected(permissao.isG_fornecedores());
							tbGerenciarFuncionarios.setSelected(permissao.isG_funcionario());
							tbGerenciarOrgao.setSelected(permissao.isG_orgao());
							tbGerenciarPermissoes.setSelected(permissao.isG_permissoes());
							tbGerenciarVenda.setSelected(permissao.isG_venda_produtos());
							tbGerenciarProdutos.setSelected(permissao.isG_produtos());
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
		JFXTreeTableColumn<PermissaoClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(55);
		JFXTreeTableColumn<PermissaoClass, String> colunaNome = new JFXTreeTableColumn<>("NOME");
		colunaNome.setPrefWidth(250);
		JFXTreeTableColumn<PermissaoClass, String> colunaNivelAcesso = new JFXTreeTableColumn<>("NÍVEL DE ACESSO");
		colunaNivelAcesso.setPrefWidth(250);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<PermissaoClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id; 
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<PermissaoClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().nome;
			else
				return colunaNome.getComputedValue(param);
		});
		colunaNivelAcesso.setCellValueFactory((TreeTableColumn.CellDataFeatures<PermissaoClass, String> param) -> {
			if (colunaNivelAcesso.validateValue(param))
				return param.getValue().getValue().nivelAcesso; 
			else
				return colunaNivelAcesso.getComputedValue(param);
		});
		ObservableList<PermissaoClass> lista_logins = FXCollections.observableArrayList(); 

		// carregando registros com os campos da coluna da classe anônima
		try {
			new PermissoesDao().listar().forEach(permissao -> {
				lista_logins.add(new PermissaoClass(permissao.getId_login(), permissao.getFuncionario() != null ? permissao.getFuncionario().getPessoa().getNome() : "",
						permissao.getNivel_acesso()));
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}

		treeTableViewLogins
				.setRoot(new RecursiveTreeItem<PermissaoClass>(lista_logins, RecursiveTreeObject::getChildren));

		treeTableViewLogins.getColumns().setAll(colunaId, colunaNome, colunaNivelAcesso);
		treeTableViewLogins.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewLogins.setPredicate(
					person -> person.getValue().id.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().nome.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().nivelAcesso.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
		visualizarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class PermissaoClass extends RecursiveTreeObject<PermissaoClass> {

		StringProperty id;
		StringProperty nome;
		StringProperty nivelAcesso;

		public PermissaoClass(int idFornecedor, String nome, String nivelAcesso) {
			this.id = new SimpleStringProperty(String.valueOf(idFornecedor));
			this.nome = new SimpleStringProperty(nome);
			this.nivelAcesso = new SimpleStringProperty(nivelAcesso);
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
			treeTableViewLogins = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewLogins);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void preencherComboBox() {
		
		comboBoxNivelAcesso.getItems().addAll("Administrador","Gerente","Atendente", "Personalizado");
		
		comboBoxFuncionario.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxFuncionario.isFocused()) {
				comboBoxFuncionario.setPromptText("");
				try {
					comboBoxFuncionario.setItems(new FuncionarioDao().listar());
					// comboBoxFuncionario.setRecords(new
					// SqlFuncionario().listar());
					// comboBoxFuncionario.setOnlyAllowPredefinedItems(true);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				comboBoxFuncionario.setPromptText("Pesquisar Funcionário ...");
			}
		});
		comboBoxNivelAcesso.valueProperty().addListener((observable, oldValue, newValue)->{
			if(newValue != null){
					if(newValue.equals("Administrador")){
						tbGerenciarAutorizacoes.setSelected(true);;
						tbGerenciarCaixa.setSelected(true);
						tbGerenciarClientes.setSelected(true);
						tbGerenciarCombustiveis.setSelected(true);
						tbGerenciarCompra.setSelected(true);
						tbGerenciarFidelizacao.setSelected(true);
						tbGerenciarFornecedores.setSelected(true);
						tbGerenciarFuncionarios.setSelected(true);
						tbGerenciarOrgao.setSelected(true);
						tbGerenciarPermissoes.setSelected(true);
						tbGerenciarVenda.setSelected(true);
						tbGerenciarProdutos.setSelected(true);
					}else if(newValue.equals("Gerente")){
						tbGerenciarAutorizacoes.setSelected(true);;
						tbGerenciarCaixa.setSelected(true);
						tbGerenciarClientes.setSelected(true);
						tbGerenciarCombustiveis.setSelected(true);
						tbGerenciarCompra.setSelected(true);
						tbGerenciarFidelizacao.setSelected(true);
						tbGerenciarFornecedores.setSelected(true);
						tbGerenciarFuncionarios.setSelected(true);
						tbGerenciarOrgao.setSelected(true);
						tbGerenciarPermissoes.setSelected(false);
						tbGerenciarVenda.setSelected(true);
						tbGerenciarProdutos.setSelected(true);
					}else if(newValue.equals("Atendente")){
						tbGerenciarAutorizacoes.setSelected(false);;
						tbGerenciarCaixa.setSelected(false);
						tbGerenciarClientes.setSelected(true);
						tbGerenciarCombustiveis.setSelected(false);
						tbGerenciarCompra.setSelected(false);
						tbGerenciarFidelizacao.setSelected(false);
						tbGerenciarFornecedores.setSelected(false);
						tbGerenciarFuncionarios.setSelected(false);
						tbGerenciarOrgao.setSelected(false);
						tbGerenciarPermissoes.setSelected(false);
						tbGerenciarVenda.setSelected(true);
						tbGerenciarProdutos.setSelected(false);
					}
				
			}
		});
		
		tbGerenciarAutorizacoes.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarCaixa.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarClientes.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarCombustiveis.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarCompra.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarFidelizacao.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarFornecedores.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarFuncionarios.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarOrgao.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarPermissoes.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarVenda.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
		tbGerenciarProdutos.setOnAction(e->comboBoxNivelAcesso.setValue("Personalizado"));
	}

	void limparCampos() {
		campoEmail.setText("");
		textAreaInformacao.setText("");
		campoSenha.setText("");
		comboBoxFuncionario.setValue(null);
		comboBoxNivelAcesso.setValue(null);
		tbGerenciarAutorizacoes.setSelected(false);;
		tbGerenciarCaixa.setSelected(false);
		tbGerenciarClientes.setSelected(false);
		tbGerenciarCombustiveis.setSelected(false);
		tbGerenciarCompra.setSelected(false);
		tbGerenciarFidelizacao.setSelected(false);
		tbGerenciarFornecedores.setSelected(false);
		tbGerenciarFuncionarios.setSelected(false);
		tbGerenciarOrgao.setSelected(false);
		tbGerenciarPermissoes.setSelected(false);
		tbGerenciarVenda.setSelected(false);
		tbGerenciarProdutos.setSelected(false);
	}
}
