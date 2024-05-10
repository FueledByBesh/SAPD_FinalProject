package Client.Messages;

import java.io.Serializable;
import java.sql.Timestamp;

public class SimpleBankMessage extends Message implements Serializable {

    public SimpleBankMessage(int messageID, int messageType, Timestamp messageTime, String message, String messageFromName, int messageToUserID, String messageToName) {
        super(messageID, messageType, messageTime, message, messageFromName, messageToUserID, messageToName);
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nSimpleBankMessage{}";
    }
}
