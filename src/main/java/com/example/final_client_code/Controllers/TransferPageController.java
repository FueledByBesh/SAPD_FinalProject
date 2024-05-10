package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Connection;
import Client.Queryes.ServerQueryType;
import Client.Queryes.TransferQuery;
import Client.User;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import com.example.final_client_code.Proxy.UsersProxy;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TransferPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Text commissionText;

    @FXML
    private Text hiddenPhoneNumberText;

    @FXML
    private TextField moneyAmountTextField;

    @FXML
    private Text myBankMoneyCount;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Text recipientNameText;

    @FXML
    private StackPane refreshPane;

    @FXML
    private Button transferButton;

    @FXML
    private TextArea messageTextArea;

    User transferToUser;

    @FXML
    void initialize() {
        addConstrains();
        refreshPage();

        phoneTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue.length() == 10){
                UsersProxy proxy = new UsersProxy();
                String userName = proxy.getUserNameByPhone(newValue);

                recipientNameText.setText(userName);
            }else {
                recipientNameText.setText("null");
                transferToUser = null;
            }
        });

        transferButton.setOnAction(actionEvent -> {
            String phone = phoneTextField.getText().trim();

            String moneyField = moneyAmountTextField.getText().trim();
            int moneyAmount = Integer.parseInt(moneyField.substring(0,moneyField.length()-2));
            String message = messageTextArea.getText().trim();


            System.out.println(phone);
            System.out.println(moneyAmount);

            UsersProxy proxy = new UsersProxy();
            transferToUser = proxy.getFromDataBase(phone);

            if(transferToUser == null){
                Methods.showErrorAlert("ERROR", "User with this id nat found!!!");
                return;
            }

            if(MyUser.getCurrentUser().getBalance() < moneyAmount){
                Methods.showErrorAlert("ERROR", "You don't have enough money to transfer!!!");
                return;
            }

            try{
                System.out.println("Connecting...");
                Connection connection = Connection.getInstance();
                System.out.println("Connected :)");
                ObjectOutputStream out = connection.getOut();
//            System.out.println(out);
                ObjectInputStream in = connection.getIn();
//            System.out.println(in);

                System.out.println("Send type of Query...");
                out.writeInt(ServerQueryType.TRANSFER);
                out.flush();

                TransferQuery transferQuery = new TransferQuery(MyUser.getCurrentUser().getUserID(), transferToUser.getUserID(), moneyAmount, (message.equals("")) ? null : message);
                out.writeObject(transferQuery);
                out.flush();

                boolean isTransferred = in.readBoolean();

                if(isTransferred){
                    Methods.showConfirmAlert("Success", "Transferred money ;)");
                }else {
                    Methods.showErrorAlert("ERROR", "Something Error!!!");
                }
                refreshPage();
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        refreshPane.setOnMouseClicked(mouseEvent -> refreshPage());
    }

    public void refreshPage(){
        MyUser.refreshNotAll();
        myBankMoneyCount.setText(MyUser.getCurrentUser().getBalance()+"tg");
    }

    public void addConstrains(){
        Methods.addNextPage(backButton, "Main_page.fxml");
        Methods.restrictToNumericInput(phoneTextField);
        moneyFieldLogistic(moneyAmountTextField);
    }

    public void moneyFieldLogistic(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", "")+"tg");
            }else {
                textField.setText(newValue+"tg");
            }
            if(newValue.matches(".*t")){
                for (int i = newValue.length()-1; i >= 0 ; i--) {
                    if(newValue.charAt(i) == 't'){
                        textField.setText(newValue.substring(0, i-1)+"tg");
                    }
                }
            }
        });
    }

}
