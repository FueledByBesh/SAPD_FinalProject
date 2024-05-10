package com.example.final_client_code;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.IOException;

public class Methods {
    public static void addToFieldOnFocus(TextField field, Text hiddenText){
        field.focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if (newValue) {
                if(!hiddenText.getStyleClass().contains("unhidden-node")) {
                    hiddenText.getStyleClass().add("unhidden-node");
                }
            } else {
                if(field.getText().trim().equals("")){
                    hiddenText.getStyleClass().remove("unhidden-node");
                }
            }
        }));
    }
    public static void addToFieldOnFocus(DatePicker field, Text hiddenText){
        field.focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if (newValue) {
                if(!hiddenText.getStyleClass().contains("unhidden-node")) {
                    hiddenText.getStyleClass().add("unhidden-node");
                }
            } else {
                System.out.println(field.getValue());
                if(field.getValue() == null){
                    hiddenText.getStyleClass().remove("unhidden-node");
                }
            }
        }));
    }

    public static void addShowPassToCheck(CheckBox checkBox, PasswordField passField, TextField hiddenPassField){
        checkBox.setOnMouseClicked(mouseEvent -> {
            if(checkBox.isSelected()){
                String passValue = passField.getText().trim();
                hiddenPassField.setText(passValue);

                passField.getStyleClass().add("hidden_field");
                passField.setEditable(false);
                passField.setMouseTransparent(true);
                passField.setDisable(true);
                hiddenPassField.getStyleClass().remove("hidden_field");
                hiddenPassField.setMouseTransparent(false);
                hiddenPassField.setEditable(true);
                hiddenPassField.setDisable(false);

//                System.out.println("not hidded"+checkBox.isSelected()+", editable="+passField.editableProperty().getValue()+", mouseTra="+passField.mouseTransparentProperty().getValue());
//                System.out.println("Hidded"+checkBox.isSelected()+", editable="+hiddenPassField.editableProperty().getValue()+", mouseTra="+hiddenPassField.mouseTransparentProperty().getValue());

            }else {
                String passValue = hiddenPassField.getText().trim();
                passField.setText(passValue);

                hiddenPassField.getStyleClass().add("hidden_field");
                hiddenPassField.setMouseTransparent(true);
                hiddenPassField.setEditable(false);
                hiddenPassField.setDisable(true);
                passField.getStyleClass().remove("hidden_field");
                passField.setMouseTransparent(false);
                passField.setEditable(true);
                passField.setDisable(false);
//                System.out.println("not hidded"+checkBox.isSelected()+", editable="+passField.editableProperty().getValue()+", mouseTra="+passField.mouseTransparentProperty().getValue());
//                System.out.println("Hidded"+checkBox.isSelected()+", editable="+hiddenPassField.editableProperty().getValue()+", mouseTra="+hiddenPassField.mouseTransparentProperty().getValue());
            }
        });
    }
    public static void addFocusToButton(Button button){
        button.focusedProperty().addListener((observableValue, aBoolean, newValue) -> {
            if(newValue){
                if(!button.getStyleClass().contains("log-in-button-focus")){
                    button.getStyleClass().add("log-in-button-focus");
                }
            }else {
                button.getStyleClass().remove("log-in-button-focus");
            }
        });
    }

    public static void addNextPage(Pane button, String fxml){
        button.setOnMouseClicked(mouseEvent -> {
            button.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap");

            Stage stage = new Stage();
            stage.setTitle("BankApp!");
            stage.setScene(scene);
            stage.show();
        });
    }
    public static void addNextPage(Button button, String fxml){
        button.setOnMouseClicked(mouseEvent -> {
            button.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap");

            Stage stage = new Stage();
            stage.setTitle("BankApp!");
            stage.setScene(scene);
            stage.show();
        });
    }
    public static void goToNextPage(Button button,String fxml){
        button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//            scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800&display=swap");

        Stage stage = new Stage();
        stage.setTitle("BankApp!");
        stage.setScene(scene);
        stage.show();
    }

    public static void restrictToNumericInput(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public static void showErrorAlert(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }

    public static void showConfirmAlert(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }
    public static String getPhoneFromTextField(TextField textField){
        String text = textField.getText().trim();
        StringBuilder phone = new StringBuilder("");
        for (int i = 0; i < text.length(); i++) {
            if(i==0 && text.charAt(i) == '8'){
                continue;
            }
            if(text.charAt(i) == ' '){
                continue;
            }
            phone.append(text.charAt(i));
        }
        return phone.toString();
    }

}
