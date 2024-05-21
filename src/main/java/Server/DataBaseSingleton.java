package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseSingleton {
    private static DataBaseSingleton instance;
    private final Connection connection;

    private DataBaseSingleton(int port,String nameDB,String user,String pass) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:"+port+"/"+nameDB, user, pass);
    }

    public static DataBaseSingleton getInstance(int port,String nameDB,String user,String pass) throws SQLException {
        if(instance == null){
            synchronized (DataBaseSingleton.class){
                if(instance == null){
                    instance = new DataBaseSingleton(port, nameDB, user, pass);
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
