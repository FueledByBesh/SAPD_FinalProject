package com.example.final_client_code.Controllers;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import Client.Connection;
import Client.Queryes.RegisterUserQuery;
import Client.Queryes.ServerQueryType;
import com.example.final_client_code.Methods;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class RegisterPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane backPane;

    @FXML
    private DatePicker birthdayDate;

    @FXML
    private TextField emailTextField;

    @FXML
    private Text hiddenEmailText;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Text hiddenBirthDateText;

    @FXML
    private Text hiddenFirstNameText;

    @FXML
    private Text hiddenLastNameText;

    @FXML
    private Text hiddenPasswordText;

    @FXML
    private TextField hiddenPasswordTextField;

    @FXML
    private Text hiddenPhoneText;

    @FXML
    private TextField hiddenRewritePasswordTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField rewritePasswordTextField;

    @FXML
    private CheckBox showPasswordCheckBox;



    @FXML
    void initialize() {
        addConstrains();

        registerButton.setOnAction(actionEvent -> {
            String phone = Methods.getPhoneFromTextField(phoneTextField);
            String firstName = firstNameTextField.getText().trim();
            String lastName = lastNameTextField.getText().trim();


            LocalDate birthDate = birthdayDate.getValue();


            String email = emailTextField.getText().trim();

            String firstPass;
            String secondPass;
            if(showPasswordCheckBox.isSelected()){
                firstPass = hiddenPasswordTextField.getText();
                secondPass = hiddenRewritePasswordTextField.getText();
            }else {
                firstPass = passwordTextField.getText();
                secondPass = rewritePasswordTextField.getText();
            }

            if(phone.equals("") || firstName.equals("") || lastName.equals("") || birthDate.toString().equals("") || email.equals("") || secondPass.equals("") || firstPass.equals("")){
                Methods.showErrorAlert("Empty Field", "Some textFields is Empty!!!");
                return;
            }

            if(!email.contains("@")){
                Methods.showErrorAlert("Incorrect email", "Write correct Email address!!!");
                return;
            }

            if(!firstPass.equals(secondPass)){
                Methods.showErrorAlert("Passwords don't match", "Write same password!!!");
                return;
            }

            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();


            try {
                System.out.println("Connecting...");
                Connection connection = Connection.getInstance();
                System.out.println("Connected :)");
                ObjectOutputStream out = connection.getOut();
                System.out.println(out);
                ObjectInputStream in = connection.getIn();
                System.out.println(in);

                System.out.println("Send type of Query...");
                out.writeInt(ServerQueryType.REGISTER);
                out.flush();

                System.out.println("Send register users data");
                RegisterUserQuery registerUser = new RegisterUserQuery(phone, firstName, lastName, birthDate, email, firstPass);
                out.writeObject(registerUser);

                System.out.println("Waiting boolean answer...");
                boolean isRegistered = in.readBoolean();

                if (isRegistered){
                    Methods.showConfirmAlert("Success", "You've successfully registered :)");
                    phoneTextField.clear();
                    firstNameTextField.clear();
                    lastNameTextField.clear();
                    emailTextField.clear();
                    passwordTextField.clear();
                    hiddenPasswordTextField.clear();
                    rewritePasswordTextField.clear();
                    hiddenRewritePasswordTextField.clear();
                    birthdayDate.setValue(null);
                }else {
                    String error = in.readUTF();
                    Methods.showErrorAlert("ERROR", error);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void addConstrains(){
        Methods.addToFieldOnFocus(phoneTextField, hiddenPhoneText);
        Methods.addToFieldOnFocus(firstNameTextField, hiddenFirstNameText);
        Methods.addToFieldOnFocus(lastNameTextField, hiddenLastNameText);
        Methods.addToFieldOnFocus(birthdayDate, hiddenBirthDateText);
        Methods.addToFieldOnFocus(emailTextField, hiddenEmailText);
        Methods.addToFieldOnFocus(passwordTextField, hiddenPasswordText);
        Methods.addToFieldOnFocus(hiddenPasswordTextField, hiddenPasswordText);
        Methods.addToFieldOnFocus(rewritePasswordTextField, hiddenPasswordText);
        Methods.addToFieldOnFocus(hiddenRewritePasswordTextField, hiddenPasswordText);

//        Methods.addShowPassToCheck(showPasswordCheckBox, rewritePasswordTextField, hiddenRewritePasswordTextField);
//        Methods.addShowPassToCheck(showPasswordCheckBox, passwordTextField, hiddenPasswordTextField);
        Methods.addShowPassToCheck(showPasswordCheckBox,passwordTextField,hiddenPasswordTextField,rewritePasswordTextField,hiddenRewritePasswordTextField);

        Methods.addFocusToButton(registerButton);

        Methods.restrictToNumericInput(phoneTextField);


        Methods.addNextPage(backPane, "First_page.fxml");
    }



}
