package Server;

import Server.Worker_classes.*;
import Server.Worker_classes.Messages.MessageAdmission;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server {
    private ServerSocket serverSocket;
    private final int port;
    protected DataBaseSingleton database;
    protected Admission admission;
    protected MessageAdmission messageSender;
    protected StockChanger stockChanger;
    protected StockAdmission stockAdmission;
    protected PhotoManager photoManager;
    protected Transfer transfer;

    public Server(int port, int dBPort,String nameDB,String user,String pass) throws IOException, SQLException {
        this.port = port;
        startServer();
        connectToDataBase(dBPort, nameDB, user, pass);
        this.admission = new Admission(database);
        this.messageSender = new MessageAdmission(database);
        this.stockChanger = new StockChanger(database);
        this.stockAdmission = new StockAdmission(database);
        this.photoManager = new PhotoManager(database);
        this.transfer = new Transfer(database);
        giveServerForAllWorkers(this);
    }

    public void giveServerForAllWorkers(Server server){
        this.admission.getServer(this);
        this.messageSender.getServer(this);
        this.stockChanger.getServer(this);
        this.stockAdmission.getServer(this);
        this.photoManager.getServer(this);
        this.transfer.getServer(this);
    }

    public void connectToDataBase(int port,String nameDB,String user,String pass) throws SQLException {
        database = DataBaseSingleton.getInstance(port, nameDB, user, pass);
    }

    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Server is already started!");
    }
    public void stopServer() throws IOException, SQLException {
        System.out.println("--------------------------------------------");
        database.closeConnection();
        System.out.println("Connection to DataBase closed!");
        serverSocket.close();
        System.out.println("Server is already closed!");
        System.out.println("--------------------------------------------");
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }
    public int getPort() {
        return port;
    }

// TODO  --- Get-workers ---
    public Admission getAdmission(){
        return admission;
    }
    public MessageAdmission getMessageSender(){
        return messageSender;
    }
    public StockAdmission getStockAdmission() {
        return stockAdmission;
    }
    public StockChanger getStockChanger() {
        return stockChanger;
    }
    public PhotoManager getPhotoManager() {
        return photoManager;
    }
    public Transfer getTransfer() {
        return transfer;
    }
}
