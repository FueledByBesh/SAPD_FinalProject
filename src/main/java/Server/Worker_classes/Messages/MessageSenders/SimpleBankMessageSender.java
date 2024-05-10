package Server.Worker_classes.Messages.MessageSenders;

import Client.User;
import Server.DataBaseSingleton;
import Server.Worker_classes.Admission;
import Server.Worker_classes.Messages.MessageType;
import Server.Worker_classes.StockAdmission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimpleBankMessageSender extends MessageSender {

    public SimpleBankMessageSender(Admission admission, StockAdmission stockAdmission, DataBaseSingleton dataBase) {
        super(admission, stockAdmission, dataBase);
    }

    public void sendSimpleMessage(int toUserID, String fromName, String message){
        User toUser = this.admission.getUserInfoWithID(toUserID, false);
        String query = "INSERT INTO SimpleBankMessages(message_type, message, message_from_name, message_to_id, message_to_name) " +
                "VALUES(?,?,?,?,?)";//5
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, MessageType.SIMPLE_MESSAGE);
            statement.setString(2, message);
            statement.setString(3, fromName);
            statement.setInt(4, toUserID);
            statement.setString(5, toUser.getUserInfo().getFirstName()+" "+toUser.getUserInfo().getLastname().charAt(0)+".");
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error when try to send simple message to user "+toUserID+", "+e.getMessage());
        }
    }

    @Override
    public String getQuery() {
        return  "SELECT * FROM SimpleBankMessages WHERE message_to_id = ?";
    }

}
