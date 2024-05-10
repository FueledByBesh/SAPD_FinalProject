package Client.Queryes;

import java.io.Serializable;

public class TransferQuery implements Serializable {
    int fromUserID;
    int toUserID;
    int moneyAmount;
    String message;

    public TransferQuery(int fromUserID, int toUserID, int moneyAmount, String message) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.moneyAmount = moneyAmount;
        this.message = message;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
