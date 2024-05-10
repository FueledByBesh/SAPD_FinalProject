package Server.Worker_classes.Messages.MessageFactory;

import Client.Messages.Message;
import Client.Messages.StockBuySellMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StockBuySellMessageFactory implements MessageFactory {

    @Override
    public Message getMessage(ResultSet table) {
        try{
            int stockMessageID = table.getInt("stock_message_id");
            int messageType = table.getInt("message_type");
            Timestamp messageTime = table.getTimestamp("message_time");
            String message = table.getString("message");
            String messageFromName = table.getString("message_from_name");
            int messageToUserID = table.getInt("message_to_id");
            String messageToName = table.getString("message_to_name");
            int moneyAmount = table.getInt("money_amount");
            int availableMoney = table.getInt("available_money");
            Message newMessage =  new StockBuySellMessage(stockMessageID, messageType, messageTime, message, messageFromName, messageToUserID, messageToName, moneyAmount, availableMoney);
            System.out.println(newMessage);
            return newMessage;
        } catch (SQLException e) {
            System.out.println("Error when try to put table info to StockBuySellMessages value, " + e.getMessage());
            return null;
        }
    }
}
