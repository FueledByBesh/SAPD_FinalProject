package Server;

import Server.Worker_classes.PhotoManager;
import Server.Worker_classes.StockAdmission;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MAIN{
    public static void main(String[] args) {
//        StockAdmission stockAdmission = new StockAdmission(DataBaseSingleton.getInstance(5433,"newBankDB","postgres","olzhas05"));
//        PhotoManager photoManager = new PhotoManager(DataBaseSingleton.getInstance(5433,"newBankDB","postgres","olzhas05"));
//        stockAdmission.addNewStock("NASDAQ Composite Index","blabalbldsjfajsf asdfa",16340);
//        File file = new File("image.jpg");

//        System.out.println(photoManager.storeImage(file));
//        File image = photoManager.retrieveImage(-880419458);
//
//        System.out.println(image.getName());
//        photoManager.removeCache();
//        DataBaseSingleton database;
//        try {
//            database = DataBaseSingleton.getInstance(5433,"newBankDB","postgres","olzhas05");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        String query = "UPDATE Users SET balance = 10000 WHERE user_id=4";
//
//        try(PreparedStatement statement = database.getConnection().prepareStatement(query)){
//            statement.executeQuery();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        Scanner scan = new Scanner(System.in);
//        while (true) {
//            int a = scan.nextInt();
//            if(a==0)
//                break;
//        }
        System.out.println("start");
        Thread.currentThread().interrupt();
        System.out.println("stop");
    }
}
