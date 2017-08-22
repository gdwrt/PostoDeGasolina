package com.postoGasolina.main;

import java.io.IOException;

import com.postoGasolina.model.Fluxo_caixa;

import insidefx.undecorator.Undecorator;
import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static Stage stage, stageAux, stageAux2;
	private Region root, rootAux, rootAux2;
	private final HostServices services = this.getHostServices();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.stage = primaryStage;
		//carregarTelaLogin();
		carregarTelaPrincipal();

	}

	public void fecharStage() {
		verificaMaximizadoOuTelaCheia();
		stage.close();
		stage = new Stage();
	}

	public void fecharStage2() {
		verificaMaximizadoOuTelaCheia();
		stageAux.close();
		stageAux = new Stage();
	}

	public void fecharStage3() {
		verificaMaximizadoOuTelaCheia();
		stageAux2.close();
		stageAux2 = new Stage();
	}

	public void verificaMaximizadoOuTelaCheia() {
		// seta os valores de tela cheia e maximizado
		stageAux = new Stage();
		stageAux.setFullScreen(stage.isFullScreen());
	}

	public void atualizarTela() {
		// verifica se o stageAux está full screen
		if (stageAux.isFullScreen()) {
			stage.sizeToScene();
			stage.setFullScreen(false);
			stage.setFullScreen(true);
		}
	}

	public void configurarTela() {
		try {

			// CUSTOMIZAR A TELA
			// final UndecoratorScene undecoratorScene = new
			// UndecoratorScene(stage, root);
			final UndecoratorScene undecoratorScene = new UndecoratorScene(stage, root);

			// ADICIONANDO EFEITO QUANDO ABRE A TELA "FADE TRANSITION"
			undecoratorScene.setFadeInTransition();

			// ADICIONANDO EFEITO AO FECHAR A TELA "FADE TRANSITION"
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					we.consume(); // Do not hide yet
					undecoratorScene.setFadeOutTransition();
				}
			});

			// SETANDO A CENA CUSTOMIZADA
			stage.setScene(undecoratorScene);

			stage.toFront();
			stage.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void configurarTelaNoFade() {
		try {

			// CUSTOMIZAR A TELA
			// final UndecoratorScene undecoratorScene = new
			// UndecoratorScene(stage, root);
			final UndecoratorScene undecoratorScene = new UndecoratorScene(stage, root);

			// ADICIONANDO EFEITO QUANDO ABRE A TELA "FADE TRANSITION"
			// undecoratorScene.setFadeInTransition();

			// ADICIONANDO EFEITO AO FECHAR A TELA "FADE TRANSITION"
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					if(Fluxo_caixa.getStatus().equals("Aberto")){
						new Main().carregarTelaFecharCaixa();
						we.consume();
					}else if(Fluxo_caixa.getStatus().equals("Fechado")){
						we.consume(); // Do not hide yet
						undecoratorScene.setFadeOutTransition();
					}
				}
			});

			// SETANDO A CENA CUSTOMIZADA
			stage.setScene(undecoratorScene);
			stage.toFront();
			stage.show();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void configurarTelaUtilitaria() {
		try {

			// CUSTOMIZAR A TELA
			// final UndecoratorScene undecoratorScene = new
			// UndecoratorScene(stage, root);
			stageAux.close();
			stageAux = new Stage();

			final UndecoratorScene undecoratorScene1 = new UndecoratorScene(stageAux, StageStyle.UTILITY, rootAux,
					null);

			// ADICIONANDO EFEITO QUANDO ABRE A TELA "FADE TRANSITION"
			// undecoratorScene1.setFadeInTransition();

			// ADICIONANDO EFEITO AO FECHAR A TELA "FADE TRANSITION"
			stageAux.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					we.consume(); // Do not hide yet
					undecoratorScene1.setFadeOutTransition();
				}
			});

			// SETANDO A CENA CUSTOMIZADA
			stageAux.setScene(undecoratorScene1);

			stageAux.initModality(Modality.WINDOW_MODAL);

			// Set sizes based on client area's sizes
			Undecorator undecorator = undecoratorScene1.getUndecorator();
			stageAux.setMinWidth(undecorator.getMinWidth());
			stageAux.setMinHeight(undecorator.getMinHeight());
			stageAux.setWidth(undecorator.getPrefWidth());
			stageAux.setHeight(undecorator.getPrefHeight());
			if (undecorator.getMaxWidth() > 0) {
				stageAux.setMaxWidth(undecorator.getMaxWidth());
			}
			if (undecorator.getMaxHeight() > 0) {
				stageAux.setMaxHeight(undecorator.getMaxHeight());
			}

			stageAux.initOwner(stage);
			stageAux.sizeToScene();
			stageAux.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	public void configurarTelaUtilitaria4() {
		try {

			// CUSTOMIZAR A TELA
			// final UndecoratorScene undecoratorScene = new
			// UndecoratorScene(stage, root);
			stageAux.close();
			stageAux = new Stage();

			final UndecoratorScene undecoratorScene1 = new UndecoratorScene(stageAux, StageStyle.UTILITY, rootAux,
					null);

			// ADICIONANDO EFEITO QUANDO ABRE A TELA "FADE TRANSITION"
			// undecoratorScene1.setFadeInTransition();

			// ADICIONANDO EFEITO AO FECHAR A TELA "FADE TRANSITION"
			stageAux.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					we.consume(); // Do not hide yet
					undecoratorScene1.setFadeOutTransition();
				}
			});

			// SETANDO A CENA CUSTOMIZADA
			stageAux.setScene(undecoratorScene1);

			stageAux.initModality(Modality.WINDOW_MODAL);

			// Set sizes based on client area's sizes
			Undecorator undecorator = undecoratorScene1.getUndecorator();
			stageAux.setMinWidth(undecorator.getMinWidth());
			stageAux.setMinHeight(undecorator.getMinHeight());
			stageAux.setWidth(undecorator.getPrefWidth());
			stageAux.setHeight(undecorator.getPrefHeight());
			if (undecorator.getMaxWidth() > 0) {
				stageAux.setMaxWidth(undecorator.getMaxWidth());
			}
			if (undecorator.getMaxHeight() > 0) {
				stageAux.setMaxHeight(undecorator.getMaxHeight());
			}

			stageAux.initOwner(stage);
			stageAux.sizeToScene();
			stageAux.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void configurarTelaUtilitaria2() {
		try {

			// CUSTOMIZAR A TELA
			// final UndecoratorScene undecoratorScene = new
			// UndecoratorScene(stage, root);
			//stageAux2.close();
			stageAux2 = new Stage();

			final UndecoratorScene undecoratorScene2 = new UndecoratorScene(stageAux2, StageStyle.UTILITY, rootAux,
					null);

			// ADICIONANDO EFEITO QUANDO ABRE A TELA "FADE TRANSITION"
			// undecoratorScene1.setFadeInTransition();

			// ADICIONANDO EFEITO AO FECHAR A TELA "FADE TRANSITION"
			stageAux2.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					we.consume(); // Do not hide yet
					undecoratorScene2.setFadeOutTransition();
				}
			});

			// SETANDO A CENA CUSTOMIZADA
			stageAux2.setScene(undecoratorScene2);

			stageAux2.initModality(Modality.WINDOW_MODAL);

			// Set sizes based on client area's sizes
			Undecorator undecorator = undecoratorScene2.getUndecorator();
			stageAux2.setMinWidth(undecorator.getMinWidth());
			stageAux2.setMinHeight(undecorator.getMinHeight());
			stageAux2.setWidth(undecorator.getPrefWidth());
			stageAux2.setHeight(undecorator.getPrefHeight());
			if (undecorator.getMaxWidth() > 0) {
				stageAux2.setMaxWidth(undecorator.getMaxWidth());
			}
			if (undecorator.getMaxHeight() > 0) {
				stageAux2.setMaxHeight(undecorator.getMaxHeight());
			}

			stageAux2.initOwner(stageAux);
			stageAux2.sizeToScene();
			stageAux2.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// Telas
	public void carregarTelaPrincipal() {
		try {
			// fecho o tela anterior
			fecharStage();
			// carrego a nova tela
			root = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaPrincipal.fxml"));
			// configuro a tela
			configurarTelaNoFade();
			// atualizo a tela
			atualizarTela();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Telas
		public void carregarTelaRecuperarSenha() {
			try {
				// fecho o tela anterior
				fecharStage();
				// carrego a nova tela
				root = FXMLLoader
						.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaRecuperarSenha.fxml"));
				// configuro a tela
				configurarTelaNoFade();
				// atualizo a tela
				atualizarTela();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public void carregarTelaLogin() {
		// TODO Auto-generated method stub
		try {
			fecharStage();
			root = FXMLLoader.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaLogin.fxml"));
			configurarTela();
			atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean fullscreen() {
		if (stage.isFullScreen()) {
			return true;
		} else {
			return false;
		}
	}

	public void carregarTelaCargo() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaCadastrarCargo.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarTipoCombustivel() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader.load(getClass().getClassLoader()
					.getResource("com/postoGasolina/view/TelaCadastrarTipoCombustivel.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarTelaCadastrarBomba() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaCadastrarBomba.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarTelaFecharVenda() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaFechamentoVenda.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarTelaDesconto() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaDesconto.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaDesconto2() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaDesconto2.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaAbrirCaixa() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaAbrirCaixa.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaFecharCaixa() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaFecharCaixa.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaCategoria() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaCadastrarCategoria.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaUnidadeMedida() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaCadastrarUnidadeMedida.fxml"));
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaUnidadeMedida2() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TelaCadastrarUnidadeMedida.fxml"));
			configurarTelaUtilitaria2();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaOrgaoUtilitaria() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader.load(getClass().getClassLoader()
					.getResource("com/postoGasolina/view/TelaGerenciaOrgãoGovernamental2.fxml"));
			
			configurarTelaUtilitaria4();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaFornecedorUtilitaria() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader.load(getClass().getClassLoader()
					.getResource("com/postoGasolina/view/TelaGerenciarFornecedores2.fxml"));
			
			configurarTelaUtilitaria4();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaCadastroRapidoClientes() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader.load(getClass().getClassLoader()
					.getResource("com/postoGasolina/view/TelaCadastroRapidoClientes.fxml"));
			
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaEnviarEmailAniversario() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader.load(getClass().getClassLoader()
					.getResource("com/postoGasolina/view/TelaEnviarEmailAniversario.fxml"));
			
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregarTelaGerarCupomDesconto() {
		// TODO Auto-generated method stub
		try {
			rootAux = FXMLLoader.load(getClass().getClassLoader()
					.getResource("com/postoGasolina/view/TelaGerarCupomDesconto.fxml"));
			
			configurarTelaUtilitaria();
			// atualizarTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 
	 
	public Stage getStage() {
		return this.stage;
	}

	public Stage getStage1() {
		return this.stageAux;
	}

	public Stage getStage2() {
		return this.stageAux2;
	}
}
