package com.postoGasolina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.dao.ClienteFisicaDao;
import com.postoGasolina.dao.ClienteJuridicaDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Cliente_Gasto;
import com.postoGasolina.util.NumeroTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class TelaGerenciarFidelizacaoClientesController implements Initializable {
	@FXML
	private BorderPane borderPaneTabela;

	@FXML
	private JFXTextField campoPesquisar;

	@FXML
	private GridPane gridPaneTop1;

	@FXML
	private JFXButton btnEnviarEmail;

	@FXML
	private JFXButton btnGerarCupom;

	@FXML
	private JFXRadioButton rbDeHoje;

	@FXML
	private JFXRadioButton rbDessaSemana;

	@FXML
	private JFXRadioButton rbDesseMes;

	@FXML
	private JFXRadioButton rbAcimaMill;

	@FXML
	private JFXRadioButton rbAcimaCincoMil;

	@FXML
	private JFXRadioButton rbAcimaDezMill;

	@FXML
	private JFXTreeTableView<FidelizacaoClass> treeTableViewFidelizacao;

	static final JFXTextField nomeCliente = new JFXTextField();
	static final JFXTextField emailCliente = new JFXTextField();
	
	private JFXSnackbar snackBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		carregarComponents();
		carregarTabela();
	}

	private void carregarComponents() {
		// TODO Auto-generated method stub
		rbDeHoje.setSelectedColor(Color.valueOf("#eb901a"));
		rbDeHoje.setUnSelectedColor(Color.WHITE);

		rbDessaSemana.setSelectedColor(Color.valueOf("#eb901a"));
		rbDesseMes.setSelectedColor(Color.valueOf("#eb901a"));
		rbAcimaCincoMil.setSelectedColor(Color.valueOf("#eb901a"));
		rbAcimaDezMill.setSelectedColor(Color.valueOf("#eb901a"));
		rbAcimaMill.setSelectedColor(Color.valueOf("#eb901a"));

		rbDessaSemana.setUnSelectedColor(Color.WHITE);
		rbDesseMes.setUnSelectedColor(Color.WHITE);
		rbAcimaCincoMil.setUnSelectedColor(Color.WHITE);
		rbAcimaDezMill.setUnSelectedColor(Color.WHITE);
		rbAcimaMill.setUnSelectedColor(Color.WHITE);

		rbDeHoje.setOnAction(e -> {

			rbDessaSemana.setSelected(false);
			rbDesseMes.setSelected(false);
			rbAcimaCincoMil.setSelected(false);
			rbAcimaDezMill.setSelected(false);
			rbAcimaMill.setSelected(false);
			if (rbDeHoje.isSelected()) {
				ObservableList<FidelizacaoClass> lista_clientes = FXCollections.observableArrayList();

				try {
					ObservableList<Cliente_Gasto> lista_gasto = new ClienteFisicaDao().listarGasto();

					new ClienteJuridicaDao().listarGasto().forEach(cliente -> {
						lista_gasto.add(new Cliente_Gasto(cliente.getId(), cliente.getCliente(),
								cliente.getGastoMensal(), cliente.getGastoAnual(), cliente.getGastoTotal(), null,
								"cliente_juridica", cliente.getEmail()));
					});
					;

					// Ordenando
					lista_gasto.sort(new Comparator<Cliente_Gasto>() {

						@Override
						public int compare(Cliente_Gasto cliente1, Cliente_Gasto cliente2) {
							// TODO Auto-generated method stub
							if (cliente1.getDataNascimento() != null
									&& cliente1.getDataNascimento().isBefore(cliente1.getDataNascimento()))
								return 1;
							if (cliente1.getDataNascimento() != null
									&& cliente1.getDataNascimento().isAfter(cliente1.getDataNascimento()))
								return -1;

							return 0;
						}
					});

					// Ordenado
					for (Cliente_Gasto cliente : lista_gasto) {
						if (cliente.getDataNascimento() != null
								&& LocalDate.now().getDayOfMonth() == cliente.getDataNascimento().getDayOfMonth()
								&& LocalDate.now().getMonthValue() == cliente.getDataNascimento().getMonthValue()) {
							lista_clientes.add(new FidelizacaoClass(cliente.getCliente(), cliente.getGastoMensal(),
									cliente.getGastoAnual(), cliente.getGastoTotal(),
									cliente.getDataNascimento() != null ? cliente.getDataNascimento()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
											: cliente.getTipo_cliente().equals("cliente_juridica") ? "Não possui"
													: "Cadastro Incompleto",
									cliente.getTipo_cliente(), cliente.getEmail()));
						}
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				treeTableViewFidelizacao.setRoot(
						new RecursiveTreeItem<FidelizacaoClass>(lista_clientes, RecursiveTreeObject::getChildren));

			} else {
				carregarTabela();
			}

		});
		rbDessaSemana.setOnAction(e -> {
			rbDeHoje.setSelected(false);
			rbDesseMes.setSelected(false);
			rbAcimaCincoMil.setSelected(false);
			rbAcimaDezMill.setSelected(false);
			rbAcimaMill.setSelected(false);

			if (rbDessaSemana.isSelected()) {
				ObservableList<FidelizacaoClass> lista_clientes = FXCollections.observableArrayList();

				try {
					ObservableList<Cliente_Gasto> lista_gasto = new ClienteFisicaDao().listarGasto();

					new ClienteJuridicaDao().listarGasto().forEach(cliente -> {
						lista_gasto.add(new Cliente_Gasto(cliente.getId(), cliente.getCliente(),
								cliente.getGastoMensal(), cliente.getGastoAnual(), cliente.getGastoTotal(), null,
								"cliente_juridica", cliente.getEmail()));
					});

					// Ordenando
					lista_gasto.sort(new Comparator<Cliente_Gasto>() {

						@Override
						public int compare(Cliente_Gasto cliente1, Cliente_Gasto cliente2) {
							// TODO Auto-generated method stub
							if (cliente1.getDataNascimento() != null
									&& cliente1.getDataNascimento().isBefore(cliente1.getDataNascimento()))
								return 1;
							if (cliente1.getDataNascimento() != null
									&& cliente1.getDataNascimento().isAfter(cliente1.getDataNascimento()))
								return -1;

							return 0;
						}
					});
					int dia = LocalDate.now().getDayOfMonth();
					int contMax = 0;
					if (LocalDate.now().getDayOfWeek().name().equals("SUNDAY")) {
						contMax = dia + 6;
					}
					if (LocalDate.now().getDayOfWeek().name().equals("MONDAY")) {
						contMax = dia + 5;
					}
					if (LocalDate.now().getDayOfWeek().name().equals("TUESDAY")) {
						contMax = dia + 4;
					}
					if (LocalDate.now().getDayOfWeek().name().equals("WEDNESDAY")) {
						contMax = dia + 3;
					}
					if (LocalDate.now().getDayOfWeek().name().equals("THURSDAY")) {
						contMax = dia + 2;
					}
					if (LocalDate.now().getDayOfWeek().name().equals("FRIDAY")) {
						contMax = dia + 1;
					}
					if (LocalDate.now().getDayOfWeek().name().equals("SATURDAY")) {
						contMax = dia;
					}

					// Ordenado
					for (Cliente_Gasto cliente : lista_gasto) {
						for (int i = dia; i <= contMax; ++i) {
							if (cliente.getDataNascimento() != null
									&& i == cliente.getDataNascimento().getDayOfMonth()) {
								lista_clientes.add(new FidelizacaoClass(cliente.getCliente(), cliente.getGastoMensal(),
										cliente.getGastoAnual(), cliente.getGastoTotal(),
										cliente.getDataNascimento() != null ? cliente.getDataNascimento()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
												: cliente.getTipo_cliente().equals("cliente_juridica") ? "Não possui"
														: "Cadastro Incompleto",
										cliente.getTipo_cliente(), cliente.getEmail()));

								System.out.println("dia da semana" + dia);
								System.out.println(cliente.getDataNascimento().getDayOfWeek().getValue());
							}
						}

					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				treeTableViewFidelizacao.setRoot(
						new RecursiveTreeItem<FidelizacaoClass>(lista_clientes, RecursiveTreeObject::getChildren));

			} else {
				carregarTabela();
			}

		});
		rbDesseMes.setOnAction(e -> {
			rbDessaSemana.setSelected(false);
			rbDeHoje.setSelected(false);
			rbAcimaCincoMil.setSelected(false);
			rbAcimaDezMill.setSelected(false);
			rbAcimaMill.setSelected(false);

			if (rbDesseMes.isSelected()) {
				ObservableList<FidelizacaoClass> lista_clientes = FXCollections.observableArrayList();

				try {
					ObservableList<Cliente_Gasto> lista_gasto = new ClienteFisicaDao().listarGasto();

					new ClienteJuridicaDao().listarGasto().forEach(cliente -> {
						lista_gasto.add(new Cliente_Gasto(cliente.getId(), cliente.getCliente(),
								cliente.getGastoMensal(), cliente.getGastoAnual(), cliente.getGastoTotal(), null,
								"cliente_juridica", cliente.getEmail()));
					});
					;

					// Ordenando
					lista_gasto.sort(new Comparator<Cliente_Gasto>() {

						@Override
						public int compare(Cliente_Gasto cliente1, Cliente_Gasto cliente2) {
							// TODO Auto-generated method stub
							if (cliente1.getDataNascimento() != null
									&& cliente1.getDataNascimento().isBefore(cliente1.getDataNascimento()))
								return 1;
							if (cliente1.getDataNascimento() != null
									&& cliente1.getDataNascimento().isAfter(cliente1.getDataNascimento()))
								return -1;

							return 0;
						}
					});

					// Ordenado
					for (Cliente_Gasto cliente : lista_gasto) {
						if (cliente.getDataNascimento() != null
								&& LocalDate.now().getMonth() == cliente.getDataNascimento().getMonth()) {
							lista_clientes.add(new FidelizacaoClass(cliente.getCliente(), cliente.getGastoMensal(),
									cliente.getGastoAnual(), cliente.getGastoTotal(),
									cliente.getDataNascimento() != null ? cliente.getDataNascimento()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
											: cliente.getTipo_cliente().equals("cliente_juridica") ? "Não possui"
													: "Cadastro Incompleto",
									cliente.getTipo_cliente(), cliente.getEmail()));
						}
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				treeTableViewFidelizacao.setRoot(
						new RecursiveTreeItem<FidelizacaoClass>(lista_clientes, RecursiveTreeObject::getChildren));

			} else {
				carregarTabela();
			}

		});
		rbAcimaCincoMil.setOnAction(e -> {
			rbDessaSemana.setSelected(false);
			rbDesseMes.setSelected(false);
			rbDeHoje.setSelected(false);
			rbAcimaDezMill.setSelected(false);
			rbAcimaMill.setSelected(false);

			if (rbAcimaCincoMil.isSelected()) {
				ObservableList<FidelizacaoClass> lista_clientes = FXCollections.observableArrayList();

				try {
					ObservableList<Cliente_Gasto> lista_gasto = new ClienteFisicaDao().listarGasto();

					new ClienteJuridicaDao().listarGasto().forEach(cliente -> {
						lista_gasto.add(new Cliente_Gasto(cliente.getId(), cliente.getCliente(),
								cliente.getGastoMensal(), cliente.getGastoAnual(), cliente.getGastoTotal(), null,
								"cliente_juridica", cliente.getEmail()));
					});
					;

					// Ordenando
					lista_gasto.sort(new Comparator<Cliente_Gasto>() {

						@Override
						public int compare(Cliente_Gasto cliente1, Cliente_Gasto cliente2) {
							// TODO Auto-generated method stub
							if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == -1)
								return 1;
							if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == 1)
								return -1;

							return 0;
						}
					});

					// Ordenado
					for (Cliente_Gasto cliente : lista_gasto) {
						if (cliente.getGastoMensal().compareTo(new BigDecimal("5000")) == 1) {
							lista_clientes.add(new FidelizacaoClass(cliente.getCliente(), cliente.getGastoMensal(),
									cliente.getGastoAnual(), cliente.getGastoTotal(),
									cliente.getDataNascimento() != null ? cliente.getDataNascimento()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
											: cliente.getTipo_cliente().equals("cliente_juridica") ? "Não possui"
													: "Cadastro Incompleto",
									cliente.getTipo_cliente(), cliente.getEmail()));
						}
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				treeTableViewFidelizacao.setRoot(
						new RecursiveTreeItem<FidelizacaoClass>(lista_clientes, RecursiveTreeObject::getChildren));

			} else {
				carregarTabela();
			}

		});
		rbAcimaDezMill.setOnAction(e -> {
			rbDessaSemana.setSelected(false);
			rbDesseMes.setSelected(false);
			rbAcimaCincoMil.setSelected(false);
			rbDeHoje.setSelected(false);
			rbAcimaMill.setSelected(false);

			if (rbAcimaDezMill.isSelected()) {
				ObservableList<FidelizacaoClass> lista_clientes = FXCollections.observableArrayList();

				try {
					ObservableList<Cliente_Gasto> lista_gasto = new ClienteFisicaDao().listarGasto();

					new ClienteJuridicaDao().listarGasto().forEach(cliente -> {
						lista_gasto.add(new Cliente_Gasto(cliente.getId(), cliente.getCliente(),
								cliente.getGastoMensal(), cliente.getGastoAnual(), cliente.getGastoTotal(), null,
								"cliente_juridica", cliente.getEmail()));
					});
					;

					// Ordenando
					lista_gasto.sort(new Comparator<Cliente_Gasto>() {

						@Override
						public int compare(Cliente_Gasto cliente1, Cliente_Gasto cliente2) {
							// TODO Auto-generated method stub
							if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == -1)
								return 1;
							if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == 1)
								return -1;

							return 0;
						}
					});

					// Ordenado
					for (Cliente_Gasto cliente : lista_gasto) {
						if (cliente.getGastoMensal().compareTo(new BigDecimal("10000")) == 1) {
							lista_clientes.add(new FidelizacaoClass(cliente.getCliente(), cliente.getGastoMensal(),
									cliente.getGastoAnual(), cliente.getGastoTotal(),
									cliente.getDataNascimento() != null ? cliente.getDataNascimento()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
											: cliente.getTipo_cliente().equals("cliente_juridica") ? "Não possui"
													: "Cadastro Incompleto",
									cliente.getTipo_cliente(), cliente.getEmail()));
						}
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				treeTableViewFidelizacao.setRoot(
						new RecursiveTreeItem<FidelizacaoClass>(lista_clientes, RecursiveTreeObject::getChildren));

			} else {
				carregarTabela();
			}
		});
		rbAcimaMill.setOnAction(e -> {
			rbDessaSemana.setSelected(false);
			rbDesseMes.setSelected(false);
			rbAcimaCincoMil.setSelected(false);
			rbAcimaDezMill.setSelected(false);
			rbDeHoje.setSelected(false);

			if (rbAcimaMill.isSelected()) {
				ObservableList<FidelizacaoClass> lista_clientes = FXCollections.observableArrayList();

				try {
					ObservableList<Cliente_Gasto> lista_gasto = new ClienteFisicaDao().listarGasto();

					new ClienteJuridicaDao().listarGasto().forEach(cliente -> {
						lista_gasto.add(new Cliente_Gasto(cliente.getId(), cliente.getCliente(),
								cliente.getGastoMensal(), cliente.getGastoAnual(), cliente.getGastoTotal(), null,
								"cliente_juridica", cliente.getEmail()));
					});
					;

					// Ordenando
					lista_gasto.sort(new Comparator<Cliente_Gasto>() {

						@Override
						public int compare(Cliente_Gasto cliente1, Cliente_Gasto cliente2) {
							// TODO Auto-generated method stub
							if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == -1)
								return 1;
							if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == 1)
								return -1;

							return 0;
						}
					});

					// Ordenado
					for (Cliente_Gasto cliente : lista_gasto) {
						if (cliente.getGastoMensal().compareTo(new BigDecimal("1000")) == 1) {
							lista_clientes.add(new FidelizacaoClass(cliente.getCliente(), cliente.getGastoMensal(),
									cliente.getGastoAnual(), cliente.getGastoTotal(),
									cliente.getDataNascimento() != null ? cliente.getDataNascimento()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
											: cliente.getTipo_cliente().equals("cliente_juridica") ? "Não possui"
													: "Cadastro Incompleto",
									cliente.getTipo_cliente(), cliente.getEmail()));
						}
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				treeTableViewFidelizacao.setRoot(
						new RecursiveTreeItem<FidelizacaoClass>(lista_clientes, RecursiveTreeObject::getChildren));

			} else {
				carregarTabela();
			}
		});

	}

	void carregarTabela() {

		atualizarTabela();
		// Criando as colunas da tabela
		JFXTreeTableColumn<FidelizacaoClass, String> colunaCliente = new JFXTreeTableColumn<>("Cliente");
		colunaCliente.setPrefWidth(250);
		JFXTreeTableColumn<FidelizacaoClass, String> colunaGastoMensal = new JFXTreeTableColumn<>("Gasto Mensal");
		colunaGastoMensal.setPrefWidth(180);
		JFXTreeTableColumn<FidelizacaoClass, String> colunaGastoAnual = new JFXTreeTableColumn<>("Gasto Anual");
		colunaGastoAnual.setPrefWidth(160);
		JFXTreeTableColumn<FidelizacaoClass, String> colunaGastoTotal = new JFXTreeTableColumn<>("Gasto Total");
		colunaGastoTotal.setPrefWidth(160);
		JFXTreeTableColumn<FidelizacaoClass, String> colunaDataNascimento = new JFXTreeTableColumn<>(
				"Data de Nascimento");
		colunaDataNascimento.setPrefWidth(170);

		colunaCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<FidelizacaoClass, String> param) -> {
			if (colunaCliente.validateValue(param))
				return param.getValue().getValue().cliente;
			else
				return colunaCliente.getComputedValue(param);
		});
		colunaGastoMensal.setCellValueFactory((TreeTableColumn.CellDataFeatures<FidelizacaoClass, String> param) -> {
			if (colunaGastoMensal.validateValue(param))
				return param.getValue().getValue().gastoMensal;
			else
				return colunaGastoMensal.getComputedValue(param);
		});
		colunaGastoAnual.setCellValueFactory((TreeTableColumn.CellDataFeatures<FidelizacaoClass, String> param) -> {
			if (colunaGastoAnual.validateValue(param))
				return param.getValue().getValue().gastoAnual;
			else
				return colunaGastoAnual.getComputedValue(param);
		});
		colunaGastoTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<FidelizacaoClass, String> param) -> {
			if (colunaGastoTotal.validateValue(param))
				return param.getValue().getValue().gastoTotal;
			else
				return colunaGastoTotal.getComputedValue(param);
		});
		colunaDataNascimento.setCellValueFactory((TreeTableColumn.CellDataFeatures<FidelizacaoClass, String> param) -> {
			if (colunaDataNascimento.validateValue(param))
				return param.getValue().getValue().dataNascimento;
			else
				return colunaDataNascimento.getComputedValue(param);
		});

		ObservableList<FidelizacaoClass> lista_clientes = FXCollections.observableArrayList();

		try {
			ObservableList<Cliente_Gasto> lista_gasto = new ClienteFisicaDao().listarGasto();

			new ClienteJuridicaDao().listarGasto().forEach(cliente -> {
				lista_gasto.add(new Cliente_Gasto(cliente.getId(), cliente.getCliente(), cliente.getGastoMensal(),
						cliente.getGastoAnual(), cliente.getGastoTotal(), null, "cliente_juridica",
						cliente.getEmail()));
			});
			;

			// Ordenando
			lista_gasto.sort(new Comparator<Cliente_Gasto>() {

				@Override
				public int compare(Cliente_Gasto cliente1, Cliente_Gasto cliente2) {
					// TODO Auto-generated method stub
					if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == -1)
						return 1;
					if (cliente1.getGastoMensal().compareTo(cliente2.getGastoMensal()) == 1)
						return -1;

					return 0;
				}
			});

			// Ordenado
			for (Cliente_Gasto cliente : lista_gasto) {
				lista_clientes.add(new FidelizacaoClass(cliente.getCliente(), cliente.getGastoMensal(),
						cliente.getGastoAnual(), cliente.getGastoTotal(), cliente.getDataNascimento() != null
								? cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
								: cliente.getTipo_cliente().equals("cliente_juridica") ? "Não possui"
										: "Cadastro Incompleto",
						cliente.getTipo_cliente(), cliente.getEmail()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		treeTableViewFidelizacao
				.setRoot(new RecursiveTreeItem<FidelizacaoClass>(lista_clientes, RecursiveTreeObject::getChildren));

		treeTableViewFidelizacao.getColumns().setAll(colunaCliente, colunaGastoMensal, colunaGastoAnual,
				colunaGastoTotal, colunaDataNascimento);
		treeTableViewFidelizacao.setShowRoot(false);

		campoPesquisar.textProperty().addListener((o, oldVal, pesquisa) -> {
			treeTableViewFidelizacao.setPredicate(
					person -> person.getValue().cliente.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().gastoMensal.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().gastoAnual.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().gastoTotal.get().toLowerCase().contains(pesquisa.toLowerCase())
							|| person.getValue().dataNascimento.get().toLowerCase().contains(pesquisa.toLowerCase()));
		});
	}

	// criando uma classe anonima para ser consumida pela tabela
	class FidelizacaoClass extends RecursiveTreeObject<FidelizacaoClass> {

		StringProperty cliente;
		StringProperty gastoMensal;
		StringProperty gastoAnual;
		StringProperty gastoTotal;
		StringProperty dataNascimento;
		StringProperty tipoCliente;
		StringProperty email;

		@Override
		public String toString() {
			return cliente.getValue() + "/" + email.getValue();
		}

		public FidelizacaoClass(String cliente, BigDecimal gastoMensal, BigDecimal gastoAnual, BigDecimal gastoTotal,
				String dataNascimento, String tipoCliente, String email) {

			this.cliente = new SimpleStringProperty(String.valueOf(cliente));
			this.gastoMensal = new SimpleStringProperty(String
					.valueOf(new NumeroTextField(gastoMensal, NumberFormat.getCurrencyInstance(new Locale("pt", "BR")))
							.getText()));
			this.gastoAnual = new SimpleStringProperty(String
					.valueOf(new NumeroTextField(gastoAnual, NumberFormat.getCurrencyInstance(new Locale("pt", "BR")))
							.getText()));
			this.gastoTotal = new SimpleStringProperty(String
					.valueOf(new NumeroTextField(gastoTotal, NumberFormat.getCurrencyInstance(new Locale("pt", "BR")))
							.getText()));
			this.dataNascimento = new SimpleStringProperty(dataNascimento);
			this.tipoCliente = new SimpleStringProperty(tipoCliente);
			this.email = new SimpleStringProperty(email);

		}

	}

	void atualizarTabela() {

		try {
			treeTableViewFidelizacao = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewFidelizacao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void btnGerarCupom(ActionEvent event) {
		if (treeTableViewFidelizacao.getSelectionModel().getSelectedIndex() != -1) {

			try {
				String dados[] = treeTableViewFidelizacao.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				String nome = dados[0] != null ? dados[0] : "null";

				if (!nome.isEmpty() && !nome.equals("null")) {
					nomeCliente.setText(nome);
					new Main().carregarTelaGerarCupomDesconto();
				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
		//			String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Esse cliente não possui cadastro completo", 4000);
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				snackBar = new JFXSnackbar(borderPaneTabela);
		///		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Esse cliente não possui cadastro completo", 4000);
			}

		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona cliente na tabela", 4000);
		}

	}

	@FXML
	void btnEnviarEmail(ActionEvent event) {
		if (treeTableViewFidelizacao.getSelectionModel().getSelectedIndex() != -1) {

			try {
				String dados[] = treeTableViewFidelizacao.getSelectionModel().getSelectedItem().getValue().toString()
						.split("/");
				String nome = dados[0];
				String email = dados[1] != null ? dados[1] : "null";

				if (!email.isEmpty() && !email.equals("null")) {
					nomeCliente.setText(nome);
					emailCliente.setText(email);
					new Main().carregarTelaEnviarEmailAniversario();
				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
			//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
					snackBar.show("Esse cliente não possui E-mail", 4000);
				
				}
			} catch (Exception e) {
				// TODO: handle exception
				snackBar = new JFXSnackbar(borderPaneTabela);
			//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Esse cliente não possui E-mail", 4000);
			
			}

		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			snackBar.show("Seleciona o aniversariante na tabela", 4000);
		
		}

	}

} 
