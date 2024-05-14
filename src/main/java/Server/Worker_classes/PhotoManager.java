package Server.Worker_classes;

import Server.DataBaseSingleton;
import Server.Server;
import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PhotoManager implements Worker {
    DataBaseSingleton dataBase;

    public PhotoManager(DataBaseSingleton dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void getServer(Server server) {

    }

    public Integer storeImage(File photoFile) {
        Image image;
        int id = createUniqueID(photoFile);
        String[] name = photoFile.getName().split("\\.");
        String fileName = id+"image."+name[name.length-1];

        String sql = "INSERT INTO Images (image_id,image_name,image) VALUES (?,?,?)";
        try(
                PreparedStatement statement = dataBase.getConnection().prepareStatement(sql);
                FileInputStream fis = new FileInputStream(photoFile);
        ) {
            statement.setInt(1, id);
            statement.setString(2,fileName);
            statement.setBinaryStream(3, fis,(int) photoFile.length());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("--> PhotoManager.savePhoto --> Something get wrong with sql :(");
            return null;
        } catch (IOException e) {
            System.out.println("--> PhotoManager.savePhoto --> Something get wrong with java :(");
            return null;
        }

        System.out.println("Photo added!!");
        return id;
    }

    public void ImageToCache(int imageID){
        File file = new File("cache/images/"+imageID+"image.png");
        if(file.exists())
            return;
        String sql = "SELECT * FROM Images WHERE image_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(sql);){
            statement.setInt(1,imageID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("image_name");
                byte[] data = resultSet.getBytes("image");
                file = new File("cache/images/"+name);

                try (OutputStream fos = new FileOutputStream(file)) {
                    fos.write(data);
                }catch (IOException e){
                    throw new IOException();
                }
            }
        } catch (SQLException e) {
            System.out.println("--> PhotoManager.ImageToCache --> something get wrong with sql");
        } catch (IOException e) {
            System.out.println("--> PhotoManager.ImageToCache --> something get wrong with IO");
        }

    }

    private int createUniqueID(File file){
        int id = Objects.hash(file);
        Set<Integer> ids = getAllImageID();
        while(true) {
            if (ids.contains(id)){
                id++;
                continue;
            }
            return id;
        }
    }

//    public byte[] readPhotoFileBytes(File photoFile) throws IOException {
//        try (FileInputStream fis = new FileInputStream(photoFile)) {
//            byte[] buffer = new byte[(int) photoFile.length()];
//            fis.read(buffer);
//            return buffer;
//        }
//    }

//    public byte[] getPhotoFromTable(ResultSet table, String columnName) throws SQLException {
//        return table.getBytes(columnName);
//    }

    public Set<Integer> getAllImageID(){
        Set<Integer> IDs = new HashSet<>();

        String query = "SELECT image_id FROM Images";
        try (PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                IDs.add(resultSet.getInt("image_id"));
            }
            resultSet.close();

        } catch (SQLException e) {
            System.out.println("--> PhotoManager.getAllImageID --> Someting get wrong while obtaining image ids!!!");
        }


        return IDs;
    }

    public void removeCache(){
        String directoryPath = "cache/images";

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path path : directoryStream) {
                Files.delete(path);
                System.out.println("Deleted file: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renameFile(String oldFileName,String newFileName){
        String path = "cache/images/";
        Path oldPath = Paths.get(path+oldFileName);
        Path newPath = Paths.get(path+newFileName);

        try {
            Files.move(oldPath, newPath);
            System.out.println("File renamed successfully!");
        } catch (IOException e) {
            System.err.println("Failed to rename file: " + e.getMessage());
        }

    }

}
