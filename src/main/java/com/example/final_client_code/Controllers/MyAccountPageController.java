package com.example.final_client_code.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MyAccountPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView accountImage;

    @FXML
    private StackPane backPane;

    @FXML
    private Text bigNameSurname;

    @FXML
    private Button changeInfoButton;

    @FXML
    private Hyperlink forgotPassLink;

    @FXML
    private Text infoBalanceTExt;

    @FXML
    private Text infoBirthDateText;

    @FXML
    private Text infoEmailText;

    @FXML
    private Text infoNameText;

    @FXML
    private Text infoPhoneText;

    @FXML
    private Button logOutButton;

    @FXML
    private Text myBankMoneyCount;

    @FXML
    private StackPane refreshPane;

    @FXML
    private AnchorPane root;

    @FXML
    void initialize() {
        addConstrains();
        refreshPane.setOnMouseClicked(mouseEvent -> {
            refreshPage();
        });
    }
    public void addConstrains(){
        refreshPage();
        Methods.addNextPage(backPane, "Main_page.fxml");
    }
    public void refreshPage(){
        MyUser.refreshNotAll();
        bigNameSurname.setText(MyUser.getCurrentUser().getShortenFullName());
        myBankMoneyCount.setText(MyUser.getCurrentUser().getBalance()+"tg");
        infoNameText.setText(MyUser.getCurrentUser().getFullName());
        infoPhoneText.setText(MyUser.getCurrentUser().getPhone());
        infoEmailText.setText(MyUser.getCurrentUser().getEmail());
        infoBalanceTExt.setText(MyUser.getCurrentUser().getBalance()+"tg");
        infoBirthDateText.setText(MyUser.getCurrentUser().getUserInfo().getBirthDate().toString());
    }

}

