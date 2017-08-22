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
import com.postoGasolina.dao.CompraDao;
import com.postoGasolina.dao.FornecedoresDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.model.Fluxo_caixa2;
import com.postoGasolina.model.Fornecedor;
import com.postoGasolina.model.Item_pedido;
import com.postoGasolina.model.Pedido_compra;
import com.postoGasolina.model.Produto_loja;
import com.postoGasolina.util.AutoShowComboBoxHelper;
import com.postoGasolina.util.NumeroTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class TelaCompraController implements Initializable {

	@FXML
	private ImageView btnFornecedor;

	@FXML
	private JFXButton btnAdicionar;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private ImageView btnExcluir;

	@FXML
	private JFXButton btnDesconto;

	@FXML
	private JFXButton btnFechamento;

	private NumeroTextField campoPreco = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
	private NumeroTextField campoTotal = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
	private NumeroTextField campoDesconto = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
	private NumeroTextField campoTotalCompra = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	private JFXTextField campoQuantidade = new JFXTextField("0");

	@FXML
	private GridPane gridPaneBottom;

	@FXML
	private GridPane gridPaneTop;

	@FXML
	private JFXTreeTableView<CompraClass> treeTableViewCompra;

	private int idTipoCombustivel;

	// private AutoFillComboBox<Cliente> comboBoxCliente = new
	// AutoFillComboBox<>();

	// private AutoFillComboBox<Funcionario> comboBoxFuncionario = new
	// AutoFillComboBox<>();

	// private AutoFillComboBox<Produto_loja> comboBoxProduto = new
	// AutoFillComboBox<>();

	ComboBox<Produto_loja> comboBoxProduto = new ComboBox<>();

	ComboBox<Fornecedor> comboBoxFornecedor = new ComboBox<>();
	@FXML
	private JFXTextField campoResponsavel;
	private ObservableList<Item_pedido> lista_itensPedido = FXCollections.observableArrayList();
	private static int idItemPedido;
	private static int idtabela = 1;
	private BigDecimal totalCalculado = new BigDecimal("0");
	
	static JFXTextField mensagem = new JFXTextField("0");
	
	private JFXSnackbar s;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			carregarComponentes();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			carregarTabela();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	void mouseEventCompra(MouseEvent event) { 
		if (!lista_itensPedido.isEmpty()) {
			campoDesconto.setNumber(RecebePedidoCompra.getDesconto());
		}
		if(mensagem.getText().equals("1")){
			s = new JFXSnackbar(borderPaneTabela);
			//String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			s.show("Desconto adicionado com sucesso", 4000);
			mensagem.setText("0");
		}
	}

	@FXML
	void btnNovo(ActionEvent event) {
		limparcamposCompleto();
	}

	private void limparcamposCompleto() {
		// TODO Auto-generated method stub
		lista_itensPedido.clear();
		carregarTabela();
		RecebePedidoCompra.setDesconto(BigDecimal.ZERO);
		limparcampos();
		campoTotalCompra.setNumber(BigDecimal.ZERO);
		campoDesconto.setNumber(BigDecimal.ZERO);
		comboBoxFornecedor.setValue(null);
		campoResponsavel.setText("");
	}

	@FXML
	void btnAdicionar(ActionEvent event) {

		if (comboBoxProduto.getValue() != null) {
			if (!campoQuantidade.getText().isEmpty() && !campoQuantidade.getText().equals("0")) {

				lista_itensPedido.add(new Item_pedido(comboBoxProduto.getSelectionModel().getSelectedItem(), 0,
						campoPreco.getNumber(), new BigDecimal(campoQuantidade.getText()),
						comboBoxProduto.getSelectionModel().getSelectedItem().getTipo_produto(),
						campoPreco.getNumber().multiply(new BigDecimal(campoQuantidade.getText())), idtabela++));

				limparcampos();
				carregarTabela();

				carregarTotalCompra();
				
				s = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				s.show("Produto adicionado com sucesso", 4000);

			} else {
				s = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				s.show("Informa quantidade", 4000);
			}
		} else {
			s = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			s.show("Seleciona um produto", 4000);
		}

	}

	private void carregarTotalCompra() {
		// TODO Auto-generated method stub
		totalCalculado = new BigDecimal("0");

		lista_itensPedido.forEach(item -> {
			totalCalculado = totalCalculado.add(item.getTotal());
		});

		totalCalculado = totalCalculado.subtract(campoDesconto.getNumber());

		campoTotalCompra.setNumber(totalCalculado);

	}

	@FXML
	void btnDesconto(ActionEvent event) {
		RecebePedidoCompra.setTotal_pagar(campoTotalCompra.getNumber());
		new Main().carregarTelaDesconto2();
	}

	@FXML
	void btnFechamento(ActionEvent event) {
		try {
			new RecebePedidoCompra(0, comboBoxFornecedor.getValue(), new Fluxo_caixa(), campoTotalCompra.getNumber(),
					campoDesconto.getNumber(), campoResponsavel.getText(), lista_itensPedido);

			if (comboBoxFornecedor.getValue() != null && !campoResponsavel.getText().isEmpty() && !lista_itensPedido.isEmpty()) {
				try {

					new CompraDao()
							.finalizar(new Pedido_compra(0, RecebePedidoCompra.getFornecedor(), new Fluxo_caixa2(),
									RecebePedidoCompra.getCampoResponsavel(), RecebePedidoCompra.getTotal_pagar(),
									RecebePedidoCompra.getDesconto(), RecebePedidoCompra.getItens_pedido()));

					limparcamposCompleto();
					
					s = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					s.show("Compra registrada com sucesso", 4000);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} else {
				s = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				s.show("Campos obrigatï¿½rios nï¿½o informado", 4000);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@FXML
	void BtnExcluirMouseClicked(MouseEvent event) {
		System.out.println(treeTableViewCompra.getSelectionModel().getSelectedIndex() != -1);
		if (treeTableViewCompra.getSelectionModel().getSelectedIndex() != -1) {

			try {
				System.out.println("entrou");
				int id = Integer
						.valueOf(treeTableViewCompra.getSelectionModel().getSelectedItem().getValue().toString());

				
				for (int i = 0; i < lista_itensPedido.size(); ++i) {
					if (lista_itensPedido.get(i).getIdItem() == id) {
						lista_itensPedido.remove(i);

						carregarTabela();
						carregarTotalCompra();
						
						s = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						s.show("Produto removido com sucesso", 4000);
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else {
			s = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			s.show("Seleciona produto na tabela", 4000);
		}
	}

	void carregarComponentes() throws SQLException, ClassNotFoundException {

		String style = getClass().getResource("/com/postoGasolina/style/TelaVenda.css").toExternalForm();

		campoDesconto.getStylesheets().add(style);
		campoDesconto.getStyleClass().add("format-campo");
		campoDesconto.setUnFocusColor(Color.TRANSPARENT);
		campoDesconto.setFocusColor(Color.TRANSPARENT);
		campoDesconto.setAlignment(Pos.CENTER);
		campoDesconto.setEditable(false);
		campoDesconto.setFocusTraversable(false);
		gridPaneBottom.add(campoDesconto, 1, 0);
		gridPaneBottom.setMargin(campoDesconto, new Insets(0, 0, 0, 0));

		campoTotalCompra.getStylesheets().add(style);
		campoTotalCompra.getStyleClass().add("format-campo");
		campoTotalCompra.setUnFocusColor(Color.TRANSPARENT);
		campoTotalCompra.setFocusColor(Color.TRANSPARENT);
		campoTotalCompra.setAlignment(Pos.CENTER);
		campoTotalCompra.setEditable(false);
		campoTotalCompra.setFocusTraversable(false);
		gridPaneBottom.add(campoTotalCompra, 2, 0);
		gridPaneBottom.setMargin(campoTotalCompra, new Insets(0, 0, 0, 0));

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionario.css").toExternalForm();

		campoPreco.getStylesheets().add(style);
		campoPreco.getStyleClass().add("formata-campo");
		campoPreco.setUnFocusColor(Color.WHITE);
		campoPreco.setFocusColor(Color.WHITE);
		campoPreco.setEditable(true);
		campoPreco.setFocusTraversable(false);
		gridPaneTop.add(campoPreco, 1, 3);
		gridPaneTop.setMargin(campoPreco, new Insets(10, 270, 0, 120));

		campoTotal.getStylesheets().add(style);
		campoTotal.getStyleClass().add("formata-campo");
		campoTotal.setUnFocusColor(Color.WHITE);
		campoTotal.setFocusColor(Color.WHITE);
		campoTotal.setEditable(false);
		campoTotal.setFocusTraversable(false);
		gridPaneTop.add(campoTotal, 1, 3);
		gridPaneTop.setMargin(campoTotal, new Insets(10, 140, 0, 240));

		campoQuantidade.getStylesheets().add(style);
		campoQuantidade.getStyleClass().add("formata-campo");
		campoQuantidade.setUnFocusColor(Color.WHITE);
		campoQuantidade.setFocusColor(Color.WHITE);
		campoQuantidade.setFocusTraversable(false);
		gridPaneTop.add(campoQuantidade, 1, 3);
		gridPaneTop.setMargin(campoQuantidade, new Insets(10, 390, 0, 0));

		campoPreco.setLabelFloat(true);
		campoTotal.setLabelFloat(true);
		campoQuantidade.setLabelFloat(true);
		campoPreco.setPromptText("Preço");
		campoTotal.setPromptText("Total");
		campoQuantidade.setPromptText("Quantidade *");
		
		campoPreco.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				try {
					if (!newValue.isEmpty()) {
						campoTotal.setNumber(campoPreco.getNumber().multiply(new BigDecimal(campoQuantidade.getText())));
					} 
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		});

		// COMBOBOX PESQUISAR

		comboBoxFornecedor.setItems(new FornecedoresDao().listar());
		// comboBoxCliente.setRecords(new Cliente().listar());
		// comboBoxCliente.setOnlyAllowPredefinedItems(true);

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionarioComboBox.css")
				.toExternalForm();
		comboBoxFornecedor.getStylesheets().add(style);
		comboBoxFornecedor.getStyleClass().add("formata-campo");
		comboBoxFornecedor.setPrefWidth(385);
		comboBoxFornecedor.setPromptText("Pesquisar Fornecedor ...");
		comboBoxFornecedor.setPadding(new Insets(0, 0, 0, -10));
		comboBoxFornecedor.setFocusTraversable(false);
	
		new AutoShowComboBoxHelper(comboBoxFornecedor);
		
		gridPaneTop.add(comboBoxFornecedor, 0, 1);
		gridPaneTop.setMargin(comboBoxFornecedor, new Insets(11, 0, 0, 50));

		comboBoxFornecedor.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxFornecedor.isFocused()) {

				comboBoxFornecedor.setPromptText("");

				try {
					comboBoxFornecedor.setItems(new FornecedoresDao().listar());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// comboBoxCliente.setRecords(new Cliente().listar());
				// comboBoxCliente.setOnlyAllowPredefinedItems(true);
			} else {
				comboBoxFornecedor.setPromptText("Pesquisar Fornecedor ...");
			}

		});

		comboBoxProduto.setItems(new Produto_loja().listar());
		// comboBoxProduto.setRecords(new Produto_loja().listar());
		// comboBoxProduto.setOnlyAllowPredefinedItems(true);

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionarioComboBox.css")
				.toExternalForm();
		comboBoxProduto.getStylesheets().add(style);
		comboBoxProduto.getStyleClass().add("formata-campo");
		comboBoxProduto.setPrefWidth(385);
		comboBoxProduto.setPromptText("Pesquisar Produtos ...");
		comboBoxProduto.setPadding(new Insets(0, 0, 0, -10));
		comboBoxProduto.setFocusTraversable(false);
		new AutoShowComboBoxHelper(comboBoxProduto);
		gridPaneTop.add(comboBoxProduto, 0, 2);
		gridPaneTop.setMargin(comboBoxProduto, new Insets(60, 0, 0, 50));

		comboBoxProduto.focusedProperty().addListener((observable, oldValue, newValue) -> {

			if (comboBoxProduto.isFocused()) {
				comboBoxProduto.setPromptText("");
				comboBoxProduto.setItems(new Produto_loja().listar());
				// comboBoxProduto.setRecords(new Produto_loja().listar());
				// comboBoxProduto.setOnlyAllowPredefinedItems(true);
				campoQuantidade.setText("0");
			} else {
				comboBoxProduto.setPromptText("Pesquisar Produtos ...");
			}

		});

		try {
			comboBoxProduto.valueProperty().addListener(new ChangeListener<Produto_loja>() {

				@Override
				public void changed(ObservableValue<? extends Produto_loja> observable, Produto_loja oldValue,
						Produto_loja newValue) {

					campoQuantidade.setText("0");
					campoPreco.setNumber(BigDecimal.ZERO);
					campoTotal.setNumber(BigDecimal.ZERO);

					if (newValue != null) {
						if (comboBoxProduto.getSelectionModel() != null) {

							if (comboBoxProduto.getSelectionModel().getSelectedIndex() != -1) {
								if (comboBoxProduto.getSelectionModel().getSelectedItem()
										.getTipo_produto() == "combustivel") {
									campoQuantidade.setEditable(true);
									btnAdicionar.setDisable(false);
									BigDecimal preco = comboBoxProduto.getSelectionModel().getSelectedItem()
											.getCombustivel().getPreco_venda();
									campoPreco.setNumber(preco);
								} else if (comboBoxProduto.getSelectionModel().getSelectedItem()
										.getTipo_produto() == "produto") {
									BigDecimal preco = comboBoxProduto.getSelectionModel().getSelectedItem()
											.getProduto().getPreco_venda();
									campoPreco.setNumber(preco);
									if (comboBoxProduto.getSelectionModel().getSelectedItem().getProduto()
											.isNao_controlar_estoque()) {
										campoQuantidade.setEditable(false);
										campoQuantidade.setText("1");
										btnAdicionar.setDisable(true);
									} else {
										campoQuantidade.setEditable(true);
										btnAdicionar.setDisable(false);
									}
								}
							}
						}
					}

				}
			});
		} catch (Exception e2) {
			// TODO: handle exception

		}

		campoQuantidade.setOnKeyTyped(event -> {
			String input = event.getCharacter();
			String numeros = "0123456789.";
			if (!numeros.contains(input + "")) {
				event.consume();
			}
		});

		campoQuantidade.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				try {
					if (!newValue.isEmpty()) {
						final NumeroTextField preco = new NumeroTextField(campoPreco.getNumber());
						final NumeroTextField qtdAtualizado = new NumeroTextField(new BigDecimal(newValue));
						campoTotal.setNumber(preco.getNumber().multiply(qtdAtualizado.getNumber()));
					} else {
						campoTotal.setNumber(BigDecimal.ZERO);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		});

		campoDesconto.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				carregarTotalCompra();
				campoDesconto.setNumber(RecebePedidoCompra.getDesconto());
			}
		});
		campoTotalCompra.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				// campoDesconto.setNumber(DescontoAtualizado);
			}
		});

	}

	public void limparcampos() {

		// campoPesquisar.setText("");
		campoQuantidade.setText("0");
		comboBoxProduto.setValue(null);
		campoTotal.setNumber(BigDecimal.ZERO);
		campoPreco.setNumber(BigDecimal.ZERO);

	}

	void carregarTabela() {

		atualizarTabela();

		// Criando as colunas da tabela
		JFXTreeTableColumn<CompraClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(150);
		JFXTreeTableColumn<CompraClass, String> colunaNome = new JFXTreeTableColumn<>("DESCRIÇÃO");
		colunaNome.setPrefWidth(300);
		JFXTreeTableColumn<CompraClass, String> colunaQuantidade = new JFXTreeTableColumn<>("QUANTIDADE");
		colunaQuantidade.setPrefWidth(200);
		JFXTreeTableColumn<CompraClass, String> colunaPreco = new JFXTreeTableColumn<>("PREÇO");
		colunaPreco.setPrefWidth(150);
		JFXTreeTableColumn<CompraClass, String> colunaTotal = new JFXTreeTableColumn<>("TOTAL");
		colunaTotal.setPrefWidth(150);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id;
			else
				return colunaId.getComputedValue(param);
		});
		colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraClass, String> param) -> {
			if (colunaNome.validateValue(param))
				return param.getValue().getValue().nome;
			else
				return colunaNome.getComputedValue(param);
		});
		colunaQuantidade.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraClass, String> param) -> {
			if (colunaQuantidade.validateValue(param))
				return param.getValue().getValue().quantidade;
			else
				return colunaQuantidade.getComputedValue(param);
		});

		colunaPreco.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraClass, String> param) -> {
			if (colunaPreco.validateValue(param))
				return param.getValue().getValue().preco;
			else
				return colunaPreco.getComputedValue(param);
		});
		colunaTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraClass, String> param) -> {
			if (colunaTotal.validateValue(param))
				return param.getValue().getValue().total;
			else
				return colunaTotal.getComputedValue(param);
		});

		ObservableList<CompraClass> lista_ItensTabela = FXCollections.observableArrayList();
		lista_itensPedido.forEach(itens -> {
			if (itens.getProduto_loja().getTipo_produto().equals("combustivel")) {

				lista_ItensTabela.add(new CompraClass(itens.getIdItem(),
						itens.getProduto_loja().getCombustivel().getTipoCombustivel().getNome(), itens.getQuantidade(),
						itens.getPreco_unitario(), itens.getTotal(),
						itens.getProduto_loja().getCombustivel().getId_combustivel()));
			} else if (itens.getProduto_loja().getTipo_produto().equals("produto")) {
				lista_ItensTabela
						.add(new CompraClass(itens.getIdItem(), itens.getProduto_loja().getProduto().getDescricao(),
								itens.getQuantidade(), itens.getPreco_unitario(), itens.getTotal(),
								itens.getProduto_loja().getProduto().getId_produto()));
			}
		});

		treeTableViewCompra
				.setRoot(new RecursiveTreeItem<CompraClass>(lista_ItensTabela, RecursiveTreeObject::getChildren));

		treeTableViewCompra.getColumns().setAll(colunaId, colunaNome, colunaQuantidade, colunaPreco, colunaTotal);
		treeTableViewCompra.setShowRoot(false);

	}

	// criando uma classe anonima para ser consumida pela tabela
	class CompraClass extends RecursiveTreeObject<CompraClass> {

		StringProperty id;
		StringProperty nome;
		StringProperty quantidade;
		StringProperty preco;
		StringProperty total;
		StringProperty idProduto;

		public CompraClass(int id, String nome, BigDecimal quantidade, BigDecimal preco, BigDecimal total,
				int idProduto) {
			super();
			this.id = new SimpleStringProperty(String.valueOf(id));
			this.nome = new SimpleStringProperty(nome);
			this.quantidade = new SimpleStringProperty(String.valueOf(quantidade));
			this.preco = new SimpleStringProperty(String.valueOf(
					new NumeroTextField(preco, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));
			this.total = new SimpleStringProperty(String.valueOf(
					new NumeroTextField(total, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));
			this.idProduto = new SimpleStringProperty(String.valueOf(idProduto));
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
			treeTableViewCompra = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewCompra);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static class RecebePedidoCompra {

		private static int id_pedido_compra;
		private static Fornecedor fornecedor;
		private static Fluxo_caixa fluxoCaixa;
		private static BigDecimal total_pagar;
		private static BigDecimal desconto;
		private static String campoResponsavel;
		private static int limparCompra;
		private static ObservableList<Item_pedido> itens_pedido = FXCollections.observableArrayList();

		public RecebePedidoCompra() {
			// TODO Auto-generated constructor stub
		}

		public RecebePedidoCompra(int id_pedido_compra, Fornecedor fornecedor, Fluxo_caixa fluxoCaixa,
				BigDecimal total_pagar, BigDecimal desconto, String campoResponsavel,
				ObservableList<Item_pedido> itens_pedido) {
			RecebePedidoCompra.id_pedido_compra = id_pedido_compra;
			RecebePedidoCompra.fornecedor = fornecedor;
			RecebePedidoCompra.fluxoCaixa = fluxoCaixa;
			RecebePedidoCompra.total_pagar = total_pagar;
			RecebePedidoCompra.desconto = desconto;
			RecebePedidoCompra.campoResponsavel = campoResponsavel;
			RecebePedidoCompra.itens_pedido = itens_pedido;
		}

		public static int getId_pedido_compra() {
			return id_pedido_compra;
		}

		public static void setId_pedido_compra(int id_pedido_compra) {
			RecebePedidoCompra.id_pedido_compra = id_pedido_compra;
		}

		public static Fornecedor getFornecedor() {
			return fornecedor;
		}

		public static void setFornecedor(Fornecedor fornecedor) {
			RecebePedidoCompra.fornecedor = fornecedor;
		}

		public static Fluxo_caixa getFluxoCaixa() {
			return fluxoCaixa;
		}

		public static void setFluxoCaixa(Fluxo_caixa fluxoCaixa) {
			RecebePedidoCompra.fluxoCaixa = fluxoCaixa;
		}

		public static BigDecimal getTotal_pagar() {
			return total_pagar;
		}

		public static void setTotal_pagar(BigDecimal total_pagar) {
			RecebePedidoCompra.total_pagar = total_pagar;
		}

		public static BigDecimal getDesconto() {
			if (desconto != null) {
				return desconto;
			} else {
				return BigDecimal.ZERO;
			}
		}

		public static void setDesconto(BigDecimal desconto) {
			if (desconto != null) {
				RecebePedidoCompra.desconto = desconto;
			} else {
				RecebePedidoCompra.desconto = BigDecimal.ZERO;
			}
		}

		public static String getCampoResponsavel() {
			return campoResponsavel;
		}

		public static void setForma_pagamento(String forma_pagamento) {
			RecebePedidoCompra.campoResponsavel = forma_pagamento;
		}

		public static ObservableList<Item_pedido> getItens_pedido() {
			return itens_pedido;
		}

		public static void setItens_pedido(ObservableList<Item_pedido> itens_pedido) {
			RecebePedidoCompra.itens_pedido = itens_pedido;
		}

		public static int getLimparCompra() {
			return limparCompra;
		}

		public static void setLimparCompra(int limparCompra) {
			RecebePedidoCompra.limparCompra = limparCompra;
		}

	}

	@FXML
	void adicionarFornecedor(MouseEvent event) {
		new Main().carregarTelaFornecedorUtilitaria();
	}

}
