package Client.Stock;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StockPriceHistory implements Serializable {
    int stockID;
    LocalDateTime dateTime;
    int price;

    public StockPriceHistory(int stockID, LocalDateTime historyDate, int price) {
        this.stockID = stockID;
        this.dateTime = historyDate;
        this.price = price;
    }


    public LocalDateTime getHistoryDate() {
        return dateTime;
    }
    public void setHistoryDate(LocalDateTime historyDate) {
        this.dateTime = historyDate;
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
                ", historyDate=" + dateTime +
                ", price=" + price +
                '}';
    }
}
