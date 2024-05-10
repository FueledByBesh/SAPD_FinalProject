package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.final_client_code.HelloApplication;
import com.example.final_client_code.Methods;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FirstPageController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignUpButton;

    @FXML
    private AnchorPane registerButton;

    @FXML
    void initialize() {

        Methods.addNextPage(SignUpButton, "Sign_in_page.fxml");
        Methods.addNextPage(registerButton, "Register_page.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }
}
