package Client.Messages;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransferMessage extends Message implements Serializable {
    private int messageFromUserID;
    private int moneyAmount;
    private int availableMoneyFromUser;
    private int availableMoneyToUser;

    public TransferMessage(int messageID, int messageType, Timestamp messageTime, String message, int messageFromUserID, String messageFromName, int messageToUserID, String messageToName, int moneyAmount, int availableMoneyFromUser, int availableMoneyToUser) {
        super(messageID, messageType, messageTime, message, messageFromName, messageToUserID, messageToName);
        this.messageFromUserID = messageFromUserID;
        this.moneyAmount = moneyAmount;
        this.availableMoneyFromUser = availableMoneyFromUser;
        this.availableMoneyToUser = availableMoneyToUser;
    }

    public int getMessageFromUserID() {
        return messageFromUserID;
    }

    public void setMessageFromUserID(int messageFromUserID) {
        this.messageFromUserID = messageFromUserID;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getAvailableMoneyFromUser() {
        return availableMoneyFromUser;
    }

    public void setAvailableMoneyFromUser(int availableMoneyFromUser) {
        this.availableMoneyFromUser = availableMoneyFromUser;
    }

    public int getAvailableMoneyToUser() {
        return availableMoneyToUser;
    }

    public void setAvailableMoneyToUser(int availableMoneyToUser) {
        this.availableMoneyToUser = availableMoneyToUser;
    }

    @Override
    public String toString() {
        return super.toString()
                +"\nTransferMessage{" +
                "messageFromUserID=" + messageFromUserID +
                ", moneyAmount=" + moneyAmount +
                ", availableMoneyFromUser=" + availableMoneyFromUser +
                ", availableMoneyToUser=" + availableMoneyToUser +
                '}';
    }
}
