package com.postoGasolina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.CategoriaDao;
import com.postoGasolina.dao.ProdutosDao;
import com.postoGasolina.dao.UnidadeMedidaDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Categoria;
import com.postoGasolina.model.Produto;
import com.postoGasolina.model.Unidade_medida;
import com.postoGasolina.util.NumeroTextField;
import com.postoGasolina.util.TextFieldFormatter;

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

public class TelaGerenciarProdutos implements Initializable {

	@FXML
	private GridPane gridPaneTop;

	@FXML
	private ComboBox<Unidade_medida> comboboxUnidadeMedida;

	@FXML
	private ComboBox<Categoria> comboboxCategoria;

	@FXML
	private ImageView btnCategoria;

	@FXML
	private ImageView btnUnidadeMedida;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXTextField campoDescricao;

	@FXML
	private JFXCheckBox checkBoxNaoControlarEstoque;

	@FXML
	private JFXTextField campoCodigoProduto;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	private JFXTreeTableView<ProdutoClass> treeTableViewProduto;

	private int idProduto;
	
	private JFXSnackbar snackBar;
	
	@FXML
	private NumeroTextField campoQuantidade = new NumeroTextField(null);
	@FXML
	private NumeroTextField campoEstoqueMinimo = new NumeroTextField(null);
	@FXML
	private NumeroTextField campoEstoqueMaximo = new NumeroTextField(null);
	
	@FXML
	private NumeroTextField campoPreco = new NumeroTextField(BigDecimal.ZERO, 
			NumberFormat.getCurrencyInstance(new Locale("pt","BR")));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregarComponents();
	}

	void carregarComponents() {
		// TODO Auto-generated method stub
		try {
			carregarTabela();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionario.css")
				.toExternalForm();

		campoQuantidade.getStylesheets().add(style);
		campoQuantidade.getStyleClass().add("formata-campo");
		campoQuantidade.setUnFocusColor(Color.WHITE);
		campoQuantidade.setFocusColor(Color.WHITE);
		campoQuantidade.setPromptText("Estoque disponível *");
		campoQuantidade.setLabelFloat(true);
		gridPaneTop.add(campoQuantidade, 1, 2);
		gridPaneTop.setMargin(campoQuantidade, new Insets(0, 167, 0, 0));

		campoEstoqueMinimo.getStylesheets().add(style);
		campoEstoqueMinimo.getStyleClass().add("formata-campo");
		campoEstoqueMinimo.setUnFocusColor(Color.WHITE);
		campoEstoqueMinimo.setFocusColor(Color.WHITE);
		campoEstoqueMinimo.setPromptText("Estoque mínimo *");
		campoEstoqueMinimo.setLabelFloat(true);
		gridPaneTop.add(campoEstoqueMinimo, 0, 4);
		gridPaneTop.setMargin(campoEstoqueMinimo, new Insets(0, 10, 20, 260));

		campoEstoqueMaximo.getStylesheets().add(style);
		campoEstoqueMaximo.getStyleClass().add("formata-campo");
		campoEstoqueMaximo.setUnFocusColor(Color.WHITE);
		campoEstoqueMaximo.setFocusColor(Color.WHITE);
		campoEstoqueMaximo.setPromptText("Estoque máximo *");
		campoEstoqueMaximo.setLabelFloat(true);
		gridPaneTop.add(campoEstoqueMaximo, 1, 4);
		gridPaneTop.setMargin(campoEstoqueMaximo, new Insets(0, 350, 20, 0));
		
		campoPreco.getStylesheets().add(style);
		campoPreco.getStyleClass().add("formata-campo");
		campoPreco.setUnFocusColor(Color.WHITE);
		campoPreco.setFocusColor(Color.WHITE);
		campoPreco.setPromptText("Preço *");
		campoPreco.setLabelFloat(true);
		gridPaneTop.add(campoPreco, 1, 4);
		gridPaneTop.setMargin(campoPreco, new Insets(0,10, 20, 330));
		
		btnUnidadeMedida.setOnMouseClicked(event ->{
			new Main().carregarTelaUnidadeMedida();
		});
		btnCategoria.setOnMouseClicked(event ->{
			new Main().carregarTelaCategoria();
		});;
		
		
		comboboxCategoria.focusedProperty().addListener((observable, oldValue, newValue) ->{
			if(comboboxCategoria.isFocused()){
				try {
					comboboxCategoria.setItems(new CategoriaDao().listar());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		comboboxUnidadeMedida.focusedProperty().addListener((observable, oldValue, newValue) ->{
			if(comboboxUnidadeMedida.isFocused()){
				try {
					comboboxUnidadeMedida.setItems(new UnidadeMedidaDao().listar());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});;
		
		campoCodigoProduto.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("#########");
			ttf.setTf(campoCodigoProduto);
			ttf.formatter();
		});
		
		checkBoxNaoControlarEstoque.setOnAction(event ->{
			verificarCheckBox();
		});
	}

	@FXML
	void btnExcluir(ActionEvent event) {
		if (treeTableViewProduto.getSelectionModel().getSelectedIndex() != -1) {

			idProduto = Integer.parseInt(treeTableViewProduto.getSelectionModel().getSelectedItem().getValue().toString());
			try {
				new ProdutosDao().remover(idProduto);
				carregarTabela();
				limparcampos();
				
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Produto removido com sucesso", 4000);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				snackBar = new JFXSnackbar(borderPaneTabela);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Produto sendo utilizado", 4000);
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona produto na tabela", 4000);
		}
	}

	@FXML
	void btnNovo(ActionEvent event) {
		try {
			limparcampos();
			carregarTabela();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if (treeTableViewProduto.getSelectionModel().getSelectedIndex() == -1) {
			try {

				if (!campoDescricao.getText().isEmpty() && comboboxCategoria.getValue() != null
						&& comboboxUnidadeMedida.getValue() != null && !campoPreco.getNumber().equals(BigDecimal.ZERO)) {

					try {
						new ProdutosDao().cadastrar(new Produto(0, campoCodigoProduto.getText(),
								new Categoria(comboboxCategoria.getValue().getId_categoria(),
										comboboxCategoria.getValue().getNome()),
								new Unidade_medida(comboboxUnidadeMedida.getValue().getId_unidade_medida(),
										comboboxUnidadeMedida.getValue().getNome()),
								campoDescricao.getText(), campoQuantidade.getNumber(), campoPreco.getNumber(), campoEstoqueMaximo.getNumber(),
								campoEstoqueMinimo.getNumber(),checkBoxNaoControlarEstoque.isSelected()));
						
						carregarTabela();
						limparcampos();
						
						snackBar = new JFXSnackbar(borderPaneTabela);
			//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						snackBar.show("Produto cadastrado com sucesso", 4000);
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
					snackBar.show("Campos obrigatï¿½rios nï¿½o informado", 4000);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (treeTableViewProduto.getSelectionModel().getSelectedIndex() != -1) {

			idProduto = Integer.parseInt( treeTableViewProduto.getSelectionModel().getSelectedItem().getValue().toString());

			if (!campoDescricao.getText().isEmpty() && comboboxCategoria.getValue() != null
					&& comboboxUnidadeMedida.getValue() != null && !campoPreco.getNumber().equals(BigDecimal.ZERO)) {

				try {
					new ProdutosDao().alterar(new Produto(idProduto, campoCodigoProduto.getText(),
							new Categoria(comboboxCategoria.getValue().getId_categoria(),
									comboboxCategoria.getValue().getNome()),
							new Unidade_medida(comboboxUnidadeMedida.getValue().getId_unidade_medida(),
									comboboxUnidadeMedida.getValue().getNome()),
							campoDescricao.getText(), campoQuantidade.getNumber(), campoPreco.getNumber(), campoEstoqueMaximo.getNumber(),
							campoEstoqueMinimo.getNumber(),checkBoxNaoControlarEstoque.isSelected()));
					carregarTabela();
					limparcampos();
					
					snackBar = new JFXSnackbar(borderPaneTabela);
					String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Produto alterado com sucesso", 4000);
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
				snackBar.show("Campos obrigatï¿½rios nï¿½o informado", 4000);
			}

		}
	}

	void carregarTabela() {

		atualizarTabela();
		// Criando as colunas da tabela
		JFXTreeTableColumn<ProdutoClass, String> colunaId = new JFXTreeTableColumn<>("ID");
		colunaId.setPrefWidth(70);
		JFXTreeTableColumn<ProdutoClass, String> colunaDescricao = new JFXTreeTableColumn<>("Descrição");
		colunaDescricao.setPrefWidth(300);
		JFXTreeTableColumn<ProdutoClass, String> colunaEstoqueDisponivel = new JFXTreeTableColumn<>(
				"Estoque disponível");
		colunaEstoqueDisponivel.setPrefWidth(150);
		JFXTreeTableColumn<ProdutoClass, String> colunaPreco = new JFXTreeTableColumn<>("Preço");
		colunaPreco.setPrefWidth(150);
		JFXTreeTableColumn<ProdutoClass, String> colunaUnidadeMedida = new JFXTreeTableColumn<>("Unidade medida");
		colunaUnidadeMedida.setPrefWidth(120);
		JFXTreeTableColumn<ProdutoClass, String> colunaCategoria = new JFXTreeTableColumn<>("Categoria");
		colunaCategoria.setPrefWidth(145);

		colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProdutoClass, String> param) -> {
			if (colunaId.validateValue(param))
				return param.getValue().getValue().id;
			else
				return colunaId.getComputedValue(param);
		});
		colunaDescricao.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProdutoClass, String> param) -> {
			if (colunaDescricao.validateValue(param))
				return param.getValue().getValue().descricao;
			else
				return colunaDescricao.getComputedValue(param);
		});

		colunaEstoqueDisponivel.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProdutoClass, String> param) -> {
			if (colunaEstoqueDisponivel.validateValue(param))
				return param.getValue().getValue().estoqueDisponivel;
			else
				return colunaEstoqueDisponivel.getComputedValue(param);
		});
		colunaPreco.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProdutoClass, String> param) -> {
			if (colunaPreco.validateValue(param))
				return param.getValue().getValue().preco;
			else
				return colunaPreco.getComputedValue(param);
		});
		colunaUnidadeMedida.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProdutoClass, String> param) -> {
			if (colunaUnidadeMedida.validateValue(param))
				return param.getValue().getValue().unidadeMedida;
			else
				return colunaUnidadeMedida.getComputedValue(param);
		});
		colunaCategoria.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProdutoClass, String> param) -> {
			if (colunaCategoria.validateValue(param))
				return param.getValue().getValue().categoria;
			else
				return colunaCategoria.getComputedValue(param);
		});

		ObservableList<ProdutoClass> lista_produtos = FXCollections.observableArrayList();

		try {
			new ProdutosDao().listar().forEach(produto -> {
				lista_produtos.add(new ProdutoClass(produto.getId_produto(), produto.getDescricao(),
						produto.getEstoque_disponivel(), produto.getPreco_venda(),
						produto.getUnidade_medida().getNome(), produto.getCategoria().getNome()));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		treeTableViewProduto
				.setRoot(new RecursiveTreeItem<ProdutoClass>(lista_produtos, RecursiveTreeObject::getChildren));

		treeTableViewProduto.getColumns().setAll(colunaId, colunaDescricao, colunaEstoqueDisponivel, colunaPreco,
				colunaUnidadeMedida, colunaCategoria);
		treeTableViewProduto.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewProduto
					.setPredicate(person -> person.getValue().id.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().descricao.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().estoqueDisponivel.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().preco.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().categoria.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().unidadeMedida.get().toLowerCase().contains(pesquisa.toLowerCase())

			);
		});
		carregarDados();
	}

	// criando uma classe anonima para ser consumida pela tabela
	class ProdutoClass extends RecursiveTreeObject<ProdutoClass> {

		StringProperty id;
		StringProperty descricao;
		StringProperty estoqueDisponivel;
		StringProperty preco;
		StringProperty unidadeMedida;
		StringProperty categoria;

		public ProdutoClass(int id, String descricao, BigDecimal estoqueDisponivel, BigDecimal preco,
				String unidadeMedida, String categoria) {

			this.id = new SimpleStringProperty(String.valueOf(id));
			this.descricao = new SimpleStringProperty(descricao);
			this.estoqueDisponivel = new SimpleStringProperty(
					String.valueOf(new NumeroTextField(estoqueDisponivel).getText()));
			this.preco = new SimpleStringProperty(String.valueOf(
					new NumeroTextField(preco, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));
			this.unidadeMedida = new SimpleStringProperty(unidadeMedida);
			this.categoria = new SimpleStringProperty(categoria);
		}

		@Override
		public String toString() {
			return String.valueOf(id.getValue());
		}
	}

	void atualizarTabela() {

		try {
			treeTableViewProduto = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewProduto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void limparcampos() {
		campoCodigoProduto.setText("");
		campoDescricao.setText("");
		campoEstoqueMaximo.setNumber(BigDecimal.ZERO);
		campoEstoqueMinimo.setNumber(BigDecimal.ZERO);
		campoPesquisar.setText("");
		campoPreco.setNumber(BigDecimal.ZERO);
		campoQuantidade.setNumber(BigDecimal.ZERO);
		checkBoxNaoControlarEstoque.setSelected(false);
		verificarCheckBox();
		comboboxCategoria.setValue(null);
		comboboxUnidadeMedida.setValue(null); 
	}
	
	public void carregarDados() {
		// TODO Auto-generated method stub
		
		treeTableViewProduto.setOnMouseClicked(event -> {
			if (treeTableViewProduto.getSelectionModel().getSelectedIndex() != -1) {
				idProduto = Integer.parseInt(String.valueOf(
						treeTableViewProduto.getSelectionModel().getSelectedItem().getValue().toString()));
				try {

					new ProdutosDao().listar().forEach(produto -> {

						if (produto.getId_produto() == idProduto) {
							campoCodigoProduto.setText(String.valueOf(produto.getCodigo_produto())); 
							campoDescricao.setText(produto.getDescricao());
							campoEstoqueMaximo.setNumber(produto.getEstoque_max());
							campoEstoqueMinimo.setNumber(produto.getEstoque_min());
							campoPreco.setNumber(produto.getPreco_venda());
							campoQuantidade.setNumber(produto.getEstoque_disponivel());
							checkBoxNaoControlarEstoque.setSelected(produto.isNao_controlar_estoque());
							comboboxCategoria.setValue(produto.getCategoria());
							comboboxUnidadeMedida.setValue(produto.getUnidade_medida());
							verificarCheckBox();

						}
					});
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
	void verificarCheckBox(){
		if(checkBoxNaoControlarEstoque.isSelected()){
			campoEstoqueMaximo.setNumber(BigDecimal.ONE);
			campoEstoqueMinimo.setNumber(BigDecimal.ONE);
			campoQuantidade.setNumber(BigDecimal.ONE);
			
			campoEstoqueMaximo.setEditable(false);
			campoEstoqueMinimo.setEditable(false);
			campoQuantidade.setEditable(false);
		} else {
			campoEstoqueMaximo.setEditable(true);
			campoEstoqueMinimo.setEditable(true);
			campoQuantidade.setEditable(true);
		}
	}
}
