package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Connection;
import Client.Queryes.BuyStockQuery;
import Client.Queryes.ServerQueryType;
import Client.Stock.Stock;
import Client.User;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class StockBlockController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buybutton;

    @FXML
    private HBox hBox;

    @FXML
    private Button minusButton;

    @FXML
    private Button plusbutton;

    @FXML
    private TextField stockCountField;

    @FXML
    private Text stockDescriptionText;

    @FXML
    private ImageView stockImage;

    @FXML
    private Text stockNameText;

    @FXML
    private Text stockPriceText;
    private int stockPrice;


    @FXML
    void initialize() {
        Methods.restrictToNumericInput(stockCountField);
        plusbutton.setOnAction(actionEvent -> {
            int count = Integer.parseInt(stockCountField.getText());
            count++;
            stockCountField.setText(String.valueOf(count));
        });
        minusButton.setOnAction(actionEvent -> {
            int count = Integer.parseInt(stockCountField.getText());
            if(count<=1)
                return;
            count--;
            stockCountField.setText(String.valueOf(count));
        });
    }

    public void setDate(Stock stock){
        stockNameText.setText(stock.getStockName());
        stockPriceText.setText(stock.getStockPrice()+"₸");
        this.stockPrice = stock.getStockPrice();
        stockDescriptionText.setText(stock.getStockDescription());
    }

    public void setActions(int stockID){
        buybutton.setOnAction(actionEvent -> {

            int amount = this.stockPrice*Integer.parseInt(this.stockCountField.getText());
            System.out.println("Amount: "+amount);

            if(MyUser.getCurrentUser().getBalance()<amount){
                Methods.showErrorAlert("ERROR","You don't have enough money:(");
                return;
            }

//            System.out.println(MyUser.getCurrentUser());
            try{
//                System.out.println("Connecting...");
                Connection connection = Connection.getInstance();
//                System.out.println("Connected :)");
                ObjectOutputStream out = connection.getOut();
//                System.out.println(out);
                ObjectInputStream in = connection.getIn();
//                System.out.println(in);

                out.writeInt(ServerQueryType.BUY_STOCK);
                out.flush();

                BuyStockQuery buyQuery = new BuyStockQuery(MyUser.getCurrentUser().getUserID(), stockID, Integer.parseInt(stockCountField.getText()));
                out.writeObject(buyQuery);
                System.out.println(buyQuery);
                out.flush();

                boolean bool = in.readBoolean();

                if(bool){
                    Methods.showConfirmAlert("Success", "You successfully bought the stock ;)");
                }else {
                    Methods.showErrorAlert("ERROR", "Error when try buy stock ;(");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }



}

