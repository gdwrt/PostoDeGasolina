package com.postoGasolina.model.validacoes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ComboBoxAutoCompleteTest extends Application {

	private static final String[] LISTA = { "Abacate", "Abacaxi", "Ameixa", "Amora", "Araticum", "Atemoia", "Avocado",
			"Banana prata", "Caju", "Cana descascada", "Caqui", "Caqui Fuyu", "Carambola", "Cereja", "Coco verde",
			"Figo", "Figo da Ã�ndia", "Framboesa", "Goiaba", "Graviola", "Jabuticaba", "Jambo", "Jambo rosa", "JambolÃ£o",
			"Kino (Kiwano)", "Kiwi", "Laranja Bahia", "Laranja para suco", "Laranja seleta", "Laranja serra dâ€™Ã¡gua",
			"Laranjinha kinkan", "Lichia", "Lima da pÃ©rsia", "LimÃ£o galego", "LimÃ£o Taiti", "MaÃ§Ã£ argentina",
			"MaÃ§Ã£ Fuji", "MaÃ§Ã£ gala", "MaÃ§Ã£ verde", "MamÃ£o formosa", "MamÃ£o HavaÃ­", "Manga espada", "Manga Haden",
			"Manga Palmer", "Manga Tommy", "Manga UbÃ¡", "Mangostim", "MaracujÃ¡ doce", "MaracujÃ¡ para suco", "Melancia",
			"Melancia sem semente", "MelÃ£o", "MelÃ£o Net", "MelÃ£o Orange", "MelÃ£o pele de sapo", "MelÃ£o redinha",
			"Mexerica carioca", "Mexerica Murcote", "Mexerica Ponkan", "Mirtilo", "Morango", "Nectarina",
			"NÃªspera ou ameixa amarela", "Noni", "Pera asiÃ¡tica", "Pera portuguesa", "PÃªssego", "Physalis", "Pinha",
			"Pitaia", "RomÃ£", "Tamarilo", "Tamarindo", "Uva red globe", "Uva rosada", "Uva Rubi", "Uva sem semente",
			"Abobora moranga", "Abobrinha italiana", "Abobrinha menina", "Alho", "Alho descascado",
			"Batata baroa ou cenoura amarela", "Batata bolinha", "Batata doce", "Batata inglesa", "Batata yacon",
			"Berinjela", "Beterraba", "Cebola bolinha", "Cebola comum", "Cebola roxa", "Cenoura", "Cenoura baby",
			"Couve flor", "Ervilha", "Fava", "Gengibre", "Inhame", "JilÃ³", "Massa de alho", "Maxixe", "Milho",
			"Pimenta biquinho fresca", "Pimenta de bode fresca", "PimentÃ£o amarelo", "PimentÃ£o verde",
			"PimentÃ£o vermelho", "Quiabo", "Repolho", "Repolho roxo", "Tomate cereja", "Tomate salada",
			"Tomate sem acidez", "Tomate uva", "Vagem", "AgriÃ£o", "Alcachofra", "Alface", "Alface americana",
			"AlmeirÃ£o", "BrÃ³colis", "Broto de alfafa", "Broto de bambu", "Broto de feijÃ£o", "Cebolinha", "Coentro",
			"Couve", "Espinafre", "HortelÃ£", "Mostarda", "RÃºcula", "Salsa", "Ovos brancos", "Ovos de codorna",
			"Ovos vermelhos" };

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		ComboBox<String> cmb = new ComboBox<>();
		cmb.setTooltip(new Tooltip());
		cmb.getItems().addAll(LISTA);
		stage.setScene(new Scene(new StackPane(cmb)));
		stage.show();
		stage.setTitle("Filtrando um ComboBox");
		stage.setWidth(300);
		stage.setHeight(300);
		new ComboBoxAutoComplete<String>(cmb);
		
	}

}
