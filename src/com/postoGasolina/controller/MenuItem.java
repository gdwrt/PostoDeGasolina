package com.postoGasolina.controller;

import com.postoGasolina.main.Main;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class MenuItem extends Pane {
	private Text text;

	private Effect shadow = new DropShadow(1, Color.valueOf("5C97BF"));
	private Effect blur = new BoxBlur(1, 1, 1);

	public MenuItem(String name) {
		Rectangle bg = new Rectangle();
		bg.setWidth(234);
		bg.setHeight(41);
		// bg.setStroke(Color.color(1, 1, 1, 0.15));
		// bg.setEffect(new GaussianBlur());

		/*
		 * bg.fillProperty().bind( Bindings.when(pressedProperty()) .then(Color.color(1,
		 * 1, 1, 0.75)) .otherwise(Color.color(1, 1, 1, 0.15)) );
		 */
		Image img = new Image("/com/postoGasolina/img/TelaPrincipal/2.png");
		bg.setFill(new ImagePattern(img));

		text = new Text(name);
		text.setTranslateX(55);
		text.setTranslateY(25);
		text.setFont(Font.loadFont(
				Main.class.getResource("/com/postoGasolina/fonts/SofiaPro-Condensed6.ttf").toExternalForm(), 17));
		text.setFill(Color.valueOf("6C7A89"));

		text.effectProperty().bind(Bindings.when(hoverProperty()).then(shadow).otherwise(blur));

		getChildren().addAll(bg, text);
	}

	public void setOnAction(Runnable action) {
		setOnMouseClicked(e -> action.run());
	}
}