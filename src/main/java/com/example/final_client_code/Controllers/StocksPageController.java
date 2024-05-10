package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Client.Connection;
import Client.Queryes.ServerQueryType;
import Client.Stock.Stock;
import com.example.final_client_code.HelloApplication;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StocksPageController implements Initializable {

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
    List<Stock> stocks;

    @FXML
    void initialize(){
        addConstrains();
    }
    public void addConstrains(){
        Methods.addNextPage(backPane, "Main_page.fxml");
    }

    private List<Stock> stocks(){
        List<Stock > list = new ArrayList<>();
        try {
            System.out.println("Try to get all stocks...");
//            System.out.println("Connecting...");
            Connection connection = Connection.getInstance();
//            System.out.println("Connected :)");
            ObjectOutputStream out = connection.getOut();
            ObjectInputStream in = connection.getIn();

            out.writeInt(ServerQueryType.GET_ALL_STOCKS);
            out.flush();
            System.out.println("Send type ..");

            list = (List<Stock>) in.readObject();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("? Error when try to get all stocks, "+e.getMessage()+" ?");
            return list;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(MyUser.getCurrentUser().getUserID()+": userID");
        stocks = new ArrayList<>(stocks());

        try{
            for (int i = 0; i < stocks().size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(HelloApplication.class.getResource("stockBlock.fxml"));
                HBox hBox = fxmlLoader.load();
                StockBlockController controller = fxmlLoader.getController();
                controller.setDate(stocks.get(i));
                controller.setActions(stocks.get(i).getStockID());

                flowpane.getChildren().add(hBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }
}
