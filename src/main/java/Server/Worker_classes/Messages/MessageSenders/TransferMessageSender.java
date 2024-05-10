package Server.Worker_classes.Messages.MessageSenders;

import Client.Messages.Message;
import Client.User;
import Server.DataBaseSingleton;
import Server.Worker_classes.Admission;
import Server.Worker_classes.Messages.MessageType;
import Server.Worker_classes.StockAdmission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransferMessageSender extends MessageSender {

    public TransferMessageSender(Admission admission, StockAdmission stockAdmission, DataBaseSingleton dataBase) {
        super(admission, stockAdmission, dataBase);
    }

    public void sendTransferMessage(int fromUserID, int toUserID, String message, int money_amount){
        User fromUser = super.admission.getUserInfoWithID(fromUserID, false);
        User toUser = super.admission.getUserInfoWithID(toUserID, false);
        String query = "INSERT INTO TransferMessages(message_type, message, message_from_id, message_from_name, message_to_id, message_to_name, money_amount) " +
                "VALUES(?,?,?,?,?,?,?)";//7
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, MessageType.TRANSFER_MESSAGE);
            statement.setString(2, message);
            statement.setInt(3, fromUserID);
            statement.setString(4, fromUser.getUserInfo().getFirstName()+" "+fromUser.getUserInfo().getLastname().charAt(0)+".");
            statement.setInt(5, toUserID);
            statement.setString(6, toUser.getUserInfo().getFirstName()+" "+toUser.getUserInfo().getLastname().charAt(0)+".");
            statement.setInt(7, money_amount);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error when try to send transfer message to user "+toUserID+", "+e.getMessage());
        }
    }

    @Override
    public String getQuery() {
        return  "SELECT * FROM TransferMessages WHERE message_to_id = ? OR message_from_id = ?";
    }

    @Override
    public ArrayList<Message> getMessage(ArrayList<Message> messages, int userID) {
        String query = getQuery();
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, userID);
            statement.setInt(2, userID);
            ResultSet table = statement.executeQuery();
            if(table.next()){
                do{
                    putMessagesFromResultSet(messages,table);
                    System.out.println("hi");
                } while (table.next());
            }
            if(nextSender == null){
                return messages;
            }
            return nextSender.getMessage(messages, userID);
        } catch (SQLException e) {
            System.out.println("Error when try to get Stock messages from user "+userID+", " + e.getMessage());
        }
        return messages;
    }
}
