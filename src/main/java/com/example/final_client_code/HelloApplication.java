package com.example.final_client_code;

import Client.Connection;
import Client.Messages.Message;
import Client.Queryes.LoginUserQuery;
import Client.Queryes.ServerQueryType;
import Client.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
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