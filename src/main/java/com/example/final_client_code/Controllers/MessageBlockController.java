package com.example.final_client_code.Controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import Client.Messages.Message;
import Client.Messages.StockBuySellMessage;
import Client.Messages.TransferMessage;
import Server.Worker_classes.Messages.MessageType;
import com.example.final_client_code.MyUser;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class MessageBlockController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hBoxMain;

    @FXML
    private HBox messageBox;

    @FXML
    private Text messageText;

    @FXML
    private HBox availableMoneyBox;

    @FXML
    private Text availableMoneyText;

    @FXML
    private StackPane messageBlock;

    @FXML
    private HBox messageFromOrToBox;

    @FXML
    private Text messageFromOrToText;

    @FXML
    private VBox messageVBox;

    @FXML
    private HBox moneyAmountBox;

    @FXML
    private Text moneyAmountText;

    @FXML
    private HBox timeBox;

    @FXML
    private Text timeText;

    @FXML
    void initialize() {


    }
    public void setData(Message message){
        boolean toMe = false;
        if(message.getMessageToUserID() == MyUser.getCurrentUser().getUserID()){
            toMe = true;
        }

        if(toMe){
            setLeftMessageStyle(hBoxMain);
        }else {
            setRightMessageStyle(hBoxMain);
        }



        if(message.getMessageType() == MessageType.SIMPLE_MESSAGE){
            messageFromOrToText.setText(message.getMessageFromName());
            messageText.setText("Message: " + message.getMessage());
            messageVBox.getChildren().removeAll(moneyAmountBox, availableMoneyBox);

        }else if (message.getMessageType() == MessageType.TRANSFER_MESSAGE){
            TransferMessage transferMessage = (TransferMessage) message;
            if(toMe){
                moneyAmountText.setText("Пополнение: +"+transferMessage.getMoneyAmount()+"tg");
                messageFromOrToText.setText(transferMessage.getMessageFromName());
                availableMoneyText.setText("Доступно: "+transferMessage.getAvailableMoneyToUser()+"tg");
            }else {
                moneyAmountText.setText("Перевод: -"+transferMessage.getMoneyAmount()+"tg");
                messageFromOrToText.setText(transferMessage.getMessageToName());
                availableMoneyText.setText("Доступно: "+transferMessage.getAvailableMoneyFromUser()+"tg");
            }
            messageText.setText("Message: "+message.getMessage());

        }else if(message.getMessageType() == MessageType.STOCK_SELL_MESSAGE || message.getMessageType() == MessageType.STOCK_BUY_MESSAGE) {
            StockBuySellMessage stockMessage = (StockBuySellMessage) message;
            if(message.getMessageType() == MessageType.STOCK_SELL_MESSAGE ){
                moneyAmountText.setText("Пополнение: +"+stockMessage.getMoneyAmount()+"tg");
            }else {
                moneyAmountText.setText("Куплено: -"+stockMessage.getMoneyAmount()+"tg");
            }
            messageFromOrToText.setText(stockMessage.getMessageFromName());
            messageText.setText("Message: "+stockMessage.getMessage());
            availableMoneyText.setText("Доступно: "+stockMessage.getAvailableMoney()+"tg");
        }

        if (message.getMessage() == null) {
            messageVBox.getChildren().remove(messageBox);
        }
        String hour = message.getMessageHour();
        String minute = message.getMessageMinute();
        if(hour.length()==1){
            hour = "0"+hour;
        }
        if(minute.length()==1){
            minute = "0"+minute;
        }
        timeText.setText(hour+":"+minute);
    }

    public void setRightMessageStyle(HBox vBox){
        vBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }
    public void setLeftMessageStyle(HBox vBox){
        vBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    }


}
