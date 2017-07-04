package com.postoGasolina.model.validacoes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Thomas Bolz
 */
public class NumeroSpinnerDemo extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Spinner Demo");
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        final NumeroSpinner defaultSpinner = new NumeroSpinner();
        final NumeroSpinner decimalFormat = new NumeroSpinner(BigDecimal.ZERO, new BigDecimal("0.05"), new DecimalFormat("#,##0.00"));
        final NumeroSpinner percent = new NumeroSpinner(BigDecimal.ZERO, new BigDecimal("0.01"), NumberFormat.getPercentInstance());
        final NumeroSpinner localizedCurrency = new NumeroSpinner(BigDecimal.ZERO, new BigDecimal("0.01"), NumberFormat.getCurrencyInstance(Locale.UK));
        final NumeroTextField lala = new NumeroTextField(BigDecimal.ZERO, NumberFormat.getCurrencyInstance(new Locale("pt","BR")));
        root.addRow(1, new Label("default"), defaultSpinner);
        root.addRow(2, new Label("custom decimal format"), decimalFormat);
        root.addRow(3, new Label("percent"), percent);
        root.addRow(4, new Label("localized currency"), lala);
        Button button = new Button("Dump layout bounds");
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                defaultSpinner.dumpSizes();
                lala.setNumber(new BigDecimal("30000000000000"));;
            }
        });
        root.addRow(5, new Label(), button);

        Scene scene = new Scene(root);
        String path = NumeroSpinnerDemo.class.getResource("number_spinner.css").toExternalForm();
        System.out.println("path=" + path);
        scene.getStylesheets().add(path);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
