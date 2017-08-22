package com.postoGasolina.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.model.Login;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

public class TelaPrincipalController implements Initializable {

	@FXML
	private BorderPane borderPaneTelaPrincipal;

	@FXML
	private GridPane gridPaneCenter;

	@FXML
	private BorderPane borderPaneRight;

	@FXML
	private BorderPane borderPaneCenter;

	private JFXDialog dialogTrocarUsuario = new JFXDialog();
	private JFXDialogLayout dialogTrocarUsuarioLayout = new JFXDialogLayout();
	private JFXButton btnOK = new JFXButton("OK");
	private JFXButton btnCANCELAR = new JFXButton("CANCELAR");
	@FXML
	private StackPane stack;

	@FXML
	private VBox vboxRight;

	private static final int WIDTH = 260;
	private static final int HEIGHT = 680;

	private List<Pair<String, Runnable>> menuData = FXCollections.observableArrayList();
	private List<Pair<String, Runnable>> menuData2 = FXCollections.observableArrayList();

	static Login login = null;


	// menu
	@FXML
	private VBox menuBox = new VBox(-5);
	private Line line;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		menuData.add(new Pair<String, Runnable>("Gerenciar", () -> {
			createContentMenuGerenciar();

		}));
		menuData.add(new Pair<String, Runnable>("Caixas", () -> {
			// 6 é caixa
			TelaAbrirCaixaController.AbrirTela.setText("6");
			carregarTelaGerenciarCaixa();
		}));
		menuData.add(new Pair<String, Runnable>("Produtos", () -> {
			carregarTelaGerenciarProdutos();
		}));
		menuData.add(new Pair<String, Runnable>("combustíveis", () -> {
			carregarTelaGerenciarCombustivel();
		}));
		menuData.add(new Pair<String, Runnable>("Vendas", () -> {
			if (Fluxo_caixa.getStatus().equals("Fechado")) {
				try {
					borderPaneCenter.setCenter(null);
					// 4 é venda
					TelaAbrirCaixaController.AbrirTela.setText("4");
					new Main().carregarTelaAbrirCaixa();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if (Fluxo_caixa.getStatus().equals("Aberto")) {
				carregarTelaVenda();
			}
		}));
		menuData.add(new Pair<String, Runnable>("Compras", () -> {

			if (Fluxo_caixa.getStatus().equals("Fechado")) {
				try {
					borderPaneCenter.setCenter(null);
					new Main().carregarTelaAbrirCaixa();
					// 5 é compra
					TelaAbrirCaixaController.AbrirTela.setText("5");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if (Fluxo_caixa.getStatus().equals("Aberto")) {
				carregarTelaCompra();
			}
		}));
		menuData.add(new Pair<String, Runnable>("Trocar de usuário", () -> {
			// dialogTrocarUsuarioLayout.setHeading(new Text("Trocar de
			// usuário"));
			dialogTrocarUsuarioLayout.setBody(new Text("Deseja realmente sair ?"));
			btnOK.setOnAction(e -> {
				new Main().carregarTelaLogin();
			});
			btnCANCELAR.setOnAction(e -> {
				dialogTrocarUsuario.close();
			});
			dialogTrocarUsuarioLayout.setActions(btnOK, btnCANCELAR);
			dialogTrocarUsuario = new JFXDialog(stack, dialogTrocarUsuarioLayout, JFXDialog.DialogTransition.CENTER);
			dialogTrocarUsuario.show();

		}));

		menuData.add(new Pair<String, Runnable>("Sair", () -> {
			if (Fluxo_caixa.getStatus().equals("Aberto")) {
				new Main().carregarTelaFecharCaixa();
			} else if (Fluxo_caixa.getStatus().equals("Fechado")) {
				new Main().getStage().close();
			}
		}));

		menuData2.add(new Pair<String, Runnable>("Funcionários", () -> {
			carregarTelaGerenciarFuncionarios();

		}));
		menuData2.add(new Pair<String, Runnable>("Clientes", () -> {
			carregarTelaGerenciarClientes();
		}));
		menuData2.add(new Pair<String, Runnable>("fidelização de clientes", () -> {
			carregarTelaGerenciarFidelizacaoClientes();
		}));
		menuData2.add(new Pair<String, Runnable>("Fornecedores", () -> {
			carregarTelaFornecedor();
		}));
		menuData2.add(new Pair<String, Runnable>("Órgãos", () -> {
			carregarTelaGerenciaOrgãoGovernamental();
		}));
		menuData2.add(new Pair<String, Runnable>("Permissões", () -> {
			carregarTelaGerenciarPermissoesFuncionarios();
		}));
		menuData2.add(new Pair<String, Runnable>("Autorizações e licenças", () -> {
			carregarTelaGerenciaLicencasAutorizacoes();
		}));
		menuData2.add(new Pair<String, Runnable>("Voltar", () -> createContentVoltar()));

		validarPermissao();

		createContent();

		TelaGerenciarClienteController.t.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (newValue.equals("1")) {
					carregarTelaGerenciarClientesJuridica();
				} else if (newValue.equals("0")) {
					carregarTelaGerenciarClientes();
				}
			}
		});
		TelaAbrirCaixaController.AbrirTela.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				System.out.println(newValue);
				if (newValue.equals("1")) {
					carregarTelaVenda();
				} else if (newValue.equals("2")) {
					carregarTelaCompra();
				}
			}
		});
	}

	void carregarTelaGerenciaOrgãoGovernamental() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciaOrgãoGovernamental.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaCompra() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader
							.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaCompra.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciaLicencasAutorizacoes() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciaLicencasAutorizacoes.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarClientesJuridica() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarClientesJuridica.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarClientes() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarClientes.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaVenda() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader
							.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaVenda.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaFornecedor() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarFornecedores.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarFuncionarios() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarFuncionarios.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarFidelizacaoClientes() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarFidelizacaoClientes.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarCombustivel() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarCombustivel.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarProdutos() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarProdutos.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarCaixa() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(
							getClass().getClassLoader().getResource("com/postoGasolina/view/TelaGerenciarCaixa.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	void carregarTelaGerenciarPermissoesFuncionarios() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Region pane = FXMLLoader.load(getClass().getClassLoader()
							.getResource("com/postoGasolina/view/TelaGerenciarPermissoesFuncionarios.fxml"));

					FadeTransition ft = new FadeTransition(Duration.millis(1500), pane);
					ft.setFromValue(0.0);
					ft.setToValue(1.0);
					ft.play();

					Platform.runLater(() -> borderPaneCenter.setCenter(pane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	// Menu
	private Parent createContent() {

		double lineX = WIDTH / 2 - 130;
		double lineY = HEIGHT / 3 - 80;
		double lineX2 = WIDTH / 2 + 50;
		double lineY2 = HEIGHT / 3 + 5;
		adicionarLinha(lineX2, lineY2);
		adicionarMenu(lineX + 5, lineY + 5);

		startAnimation();

		return borderPaneTelaPrincipal;
	}

	private Parent createContentVoltar() {
		menuBox.getChildren().clear();
		vboxRight.getChildren().clear();

		double lineX = WIDTH / 2 - 130;
		double lineY = HEIGHT / 3 - 80;
		double lineX2 = WIDTH / 2 + 50;
		double lineY2 = HEIGHT / 3 + 5;
		// adicionarLinha(lineX2, lineY2);
		adicionarMenu(lineX + 5, lineY + 5);

		startAnimationSubMenu();

		return borderPaneTelaPrincipal;
	}

	private Parent createContentMenuGerenciar() {

		menuBox.getChildren().clear();
		vboxRight.getChildren().clear();

		double lineX = WIDTH / 2 - 130;
		double lineY = HEIGHT / 3 - 80;
		double lineX2 = WIDTH / 2 + 50;
		double lineY2 = HEIGHT / 3 + 5;
		// adicionarLinha(lineX2, lineY2);
		adicionarSubMenuGerenciar(lineX + 5, lineY + 5);

		startAnimationSubMenu();

		return borderPaneTelaPrincipal;
	}

	private void adicionarLinha(double x, double y) {
		line = new Line(x, y, x, y + 390);
		line.setStrokeWidth(3);
		line.setStroke(Color.color(1, 1, 1, 0.55));
		line.setEffect(new DropShadow(5, Color.BLACK));
		line.setScaleY(0);

		gridPaneCenter.add(line, 1, 1);
		gridPaneCenter.setHalignment(line, HPos.RIGHT);

	}

	private void startAnimation() {
		ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
		st.setToY(1);
		st.setOnFinished(e -> {

			for (int i = 0; i < menuBox.getChildren().size(); i++) {
				Node n = menuBox.getChildren().get(i);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
				tt.setToX(0);
				tt.setOnFinished(e2 -> n.setClip(null));
				tt.play();
			}
		});
		st.play();
	}

	private void startAnimationSubMenu() {
		ScaleTransition st = new ScaleTransition(Duration.seconds(0.1), line);
		st.setToY(1);
		st.setOnFinished(e -> {

			for (int i = 0; i < menuBox.getChildren().size(); i++) {
				Node n = menuBox.getChildren().get(i);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.8 + i * 0.15), n);
				tt.setToX(0);
				tt.setOnFinished(e2 -> n.setClip(null));
				tt.play();
			}
		});
		st.play();
	}

	private void adicionarMenu(double x, double y) {

		try {
			menuBox.setTranslateX(x);
			menuBox.setTranslateY(y);
			menuData.forEach(data -> {
				MenuItem item = new MenuItem(data.getKey());
				item.setOnAction(data.getValue());
				item.setTranslateX(-300);

				Rectangle clip = new Rectangle(234, 41);
				clip.translateXProperty().bind(item.translateXProperty().negate());

				item.setClip(clip);

				menuBox.getChildren().addAll(item);
			});

			vboxRight.getChildren().add(menuBox);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void adicionarSubMenuGerenciar(double x, double y) {

		try {
			menuBox.setTranslateX(x);
			menuBox.setTranslateY(y);
			menuData2.forEach(data2 -> {
				MenuItem item = new MenuItem(data2.getKey());
				item.setOnAction(data2.getValue());
				item.setTranslateX(-300);

				Rectangle clip = new Rectangle(234, 41);
				clip.translateXProperty().bind(item.translateXProperty().negate());

				item.setClip(clip);

				menuBox.getChildren().addAll(item);
			});

			vboxRight.getChildren().add(menuBox);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	void btnFacebookOnAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://pt-br.facebook.com/"));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btnInstagramOnAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://www.instagram.com/?hl=pt-br"));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btnSnapChatOnAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://www.snapchat.com/l/pt-br/"));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btntwitterOnAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://twitter.com/signup?lang=pt-br"));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void validarPermissao() {

		if (login != null) {
			try {

				if (!login.isG_caixa()) {
					for (int i = 0; i < menuData.size(); ++i) {
						if (menuData.get(i).getKey().equals("Caixa")) {
							menuData.remove(i);
						}
						;
					}
				}

				if (!login.isG_produtos()) {
					for (int i = 0; i < menuData.size(); ++i) {
						if (menuData.get(i).getKey().equals("Produtos")) {
							menuData.remove(i);
						}
						;
					}

				}
				;
				if (!login.isG_combustivel()) {
					for (int i = 0; i < menuData.size(); ++i) {
						if (menuData.get(i).getKey().equals("combustíveis")) {
							menuData.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_venda_produtos()) {
					for (int i = 0; i < menuData.size(); ++i) {
						if (menuData.get(i).getKey().equals("Venda")) {
							menuData.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_compra_produtos()) {
					for (int i = 0; i < menuData.size(); ++i) {
						if (menuData.get(i).getKey().equals("Compra")) {
							menuData.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_funcionario()) {
					for (int i = 0; i < menuData2.size(); ++i) {
						if (menuData2.get(i).getKey().equals("Funcionários")) {
							menuData2.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_clientes()) {
					for (int i = 0; i < menuData2.size(); ++i) {
						if (menuData2.get(i).getKey().equals("Clientes")) {
							menuData2.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_fidelizacao()) {
					for (int i = 0; i < menuData2.size(); ++i) {
						if (menuData2.get(i).getKey().equals("fidelização de clientes")) {
							menuData2.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_fornecedores()) {
					for (int i = 0; i < menuData2.size(); ++i) {
						if (menuData2.get(i).getKey().equals("Fornecedores")) {
							menuData2.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_orgao()) {
					for (int i = 0; i < menuData2.size(); ++i) {
						if (menuData2.get(i).getKey().equals("Órgãos")) {
							menuData2.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_permissoes()) {
					for (int i = 0; i < menuData2.size(); ++i) {
						if (menuData2.get(i).getKey().equals("Permissões")) {
							menuData2.remove(i);
						}
						;
					}
				}
				;
				if (!login.isG_autorizacao_licenca()) {
					for (int i = 0; i < menuData2.size(); ++i) {
						if (menuData2.get(i).getKey().equals("Autorizações e licenças")) {
							menuData2.remove(i);
						}
						;
					}
				}
				;

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

}
