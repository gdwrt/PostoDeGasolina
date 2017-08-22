package com.postoGasolina.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.CaixaDao;
import com.postoGasolina.dao.CompraDao;
import com.postoGasolina.dao.VendaDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.model.Fluxo_caixa2;
import com.postoGasolina.model.Item_pedido;
import com.postoGasolina.model.Pedido_compra;
import com.postoGasolina.model.Pedido_venda;
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
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class TelaGerenciarCaixaController implements Initializable {

	@FXML
	private GridPane gridPaneTop;

	@FXML
	private JFXButton btnAbrirCaixa;

	@FXML
	private JFXButton btnFecharCaixa;

	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	@FXML
	private GridPane gridPaneTop1;

	@FXML
	private JFXButton btnImprimirRelatorioVendas;

	@FXML
	private BorderPane borderPaneTabela2;

	@FXML
	private JFXTextField campoPesquisar2;

	@FXML
	private JFXTabPane tabPanePrincipal;

	@FXML
	private JFXTreeTableView<CaixaClass> treeTableViewCaixa;
	// @FXML
	private JFXTreeTableView<CaixaFClass> treeTableViewCaixaAnteriores;

	@FXML
	private BarChart<String, BigDecimal> GraficoMensal;

	@FXML
	private GridPane gripaneBottom;

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	private NumeroTextField campoTotalVenda = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	static final JFXTextField atualizaG = new JFXTextField("0");
	
	private JFXSnackbar snackBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		carregarComponents();
		carregarGrafico();

	}
	@FXML
    void tabPrincipal(MouseEvent event) {
    		System.out.println("entrou");
		if (atualizaG.getText().equals("1")) {
			GraficoMensal.getData().clear();
			carregarGrafico();
			carregarTabela();
			carregarTabela2();
			atualizaG.setText("0");
		}
    }
	private void carregarGrafico() {

		// TODO Auto-generated method stub
		XYChart.Series<String, BigDecimal> set1 = new XYChart.Series<>();
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Janeiro", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Fevereiro", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Março", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Abril", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Maio", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Junho", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Julho", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Agosto", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Setembro", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Outubro", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Novembro", BigDecimal.ZERO));
		set1.getData().add(new XYChart.Data<String, BigDecimal>("Dezembro", BigDecimal.ZERO));

		try {
			for (Fluxo_caixa2 caixa : new CaixaDao().listar()) {

				if (caixa.getData_hora_final() != null) {
					if (caixa.getData_hora_final().getYear() == LocalDate.now().getYear()) {

						if (caixa.getData_hora_final().getMonthValue() == 1) {
							set1.getData().get(0)
									.setYValue((set1.getData().get(0).getYValue().add(caixa.getSaldo_final()))); // janeiro
						} else if (caixa.getData_hora_final().getMonthValue() == 2) {
							set1.getData().get(1)
									.setYValue((set1.getData().get(1).getYValue().add(caixa.getSaldo_final()))); // fevereiro
						} else if (caixa.getData_hora_final().getMonthValue() == 3) {
							set1.getData().get(2)
									.setYValue((set1.getData().get(2).getYValue().add(caixa.getSaldo_final()))); // março
						} else if (caixa.getData_hora_final().getMonthValue() == 4) {
							set1.getData().get(3)
									.setYValue((set1.getData().get(3).getYValue().add(caixa.getSaldo_final()))); // abril
						} else if (caixa.getData_hora_final().getMonthValue() == 5) {
							set1.getData().get(4)
									.setYValue((set1.getData().get(4).getYValue().add(caixa.getSaldo_final()))); // maio
						} else if (caixa.getData_hora_final().getMonthValue() == 6) {
							set1.getData().get(5)
									.setYValue(set1.getData().get(5).getYValue().add(caixa.getSaldo_final())); // junho
						} else if (caixa.getData_hora_final().getMonthValue() == 7) {
							set1.getData().get(6)
									.setYValue((set1.getData().get(6).getYValue().add(caixa.getSaldo_final()))); // julho
						} else if (caixa.getData_hora_final().getMonthValue() == 8) {
							set1.getData().get(7)
									.setYValue((set1.getData().get(7).getYValue().add(caixa.getSaldo_final()))); // agosto
						} else if (caixa.getData_hora_final().getMonthValue() == 9) {
							set1.getData().get(8)
									.setYValue((set1.getData().get(8).getYValue().add(caixa.getSaldo_final()))); // setembro
						} else if (caixa.getData_hora_final().getMonthValue() == 10) {
							set1.getData().get(9)
									.setYValue((set1.getData().get(9).getYValue().add(caixa.getSaldo_final()))); // outubro
						} else if (caixa.getData_hora_final().getMonthValue() == 11) {
							set1.getData().get(10)
									.setYValue((set1.getData().get(10).getYValue().add(caixa.getSaldo_final()))); // novembro
						} else if (caixa.getData_hora_final().getMonthValue() == 12) {
							set1.getData().get(11)
									.setYValue((set1.getData().get(11).getYValue().add(caixa.getSaldo_final()))); // dezembro
						}
					}
				}

			}

			GraficoMensal.getData().addAll(set1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btnAbrirCaixa(ActionEvent event) {
		if (Fluxo_caixa.getStatus().equals("Fechado")) {
			new Main().carregarTelaAbrirCaixa();
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Já possui caixa aberto", 4000);
		
		}
	}

	@FXML
	void btnFecharCaixa(ActionEvent event) {
		if (Fluxo_caixa.getStatus().equals("Aberto")) {
			new Main().carregarTelaFecharCaixa();
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
	//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Não possui caixa aberto", 4000);
		
		}

	}

	@FXML
	void btnImprimirRelatorioVendas(ActionEvent event) {
		try {
			OutputStream file = new FileOutputStream(new File("RelatorioVendas.pdf"));

			Document document = new Document();
			PdfWriter.getInstance(document, file);

			document.open();

			// parágrafo
			Paragraph paragraph = new Paragraph();

			// nova linha
			paragraph.add(new Paragraph(" "));
			paragraph.add(new Paragraph("                    RELATÓRIO DE CAIXAS ENCERRADOS",
					new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
			paragraph.add(new Paragraph(
					"                                        "
							+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
					new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
			paragraph.add(new Paragraph(" "));
			document.add(paragraph);

			// cria tabela e carrega registros
			PdfPTable pdfTable = new PdfPTable(4);
			PdfPCell cell1 = new PdfPCell(new Phrase("Abertura"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfTable.addCell(cell1);

			cell1 = new PdfPCell(new Phrase("Fechamento"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfTable.addCell(cell1);

			cell1 = new PdfPCell(new Phrase("Saldo Inicial"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfTable.addCell(cell1);

			cell1 = new PdfPCell(new Phrase("Saldo Final"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfTable.addCell(cell1);

			pdfTable.setHeaderRows(1);

			BigDecimal total = BigDecimal.ZERO;

			for (Fluxo_caixa2 caixa : new CaixaDao().listar()) {
				if (caixa.getData_hora_final() != null) {
					pdfTable.addCell(
							caixa.getData_hora_inicial().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
					pdfTable.addCell(
							caixa.getData_hora_final().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
					pdfTable.addCell(String.valueOf(new NumeroTextField(caixa.getSaldo_atual(),
							NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));

					pdfTable.addCell(String.valueOf(new NumeroTextField(caixa.getSaldo_final(),
							NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));

					total = total.add(caixa.getSaldo_final());
				}
			}

			pdfTable.addCell("");
			pdfTable.addCell("");
			pdfTable.addCell("TOTAL ");
			pdfTable.addCell(String.valueOf(
					new NumeroTextField(total, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));

			document.add(pdfTable);

			document.close();
			file.close();

			Desktop.getDesktop().open(new File("RelatorioVendas.pdf"));
			
			snackBar = new JFXSnackbar(borderPaneTabela);
	//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Relatório gerado com sucesso", 4000); 

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	void carregarComponents() {
		try {
			carregarTabela();
			carregarTabela2();

			String style = getClass().getResource("/com/postoGasolina/style/TelaVenda.css").toExternalForm();

			campoTotalVenda.getStylesheets().add(style);
			campoTotalVenda.getStyleClass().add("format-campo4");
			campoTotalVenda.setUnFocusColor(Color.TRANSPARENT);
			campoTotalVenda.setFocusColor(Color.TRANSPARENT);
			campoTotalVenda.setAlignment(Pos.CENTER_RIGHT);
			campoTotalVenda.setEditable(false);
			campoTotalVenda.setFocusTraversable(false);
			gripaneBottom.add(campoTotalVenda, 1, 1);
			gripaneBottom.setMargin(campoTotalVenda, new Insets(15, 60, 0, 0));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	void carregarTabela() {

		atualizarTabela();
		// Criando as colunas da tabela
		JFXTreeTableColumn<CaixaClass, String> colunaFuncionario = new JFXTreeTableColumn<>("Funcionário");
		colunaFuncionario.setPrefWidth(150);
		JFXTreeTableColumn<CaixaClass, String> colunaProduto = new JFXTreeTableColumn<>("Produto");
		colunaProduto.setPrefWidth(230);
		JFXTreeTableColumn<CaixaClass, String> colunaPreco = new JFXTreeTableColumn<>("Preço");
		colunaPreco.setPrefWidth(110);
		JFXTreeTableColumn<CaixaClass, String> colunaTipo = new JFXTreeTableColumn<>("Tipo(C/V)");
		colunaTipo.setPrefWidth(105);
		JFXTreeTableColumn<CaixaClass, String> colunaCliente = new JFXTreeTableColumn<>("Cliente");
		colunaCliente.setPrefWidth(150);
		JFXTreeTableColumn<CaixaClass, String> colunaQunatidade = new JFXTreeTableColumn<>("Quantidade");
		colunaQunatidade.setPrefWidth(84);
		JFXTreeTableColumn<CaixaClass, String> colunaValorTotal = new JFXTreeTableColumn<>("Valor total");
		colunaValorTotal.setPrefWidth(110);

		colunaFuncionario.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaClass, String> param) -> {
			if (colunaFuncionario.validateValue(param))
				return param.getValue().getValue().funcionario;
			else
				return colunaFuncionario.getComputedValue(param);
		});
		colunaProduto.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaClass, String> param) -> {
			if (colunaProduto.validateValue(param))
				return param.getValue().getValue().produto;
			else
				return colunaProduto.getComputedValue(param);
		});
		colunaPreco.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaClass, String> param) -> {
			if (colunaPreco.validateValue(param))
				return param.getValue().getValue().preco;
			else
				return colunaTipo.getComputedValue(param);
		});
		colunaTipo.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaClass, String> param) -> {
			if (colunaTipo.validateValue(param))
				return param.getValue().getValue().tipo;
			else
				return colunaTipo.getComputedValue(param);
		});
		colunaCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaClass, String> param) -> {
			if (colunaCliente.validateValue(param))
				return param.getValue().getValue().cliente;
			else
				return colunaCliente.getComputedValue(param);
		});
		colunaQunatidade.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaClass, String> param) -> {
			if (colunaQunatidade.validateValue(param))
				return param.getValue().getValue().quantidade;
			else
				return colunaQunatidade.getComputedValue(param);
		});
		colunaValorTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaClass, String> param) -> {
			if (colunaValorTotal.validateValue(param))
				return param.getValue().getValue().valorTotal;
			else
				return colunaValorTotal.getComputedValue(param);
		});

		ObservableList<CaixaClass> lista_produtos = FXCollections.observableArrayList();

		try {

			if (Fluxo_caixa.getStatus().equals("Aberto")) {
				for (Pedido_venda pedido : new VendaDao().pesquisar(Fluxo_caixa.getId_fluxo_caixa())) {
					for (Item_pedido item : pedido.getItens_pedido()) {
						if (item.getTipo_produto().equals("combustivel")) {
							lista_produtos.add(new CaixaClass(pedido.getFuncionario().getPessoa().getNome(),
									item.getProduto_loja().getCombustivel().getTipoCombustivel().getNome(), "Venda",
									item.getPreco_unitario(),

									pedido.getCliente().getTipoCliente().equals("cliente_fisica")
											? pedido.getCliente().getCliente_fisica().getPessoa().getNome()
											: pedido.getCliente().getTipoCliente().equals("cliente_juridica")
													? pedido.getCliente().getCliente_juridica().getNome() : "Anônimo",
									item.getQuantidade(), item.getTotal()));
						} else if (item.getTipo_produto().equals("produto")) {
							lista_produtos.add(new CaixaClass(pedido.getFuncionario().getPessoa().getNome(),
									item.getProduto_loja().getProduto().getDescricao(), "Venda",
									item.getPreco_unitario(),
									pedido.getCliente().getTipoCliente().equals("cliente_fisica")
											? pedido.getCliente().getCliente_fisica().getPessoa().getNome()
											: pedido.getCliente().getTipoCliente().equals("cliente_juridica")
													? pedido.getCliente().getCliente_juridica().getNome() : "Anônimo",
									item.getQuantidade(), item.getTotal()));
						}

						// adicionando no total
						campoTotalVenda.setNumber(campoTotalVenda.getNumber().add(item.getTotal()));
					}

				}
				for (Pedido_compra pedido : new CompraDao().pesquisar(Fluxo_caixa.getId_fluxo_caixa())) {
					for (Item_pedido item : pedido.getItens_pedido()) {
						if (item.getTipo_produto().equals("combustivel")) {
							lista_produtos.add(new CaixaClass(pedido.getNome_responsavel(),
									item.getProduto_loja().getCombustivel().getTipoCombustivel().getNome(), "Compra",
									item.getPreco_unitario(), "Posto", item.getQuantidade(), item.getTotal()));
						} else if (item.getTipo_produto().equals("produto")) {
							lista_produtos.add(new CaixaClass(pedido.getFornecedor().getRazao_social(),
									item.getProduto_loja().getProduto().getDescricao(), "Compra",
									item.getPreco_unitario(), "Posto", item.getQuantidade(), item.getTotal()));
						}

						// adicionando no total
						campoTotalVenda.setNumber(campoTotalVenda.getNumber().add(item.getTotal()));
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		treeTableViewCaixa.setRoot(new RecursiveTreeItem<CaixaClass>(lista_produtos, RecursiveTreeObject::getChildren));

		treeTableViewCaixa.getColumns().setAll(colunaFuncionario, colunaProduto, colunaPreco, colunaTipo, colunaCliente,
				colunaQunatidade, colunaValorTotal);
		treeTableViewCaixa.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewCaixa.setPredicate(
					person -> person.getValue().funcionario.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().produto.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().preco.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().tipo.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().quantidade.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().valorTotal.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().cliente.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
	}

	// criando uma classe anonima para ser consumida pela tabela
	class CaixaClass extends RecursiveTreeObject<CaixaClass> {

		StringProperty funcionario;
		StringProperty produto;
		StringProperty preco;
		StringProperty tipo;
		StringProperty cliente;
		StringProperty quantidade;
		StringProperty valorTotal;

		@Override
		public String toString() {
			return String.valueOf(funcionario.getValue());
		}

		public CaixaClass(String funcionario, String produto, String tipo, BigDecimal preco, String cliente,
				BigDecimal qtd, BigDecimal valorTotal) {

			this.funcionario = new SimpleStringProperty(String.valueOf(funcionario));
			this.produto = new SimpleStringProperty(produto);
			this.tipo = new SimpleStringProperty(String.valueOf(tipo));
			this.preco = new SimpleStringProperty(String.valueOf(
					new NumeroTextField(preco, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));
			this.cliente = new SimpleStringProperty(cliente);
			this.quantidade = new SimpleStringProperty(String.valueOf(new NumeroTextField(qtd).getText()));
			this.valorTotal = new SimpleStringProperty(String
					.valueOf(new NumeroTextField(valorTotal, NumberFormat.getCurrencyInstance(new Locale("pt", "BR")))
							.getText()));

		}

	}

	void atualizarTabela() {

		try {
			treeTableViewCaixa = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewCaixa);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void carregarTabela2() {

		atualizarTabela2();

		// Criando as colunas da tabela
		JFXTreeTableColumn<CaixaFClass, String> colunaAbertura = new JFXTreeTableColumn<>("Abertura");
		colunaAbertura.setPrefWidth(250);
		JFXTreeTableColumn<CaixaFClass, String> colunaFechamento = new JFXTreeTableColumn<>("Fechamento");
		colunaFechamento.setPrefWidth(250);
		JFXTreeTableColumn<CaixaFClass, String> colunaSaldoInicial = new JFXTreeTableColumn<>("Saldo Inicial");
		colunaSaldoInicial.setPrefWidth(215);
		JFXTreeTableColumn<CaixaFClass, String> colunaSaldoTVendas = new JFXTreeTableColumn<>("Saldo Final");
		colunaSaldoTVendas.setPrefWidth(215);

		colunaAbertura.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaFClass, String> param) -> {
			if (colunaAbertura.validateValue(param))
				return param.getValue().getValue().colunaAbertura;
			else
				return colunaAbertura.getComputedValue(param);
		});
		colunaFechamento.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaFClass, String> param) -> {
			if (colunaFechamento.validateValue(param))
				return param.getValue().getValue().colunaFechamento;
			else
				return colunaFechamento.getComputedValue(param);
		});
		colunaSaldoInicial.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaFClass, String> param) -> {
			if (colunaSaldoInicial.validateValue(param))
				return param.getValue().getValue().colunaSaldoInicial;
			else
				return colunaSaldoInicial.getComputedValue(param);
		});
		colunaSaldoTVendas.setCellValueFactory((TreeTableColumn.CellDataFeatures<CaixaFClass, String> param) -> {
			if (colunaSaldoTVendas.validateValue(param))
				return param.getValue().getValue().ColunaSaldofinal;
			else
				return colunaSaldoTVendas.getComputedValue(param);
		});

		ObservableList<CaixaFClass> lista_caixas = FXCollections.observableArrayList();

		// carregando registros com os campos da coluna da classe anônima
		try {
			ObservableList<Fluxo_caixa2> listaCaixasDao = new CaixaDao().listar();

			//ORDENANDO..
			listaCaixasDao.sort(new Comparator<Fluxo_caixa2>() {

				@Override
				public int compare(Fluxo_caixa2 la1, Fluxo_caixa2 la2) {
					if (la1.getData_hora_inicial().isBefore(la2.getData_hora_inicial())) {
						return -1;
					}
					if (la1.getData_hora_inicial().isAfter(la2.getData_hora_inicial())) {
						return 1;
					}

					return 0;
				}
			});
			
			//
			for (Fluxo_caixa2 caixa : listaCaixasDao) {

				if (caixa.getData_hora_final() != null) {
					lista_caixas.add(new CaixaFClass("", "", caixa.getData_hora_inicial(), caixa.getData_hora_final(),
							caixa.getSaldo_atual(), caixa.getSaldo_final()));
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treeTableViewCaixaAnteriores
				.setRoot(new RecursiveTreeItem<CaixaFClass>(lista_caixas, RecursiveTreeObject::getChildren));

		treeTableViewCaixaAnteriores.getColumns().setAll(colunaAbertura, colunaFechamento, colunaSaldoInicial,
				colunaSaldoTVendas);
		treeTableViewCaixaAnteriores.setShowRoot(false);

		campoPesquisar2.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewCaixaAnteriores.setPredicate(person -> person.getValue().funcionarioAbriuCaixa.get()
					.toLowerCase().contains(pesquisa.toLowerCase())
					|| person.getValue().funcionarioFechouCaixa.get().toLowerCase().contains(pesquisa.toLowerCase())
					|| person.getValue().colunaAbertura.get().toLowerCase().contains(pesquisa.toLowerCase())
					|| person.getValue().colunaFechamento.get().toLowerCase().contains(pesquisa.toLowerCase())
					|| person.getValue().colunaSaldoInicial.get().toLowerCase().contains(pesquisa.toLowerCase())
					|| person.getValue().ColunaSaldofinal.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
	}

	// criando uma classe anonima para ser consumida pela tabela
	class CaixaFClass extends RecursiveTreeObject<CaixaFClass> {

		StringProperty funcionarioAbriuCaixa;
		StringProperty funcionarioFechouCaixa;
		StringProperty colunaAbertura;
		StringProperty colunaFechamento;
		StringProperty colunaSaldoInicial;
		StringProperty ColunaSaldofinal;

		public CaixaFClass(String funcionarioAbriu, String funcionarioFechou, LocalDateTime colunaAbertura,
				LocalDateTime fechamento, BigDecimal saldoInicial, BigDecimal saldoFinal) {

			this.funcionarioAbriuCaixa = new SimpleStringProperty(funcionarioAbriu);
			this.funcionarioFechouCaixa = new SimpleStringProperty(funcionarioFechou);
			this.colunaAbertura = new SimpleStringProperty(
					colunaAbertura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			this.colunaFechamento = new SimpleStringProperty(
					fechamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			this.colunaSaldoInicial = new SimpleStringProperty(String
					.valueOf(new NumeroTextField(saldoInicial, NumberFormat.getCurrencyInstance(new Locale("pt", "BR")))
							.getText()));
			this.ColunaSaldofinal = new SimpleStringProperty(String
					.valueOf(new NumeroTextField(saldoFinal, NumberFormat.getCurrencyInstance(new Locale("pt", "BR")))
							.getText()));
		}

	}

	void atualizarTabela2() {

		try {
			treeTableViewCaixaAnteriores = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela2.setCenter(treeTableViewCaixaAnteriores);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
