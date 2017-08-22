package com.postoGasolina.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.mail.EmailException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.postoGasolina.dao.PermissoesDao;
import com.postoGasolina.main.Main;
import com.postoGasolina.model.Email;
import com.postoGasolina.model.Login;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class TelaRecuperarSenhaController implements Initializable {

	@FXML
	private BorderPane borderPanePrincipal;

	@FXML
	private VBox vboxLogin;

	@FXML
	private ImageView logoPosto;

	@FXML
	private JFXTextField campoEmail;

	@FXML
	private JFXPasswordField campoSenha;

	@FXML
	private JFXButton btnEntrar;

	@FXML
	private BorderPane borderPaneCenter;

	// recebe um resultado do método
	private byte result = 0;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnAvancar;

	private static int posicaoSlideShow = 1;

	@FXML
	private JFXButton btnSnapChat;

	@FXML
	private JFXButton btnFaceboook;

	@FXML
	private JFXButton btnTwitter;

	@FXML
	private JFXButton btnInstagram;

	@FXML
	private GridPane gridPaneCenter;

	@FXML
	private Hyperlink linkVoltar;

	@FXML
	private ImageView imgLoad;
	
	private JFXSnackbar snackBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		slideShow();
		logoTransition();
		linkVoltar.setOnAction(e -> {
			new Main().carregarTelaLogin();
		});
	}

	void slideShow() {
		try {
			borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
					+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/bombaCombustivel.png\");");

			FadeTransition fadein = new FadeTransition(Duration.seconds(5), borderPaneCenter);
			fadein.setFromValue(1);
			fadein.setToValue(1);
			fadein.play();
			posicaoSlideShow = 1;

			fadein.setOnFinished(e -> {

				borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
						+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/galao.png\");");
				FadeTransition fadein2 = new FadeTransition(Duration.seconds(5), borderPaneCenter);
				fadein2.setFromValue(0.1);
				fadein2.setToValue(1);
				fadein2.play();
				posicaoSlideShow = 2;

				fadein2.setOnFinished(event2 -> {

					borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
							+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/car.png\");");

					FadeTransition fadein3 = new FadeTransition(Duration.seconds(5), borderPaneCenter);
					fadein3.setFromValue(0.1);
					fadein3.setToValue(1);
					fadein3.play();
					posicaoSlideShow = 3;

					fadein3.setOnFinished(event3 -> {
						borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
								+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/bombaCombustivel.png\");");

						FadeTransition fadein4 = new FadeTransition(Duration.seconds(5), borderPaneCenter);
						fadein4.setFromValue(0.1);
						fadein4.setToValue(1);
						fadein4.play();
						posicaoSlideShow = 1;

					});
				});

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void btnAvancarOnAction(ActionEvent event) {

		if (posicaoSlideShow == 3) {
			posicaoSlideShow = 1;
		} else {
			posicaoSlideShow++;
		}
		if (posicaoSlideShow == 1) {
			borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
					+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/bombaCombustivel.png\");");

			FadeTransition fadein = new FadeTransition(Duration.seconds(5), borderPaneCenter);
			fadein.setFromValue(0);
			fadein.setToValue(1);
			fadein.play();
		} else if (posicaoSlideShow == 2) {
			borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
					+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/galao.png\");");

			FadeTransition fadein = new FadeTransition(Duration.seconds(5), borderPaneCenter);
			fadein.setFromValue(0);
			fadein.setToValue(1);
			fadein.play();
		} else if (posicaoSlideShow == 3) {
			borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
					+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/car.png\");");

			FadeTransition fadein = new FadeTransition(Duration.seconds(5), borderPaneCenter);
			fadein.setFromValue(0);
			fadein.setToValue(1);
			fadein.play();
		}
	}

	@FXML
	void btnVoltarOnAction(ActionEvent event) {
		if (posicaoSlideShow == 1) {
			posicaoSlideShow = 3;
		} else {
			posicaoSlideShow--;
		}
		if (posicaoSlideShow == 1) {
			borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
					+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/bombaCombustivel.png\");");

			FadeTransition fadein = new FadeTransition(Duration.seconds(5), borderPaneCenter);
			fadein.setFromValue(0);
			fadein.setToValue(1);
			fadein.play();
		} else if (posicaoSlideShow == 2) {
			borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
					+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/galao.png\");");

			FadeTransition fadein = new FadeTransition(Duration.seconds(5), borderPaneCenter);
			fadein.setFromValue(0);
			fadein.setToValue(1);
			fadein.play();
		} else if (posicaoSlideShow == 3) {
			borderPaneCenter.setStyle("-fx-background-position: center ;-fx-background-repeat: no-repeat;"
					+ "-fx-background-image: url(\"/com/postoGasolina/img/TelaLogin/car.png\");");

			FadeTransition fadein = new FadeTransition(Duration.seconds(5), borderPaneCenter);
			fadein.setFromValue(0);
			fadein.setToValue(1);
			fadein.play();
		}
	}

	void logoTransition() {
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(122), logoPosto);
		rotateTransition.setFromAngle(0);
		rotateTransition.setToAngle(10 * 720);
		rotateTransition.play();

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

	public static boolean consegueConectar(String address) {
		try {
			URL url = new URL(address);
			URLConnection connection = url.openConnection();
			connection.connect();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@FXML
	void onActionBtnRecuperar(ActionEvent event) {
		imgLoad.setImage(new Image(new File("/com/postoGasolina/img/TelaVenda/indicator_32.gif").toString()));
		if (!campoEmail.getText().isEmpty()) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						boolean result = new PermissoesDao().validarEmail(campoEmail.getText());
						if (result) {

							try {
								if (consegueConectar("http://www.google.com.br")) {

									// gera uma nova senha
									String novaSenha = new Email().geradorSenhaAutomatico();
									// atualiza no banco de dados
									new PermissoesDao().AtualizarSenha(campoEmail.getText(), novaSenha);
									// litando dados do login
									Login l = !new PermissoesDao().pesquisar(campoEmail.getText()).isEmpty()
											? new PermissoesDao().pesquisar(campoEmail.getText()).get(0) : null;
									// envia o email pro usuário com a nova
									// senha
									new Email().enviarNovaSenha(campoEmail.getText(),
											l != null ? l.getFuncionario().getPessoa().getNome() : "", novaSenha);

									String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css")
											.toExternalForm();
									javafx.application.Platform.runLater(() -> {
										snackBar = new JFXSnackbar(borderPaneCenter);
										snackBar.show("Senha gerada com sucesso, verifique seu E-mail", 6000);
										imgLoad.setImage(null);

									});

									campoEmail.setText("");
								} else {
									javafx.application.Platform.runLater(() -> {
										snackBar = new JFXSnackbar(borderPaneCenter);
										snackBar.show("Conexão com a internet indisponível", 6000);
										imgLoad.setImage(null);

									});
								}

							} catch (EmailException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();

								String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css")
										.toExternalForm();
								javafx.application.Platform.runLater(() -> {
									snackBar = new JFXSnackbar(borderPaneCenter);
									snackBar.show("Não foi possível enviar E-mail", 4000);
									imgLoad.setImage(null);
								});
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();

								String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css")
										.toExternalForm();
								javafx.application.Platform.runLater(() -> {
									snackBar = new JFXSnackbar(borderPaneCenter);
									snackBar.show("Não foi possível enviar E-mail", 4000);
									imgLoad.setImage(null);
								});
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								javafx.application.Platform.runLater(() -> {
									snackBar = new JFXSnackbar(borderPaneCenter);
									snackBar.show("Não foi possível enviar E-mail", 4000);
									imgLoad.setImage(null);
								});
							}

						} else {

							String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css")
									.toExternalForm();
							javafx.application.Platform.runLater(() -> {
								snackBar = new JFXSnackbar(borderPaneCenter);
								snackBar.show("E-mail inválido", 4000);
								imgLoad.setImage(null);
							});

						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						javafx.application.Platform.runLater(() -> {
							snackBar = new JFXSnackbar(borderPaneCenter);
							snackBar.show("Não foi possível enviar E-mail", 4000);
							imgLoad.setImage(null);
						});
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
						javafx.application.Platform.runLater(() -> {
							snackBar = new JFXSnackbar(borderPaneCenter);
							snackBar.show("Não foi possível enviar E-mail", 4000);
							imgLoad.setImage(null);
						});
					}

				} 
			}).start();

		} else {
			imgLoad.setImage(null);
			snackBar = new JFXSnackbar(borderPaneCenter);
			snackBar.show("Informa E-mail", 4000);
		}
	}

}
