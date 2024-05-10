package Server.Worker_classes.Messages.MessageFactory;

import Client.Messages.Message;
import Client.Messages.SimpleBankMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SimpleBankMessageFactory implements MessageFactory {

    @Override
    public Message getMessage(ResultSet table) {
        try{
            int simpleMessageID = table.getInt("simple_message_id");
            int messageType = table.getInt("message_type");
            Timestamp messageTime = table.getTimestamp("message_time");
            String message = table.getString("message");
            String messageFromName = table.getString("message_from_name");
            int messageToUserID = table.getInt("message_to_id");
            String messageToName = table.getString("message_to_name");
            Message newMessage = new SimpleBankMessage(simpleMessageID, messageType, messageTime, message, messageFromName, messageToUserID, messageToName);
            System.out.println(newMessage);
            return newMessage;
        } catch (SQLException e) {
            System.out.println("Error when try to put table info to SimpleMessages value, " + e.getMessage());
            return null;
        }
    }
}
