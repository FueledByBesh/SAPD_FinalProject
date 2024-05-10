package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Connection;
import Client.Queryes.SellStockQuery;
import Client.Queryes.ServerQueryType;
import Client.Stock.Stock;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MyStockBlockController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hBox;

    @FXML
    private Button minusButton;

    @FXML
    private Button plusbutton;

    @FXML
    private Button sellButton;

    @FXML
    private TextField stockCountField;

    @FXML
    private Text stockCountText;

    @FXML
    private Text stockDescriptionText;

    @FXML
    private ImageView stockImage;

    @FXML
    private Text stockNameText;

    @FXML
    private Text stockPriceText;

    @FXML
    void initialize() {
        Methods.restrictToNumericInput(stockCountField);
        plusbutton.setOnAction(actionEvent -> {
            stockCountField.setText(Integer.parseInt(stockCountField.getText())+1+"");
        });
        minusButton.setOnAction(actionEvent -> {
            stockCountField.setText(Integer.parseInt(stockCountField.getText())-1+"");
        });
    }

    public void setDate(Stock stock){
        stockNameText.setText(stock.getStockName());
        stockPriceText.setText(stock.getStockPrice()+"");
        stockDescriptionText.setText(stock.getStockDescription());
        stockCountText.setText(stock.getStockCount()+"");
    }

    public void setActions(int stockID, MyStocksPageController myStockPage){
        sellButton.setOnAction(actionEvent -> {
            try{
                Connection connection = Connection.getInstance();
                ObjectOutputStream out = connection.getOut();
                ObjectInputStream in = connection.getIn();

                out.writeInt(ServerQueryType.SELL_STOCK);
                out.flush();

                SellStockQuery sellQuery = new SellStockQuery(MyUser.getCurrentUser().getUserID(), stockID, Integer.parseInt(stockCountField.getText()));
                out.writeObject(sellQuery);
                out.flush();

                boolean bool = in.readBoolean();

                if(bool){
                    Methods.showConfirmAlert("Success", "You already SELL the stock ;)");
                }else {
                    Methods.showErrorAlert("ERROR", "Error when try SELL stock ;(");
                }
                myStockPage.refreshPage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
