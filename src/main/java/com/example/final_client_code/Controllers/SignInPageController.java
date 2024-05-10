package com.example.final_client_code.Controllers;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import Client.*;
import Client.Queryes.LoginUserQuery;
import Client.Queryes.ServerQueryType;
import com.example.final_client_code.Methods;
import com.example.final_client_code.MyUser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SignInPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInButton;

    @FXML
    private StackPane backPane;

    @FXML
    private Text hiddenPasswordText;

    @FXML
    private Text hiddenPhoneText;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField hiddenPasswordTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    void initialize() {
        addConstrains();

        logInButton.setOnAction(actionEvent -> {
            try {

                String phone = Methods.getPhoneFromTextField(phoneTextField);
                String password = passwordTextField.getText().trim();

                System.out.println(phone);
                System.out.println(password);
                if(phone.equals("") || password.equals("")){
                    Methods.showErrorAlert("Empty fields", "Phone and password can't be empty!!!");
                    return;
                }

                System.out.println("Try to send Login query...");
                Connection connection = Connection.getInstance();
                ObjectOutputStream out = connection.getOut();
                ObjectInputStream in = connection.getIn();

                out.writeInt(ServerQueryType.LOG_IN);
                out.flush();

                LoginUserQuery logInUser = new LoginUserQuery(phone, password);
                out.writeObject(logInUser);
                System.out.println(logInUser);

                User user = (User)in.readObject();
                System.out.println(user);
                if(user==null){
                    Methods.showErrorAlert("Error", "Phone or Password is not correct!!!");
                    return;
                }
                MyUser.setCurrentUser(user);
                System.out.println("| Login finished |");

                Methods.goToNextPage(logInButton,"Main_page.fxml");

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void addConstrains(){
        Methods.addToFieldOnFocus(phoneTextField,hiddenPhoneText);
        Methods.addToFieldOnFocus(passwordTextField,hiddenPasswordText);
        Methods.addToFieldOnFocus(hiddenPasswordTextField,hiddenPasswordText);
        Methods.addShowPassToCheck(showPasswordCheckBox, passwordTextField, hiddenPasswordTextField);
        Methods.restrictToNumericInput(phoneTextField);
        Methods.addFocusToButton(logInButton);
        Methods.addNextPage(backPane, "First_page.fxml");
    }


}
