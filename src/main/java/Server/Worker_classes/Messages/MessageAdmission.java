package Server.Worker_classes.Messages;

import Client.Messages.Message;
import Server.DataBaseSingleton;
import Server.Server;
import Server.Worker_classes.Admission;
import Server.Worker_classes.Messages.MessageSenders.SimpleBankMessageSender;
import Server.Worker_classes.Messages.MessageSenders.StockBuySellMessageSender;
import Server.Worker_classes.Messages.MessageSenders.TransferMessageSender;
import Server.Worker_classes.StockAdmission;
import Server.Worker_classes.Worker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class MessageAdmission implements Worker {
    private DataBaseSingleton dataBase;
    private Admission admission;
    private StockAdmission stockAdmission;
    private TransferMessageSender transferMessageSender;
    private StockBuySellMessageSender stockBuySellMessageSender;
    private SimpleBankMessageSender simpleBankMessageSender;


    public MessageAdmission(DataBaseSingleton dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void getServer(Server server) {
        this.admission = server.getAdmission();
        this.stockAdmission = server.getStockAdmission();
        this.transferMessageSender = new TransferMessageSender(admission, stockAdmission, dataBase);
        this.stockBuySellMessageSender = new StockBuySellMessageSender(admission, stockAdmission, dataBase);
        this.simpleBankMessageSender = new SimpleBankMessageSender(admission, stockAdmission, dataBase);

        stockBuySellMessageSender.setNextSender(simpleBankMessageSender);
        transferMessageSender.setNextSender(stockBuySellMessageSender);
    }

    public void sendTransferMessage(int fromUserID, int toUserID, String message, int money_amount){
        transferMessageSender.sendTransferMessage(fromUserID, toUserID, message, money_amount);
    }

    public void sendSimpleMessage(int toUserID, String fromName, String message){
        simpleBankMessageSender.sendSimpleMessage(toUserID, fromName, message);
    }

    public void sendStockBuyMessage(int toUserID, int stockID, String message, int money_amount){
        stockBuySellMessageSender.sendStockBuyMessage(toUserID, stockID, message, money_amount);
    }

    // TODO: 20.12.2023
    public void sendStockSellMessage(int toUserID, int stockID, String message, int money_amount){
        stockBuySellMessageSender.sendStockSellMessage(toUserID, stockID, message, money_amount);
    }

    public ArrayList<Message> getListOfMessagesWithUserID(int id) {

        return transferMessageSender.getMessage(new ArrayList<>(), id);
    }


    public DataBaseSingleton getDataBase() {
        return dataBase;
    }

    public Admission getAdmission() {
        return admission;
    }

    public StockAdmission getStockAdmission() {
        return stockAdmission;
    }
}
