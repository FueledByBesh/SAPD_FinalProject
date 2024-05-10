package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

import Client.Messages.Message;
import Client.Messages.StockBuySellMessage;
import Client.Messages.TransferMessage;
import Server.Worker_classes.Messages.MessageType;
import com.example.final_client_code.HelloApplication;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessagesPageController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane backPane;

    @FXML
    private FlowPane messagesFlowPane;

    @FXML
    private StackPane refreshPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void initialize() {
        Methods.addNextPage(backPane, "Main_page.fxml");

        refreshPane.setOnMouseClicked(mouseEvent -> {
            refreshPage();
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMessages();
        initialize();
    }

    public void refreshPage(){
        messagesFlowPane.getChildren().clear();
        loadMessages();
        initialize();
    }

    public void loadMessages(){
        MyUser.refreshUserMessages();
        MyUser.getCurrentUser().getMessages().sort((o1, o2) -> {
            if(o1.getMessageTime().compareTo(o2.getMessageTime()) > 0){
                return 1;
            } else if (o1.getMessageTime().compareTo(o2.getMessageTime()) < 0) {
                return -1;
            }else {
                return 0;
            }
        });

//        for (Message message : MyUser.getCurrentUser().getMessages()) {
//            System.out.println(message.getMessageTime());
//        }
        try{
            System.out.println("load messages");
            for (Message message : MyUser.getCurrentUser().getMessages()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
//                System.out.println(fxmlLoader);
                fxmlLoader.setLocation(HelloApplication.class.getResource(("MessageBlock.fxml")));

                HBox hBox = fxmlLoader.load();

                MessageBlockController controller = fxmlLoader.getController();
                controller.setData(message);

                messagesFlowPane.getChildren().add(hBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scrollPane.setVvalue(1);

    }


}
