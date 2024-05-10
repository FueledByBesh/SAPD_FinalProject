package Client.Queryes;

import java.io.Serializable;

public class SellStockQuery implements Serializable {
    private int userID;
    private int stockID;
    private int stockCount;

    public SellStockQuery(int userID, int stockID, int stockCount) {
        this.userID = userID;
        this.stockID = stockID;
        this.stockCount = stockCount;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    @Override
    public String toString() {
        return "BuyQuery{" +
                "userID=" + userID +
                ", stockID=" + stockID +
                ", stockCount=" + stockCount +
                '}';
    }
}
