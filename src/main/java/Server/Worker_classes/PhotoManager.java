package Server.Worker_classes;

import Server.DataBaseSingleton;
import Server.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoManager implements Worker {
    DataBaseSingleton dataBase;

    public PhotoManager(DataBaseSingleton dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void getServer(Server server) {

    }

    public void savePhoto(File photoFile) throws SQLException, IOException {
        String sql = "INSERT INTO photos (photo_data) VALUES (?)";

        PreparedStatement statement = dataBase.getConnection().prepareStatement(sql);
        byte[] photoBytes = readPhotoFileBytes(photoFile);
        statement.setBytes(1, photoBytes);
        statement.executeUpdate();
        statement.close();
        System.out.println("Photo added!!");

    }

    public byte[] readPhotoFileBytes(File photoFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(photoFile)) {
            byte[] buffer = new byte[(int) photoFile.length()];
            fis.read(buffer);
            return buffer;
        }
    }

    public byte[] getPhotoFromTable(ResultSet table, String columnName) throws SQLException {
        return table.getBytes(columnName);

    }


}
