package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Connection;
import Client.Queryes.ServerQueryType;
import com.example.final_client_code.HelloApplication;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox myStocksPane;

    @FXML
    private HBox MessagesPagePane;

    @FXML
    private StackPane backPane;

    @FXML
    private HBox myAccountPagePane;

    @FXML
    private HBox stocksPagePane;

    @FXML
    private HBox transferMoneyPagePane;

    @FXML
    private Text nameTextOnBlockMyAccount;

    @FXML
    void initialize() {
        addConstrains();
        System.out.println("Welcome to Main page!");
        MyUser.refreshAll();

    }

    public void addConstrains(){
        Methods.addNextPage(myAccountPagePane,"My_account_page.fxml");
        Methods.addNextPage(MessagesPagePane,"Messages_page.fxml");
        Methods.logOut(backPane,"First_page.fxml");
        Methods.addNextPage(transferMoneyPagePane, "Transfer_page.fxml");
        Methods.addNextPage(stocksPagePane,"Stocks_page.fxml");
        Methods.addNextPage(myStocksPane, "My_stocks_page.fxml");
        nameTextOnBlockMyAccount.setText(MyUser.getCurrentUser().getShortenFullName());


    }

}
