package Server.Worker_classes.Messages.MessageFactory;

import Client.Messages.Message;
import Client.Messages.TransferMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransferMessageFactory implements MessageFactory {

    @Override
    public Message getMessage(ResultSet table) {
        try{
            int stockMessageID = table.getInt("transfer_message_id");
            int messageType = table.getInt("message_type");
            Timestamp messageTime = table.getTimestamp("message_time");
            String message = table.getString("message");
            int messageFromID = table.getInt("message_from_id");
            String messageFromName = table.getString("message_from_name");
            int messageToUserID = table.getInt("message_to_id");
            String messageToName = table.getString("message_to_name");
            int moneyAmount = table.getInt("money_amount");
            int availableMoneyFromUser = table.getInt("available_money_from_user");
            int availableMoneyToUser = table.getInt("available_money_to_user");
            Message newMessage = new TransferMessage(stockMessageID, messageType, messageTime, message, messageFromID, messageFromName, messageToUserID, messageToName, moneyAmount, availableMoneyFromUser, availableMoneyToUser);
            System.out.println(newMessage);
            return newMessage;
        } catch (SQLException e) {
            System.out.println("Error when try to put table info to StockBuySellMessages value, " + e.getMessage());
            return null;
        }
    }
}
