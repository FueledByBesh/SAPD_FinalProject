package Client.Stock;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Stock implements Serializable {
    int stockID;
    String stockName;
    int stockPrice;
    List<StockPriceHistory> priceHistory;
    int stockInvestorsCount;
    String stockDescription;
    int stockCount = 0;
    byte[] stockIcon;

    public Stock(int stockID, String stockName, int stockPrice, List<StockPriceHistory> priceHistory, int stockInvestorsCount, String stockDescription) {
        this.stockID = stockID;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.priceHistory = priceHistory;
        this.stockInvestorsCount = stockInvestorsCount;
        this.stockDescription = stockDescription;
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
                ", stockIcon=" + Arrays.toString(stockIcon) +
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

    public byte[] getStockIcon() {
        return stockIcon;
    }

    public void setStockIcon(byte[] stockIcon) {
        this.stockIcon = stockIcon;
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
