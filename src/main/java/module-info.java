module com.example.final_client_code {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.final_client_code to javafx.fxml;
    exports com.example.final_client_code;
    exports com.example.final_client_code.Controllers;
    opens com.example.final_client_code.Controllers to javafx.fxml;
}