/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.postoGasolina.model.validacoes;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * An simple autofill function to JavaFX combobox
 * @author Gustavo Fragoso
 */
public class AutoFillComboboxMain extends Application {
    
    //private AutoFillComboBox2 test;
    
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane pane = new BorderPane();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Albuquerque","Arlington","Atlanta","Austin","Baltimore","Boston","Charlotte","Chicago","Cleveland"
        ,"Colorado Springs","Columbus","Dallas","Denver","Detroit","El Paso","Fort Worth","Fresno","Honolulu"
        ,"Houston","Indianapolis","Jacksonville","Kansas City","Las Vegas","Long Beach","Los Angeles","Louisville"
        ,"Memphis","Mesa","Miami","Milwaukee","Minneapolis","Nashville-Davidson","New Orleans","New York","Oakland"
        ,"Oklahoma City","Omaha","Philadelphia","Phoenix","Portland","Raleigh","Sacramento","St. Louis","San Antonio"
        ,"San Diego","San Francisco","San Jose","Seattle","Tucson","Tulsa","Virginia Beach","Washington","Wichita");
        //test = new AutoFillComboBox2(items);
        
       // pane.setCenter(test);
        Scene scene = new Scene(pane, 400, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
