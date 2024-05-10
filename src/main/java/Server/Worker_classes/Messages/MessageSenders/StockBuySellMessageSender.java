package Server.Worker_classes.Messages.MessageSenders;

import Client.Stock.Stock;
import Client.User;
import Server.DataBaseSingleton;
import Server.Worker_classes.Admission;
import Server.Worker_classes.Messages.MessageType;
import Server.Worker_classes.StockAdmission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockBuySellMessageSender extends MessageSender {

    public StockBuySellMessageSender(Admission admission, StockAdmission stockAdmission, DataBaseSingleton dataBase) {
        super(admission, stockAdmission, dataBase);
    }

    public void sendStockBuyMessage(int toUserID, int stockID, String message, int money_amount){
        User user = this.admission.getUserInfoWithID(toUserID, false);
        Stock stock = this.stockAdmission.getStockWithID(stockID);
        String query = "INSERT INTO StockBuySellMessages(message_type, message, message_from_name, message_to_id, message_to_name, money_amount)\n" +
                "VALUES (?,?,?,?,?,?);";//6
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, MessageType.STOCK_BUY_MESSAGE);
            statement.setString(2, message);
            statement.setString(3, stock.getStockName());
            statement.setInt(4, toUserID);
            statement.setString(5, user.getUserInfo().getFirstName()+" "+user.getUserInfo().getLastname());
            statement.setInt(6, money_amount);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error when try to send about Buy Stock message to user "+toUserID+", "+e.getMessage());
        }
    }
    public void sendStockSellMessage(int toUserID, int stockID, String message, int money_amount){
        User user = this.admission.getUserInfoWithID(toUserID, false);
        Stock stock = this.stockAdmission.getStockWithID(stockID);
        String query = "INSERT INTO StockBuySellMessages(message_type, message, message_from_name, message_to_id, message_to_name, money_amount)\n" +
                "VALUES (?,?,?,?,?,?);";//6
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, MessageType.STOCK_SELL_MESSAGE);
            statement.setString(2, message);
            statement.setString(3, stock.getStockName());
            statement.setInt(4, toUserID);
            statement.setString(5, user.getUserInfo().getFirstName()+" "+user.getUserInfo().getLastname());
            statement.setInt(6, money_amount);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error when try to send about Sell Stock message to user "+toUserID+", "+e.getMessage());
        }
    }


    @Override
    public String getQuery() {
        return "SELECT * FROM StockBuySellMessages WHERE message_to_id = ?";
    }

}
