package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Client.Stock.Stock;
import com.example.final_client_code.HelloApplication;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MyStocksPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane backPane;

    @FXML
    private FlowPane flowpane;

    @FXML
    private StackPane refreshPane;

    @FXML
    void initialize() {
        addConstrains();
        loadStocks();

        refreshPane.setOnMouseClicked(mouseEvent -> {
            refreshPage();
        });
    }

    public void loadStocks(){
        flowpane.getChildren().clear();
        System.out.println(MyUser.getCurrentUser().getUserID()+": userID");
        ArrayList<Stock> stocks = new ArrayList<>(stocks());
        System.out.println("------------------------------------------");
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
        System.out.println("------------------------------------------");
        try{
            for (int i = 0; i < stocks().size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(HelloApplication.class.getResource(("My_stock_block.fxml")));

                HBox hBox = fxmlLoader.load();
                MyStockBlockController controller = fxmlLoader.getController();
                controller.setDate(stocks.get(i));
                controller.setActions(stocks.get(i).getStockID(), this);

                flowpane.getChildren().add(hBox);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void refreshPage(){
        MyUser.refreshUserStocks();
        loadStocks();
    }
    public void addConstrains(){
        Methods.addNextPage(backPane, "Main_page.fxml");
    }

    private List<Stock> stocks(){
        return MyUser.getCurrentUser().getStocks();
    }

}
