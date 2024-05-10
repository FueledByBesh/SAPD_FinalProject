package Client.Stock;

import java.io.Serializable;
import java.time.LocalDate;

public class StockPriceHistory implements Serializable {
    int stockID;
    int historyID;
    LocalDate historyDate;
    int price;

    public StockPriceHistory(int stockID,int historyID, LocalDate historyDate, int price) {
        this.stockID = stockID;
        this.historyID = historyID;
        this.historyDate = historyDate;
        this.price = price;
    }

    public int getHistoryID() {
        return historyID;
    }public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public LocalDate getHistoryDate() {
        return historyDate;
    }public void setHistoryDate(LocalDate historyDate) {
        this.historyDate = historyDate;
    }

    public int getPrice() {
        return price;
    }public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "StockPriceHistory{" +
                "stockID=" + stockID +
                ", historyID=" + historyID +
                ", historyDate=" + historyDate +
                ", price=" + price +
                '}';
    }
}
