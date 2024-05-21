package com.example.final_client_code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        System.out.println("App started!!!");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("first_page.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap");

        stage.setTitle("BankApp!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args){
        launch();
    }



}