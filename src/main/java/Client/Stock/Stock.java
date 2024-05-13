package Client.Stock;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Stock implements Serializable {
    private int stockID;
    private String stockName;
    private int stockPrice;
    private List<StockPriceHistory> priceHistory;
    private int stockInvestorsCount;
    private String stockDescription;
    private int stockCount = 0;
    private int stockIconID;

    public Stock(int stockID, String stockName, int stockPrice, List<StockPriceHistory> priceHistory, int stockInvestorsCount, String stockDescription,int stockIconID) {
        this.stockID = stockID;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.priceHistory = priceHistory;
        this.stockInvestorsCount = stockInvestorsCount;
        this.stockDescription = stockDescription;
        this.stockIconID=stockIconID;
    }

    public String getStockName() {
        return stockName;
    }
    public int getStockPrice() {
        return stockPrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockID=" + stockID +
                ", stockName='" + stockName + '\'' +
                ", stockPrice=" + stockPrice +
                ", priceHistory=" + priceHistory +
                ", stockInvestorsCount=" + stockInvestorsCount +
                ", stockDescription='" + stockDescription + '\'' +
                ", stockCount=" + stockCount +
                ", stockIconID=" + stockIconID +
                '}';
    }

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice = stockPrice;
    }

    public List<StockPriceHistory> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<StockPriceHistory> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public int getStockInvestorsCount() {
        return stockInvestorsCount;
    }

    public void setStockInvestorsCount(int stockInvestorsCount) {
        this.stockInvestorsCount = stockInvestorsCount;
    }

    public int getStockIconID() {
        return stockIconID;
    }

    public void setStockIconID(int stockIconID) {
        this.stockIconID = stockIconID;
    }

    public String getStockDescription() {
        return stockDescription;
    }

    public void setStockDescription(String stockDescription) {
        this.stockDescription = stockDescription;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public int getStockCount() {
        return stockCount;
    }
}
