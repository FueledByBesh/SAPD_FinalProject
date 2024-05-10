package Client.Messages;

import java.io.Serializable;
import java.sql.Timestamp;

public class StockBuySellMessage extends Message implements Serializable {
    private int moneyAmount;
    private int availableMoney;

    public StockBuySellMessage(int messageID, int messageType, Timestamp messageTime, String message, String messageFromName, int messageToUserID, String messageToName, int moneyAmount, int availableMoney) {
        super(messageID, messageType, messageTime, message, messageFromName, messageToUserID, messageToName);
        this.moneyAmount = moneyAmount;
        this.availableMoney = availableMoney;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(int availableMoney) {
        this.availableMoney = availableMoney;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nStockBuySellMessage{" +
                "moneyAmount=" + moneyAmount +
                ", availableMoney=" + availableMoney +
                '}';
    }
}
