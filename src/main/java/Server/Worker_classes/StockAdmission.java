package Server.Worker_classes;

import Client.Stock.Stock;
import Client.*;
import Client.Stock.StockPriceHistory;
import Server.DataBaseSingleton;
import Server.Server;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StockAdmission implements Worker{
    DataBaseSingleton dataBase;
    PhotoManager photoManager;
    Admission admission;
    public StockAdmission(DataBaseSingleton dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void getServer(Server server) {
        this.photoManager = server.getPhotoManager();
        this.admission = server.getAdmission();
    }

    public void addNewStock(String stockName, String stock_description, int price, File icon) {

        int iconId = photoManager.storeImage(icon);

        int stockID = createUniqueID(stockName,stock_description,price);
        String query = "INSERT INTO Stocks(stock_id,stock_name, stock_price, stock_description,stock_iconid) VALUES (?,?,?,?,?)";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, stockID);
            statement.setString(2, stockName);
            statement.setInt(3, price);
            statement.setString(4, stock_description);
            statement.setInt(5,iconId);
            statement.execute();
            System.out.println("New Stock added :)");
        } catch (SQLException e) {
            System.out.println("Error when addNewStock, "+e.getMessage());
            return;
        }

        this.updateStockHistory(stockID,price);
    }

    private int createUniqueID(String stockName,String stock_description, int price){
        List<Stock> stocks = this.getAllStocks();
        int uniqueID = Objects.hash(stockName,stock_description,price);
        while (true){
            boolean acceptable = true;
            for (Stock stock: stocks) {
                if(stock.getStockID()==uniqueID) {
                    acceptable = false;
                    break;
                }
            }
            if(acceptable)
                return uniqueID;
            uniqueID++;
        }
    }

    public Stock getStockWithID(int stockID){
        String query = "SELECT * FROM Stocks WHERE stock_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1,stockID);
            ResultSet table = statement.executeQuery();

            if(!table.next()){
                System.out.println("Stock with this id doesn't exist!!!");
                statement.close();
                return null;
            }
            return getStockFromResultSet(table);
        } catch (SQLException e) {
            System.out.println("Error when try to get Stock with ID, "+e.getMessage());
        }
        return null;
    }

    public Stock getStockFromResultSet(ResultSet table) throws SQLException {
        int stockID = table.getInt("stock_id");
        String stockName = table.getString("stock_name");
        int stock_price = table.getInt("stock_price");
        String stockDescription = table.getString("stock_description");
        int iconID = table.getInt("stock_iconid");

        return new Stock(stockID, stockName, stock_price, getStockHistory(stockID), getStockInvestorsID(stockID).size(), stockDescription,iconID);
//        return new Stock(stockID, stockName, stock_price, null, 0, stockDescription);
    }

    public List<Stock> getAllStocks(){
        String query = "SELECT * FROM Stocks ORDER BY stock_id";
        ArrayList<Stock> stocks = new ArrayList<>();
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            ResultSet table = statement.executeQuery();
            while (table.next()){
                stocks.add(getStockFromResultSet(table));
            }
        } catch (SQLException e) {
            System.out.println("Some error when try to get all stocks, "+e.getMessage());
        }
        return stocks;
    }

    public Image getStockImage(Stock stock){
        return photoManager.retrieveImage(stock.getStockIconID());
    }

    public boolean addInvestorToStock(int userID, int stockID, int stockCount){
        User user = admission.getUserInfoWithID(userID, false);
        if(user == null){
            return false;
        }
        Stock stock = getStockWithID(stockID);
        if(stock == null){
            return false;
        }

        String query = "SELECT * FROM stockInvestors WHERE user_id = ? AND stock_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            System.out.print("Check, is user before invest this stock?: ");
            statement.setInt(1, userID);
            statement.setInt(2, stockID);
            ResultSet table = statement.executeQuery();

            if(table.next()){
                System.out.println("This user before invests this stock!");
                String query2 = "UPDATE stockInvestors SET stock_count = stock_count + ? WHERE invest_id = ?";
                try(PreparedStatement statement1 = dataBase.getConnection().prepareStatement(query2)){
                    statement1.setInt(1, stockCount);
                    statement1.setInt(2, table.getInt("invest_id"));
                    statement1.execute();
                    System.out.println("user "+user.getUserInfo().getFirstName()+", invested to the stock "+stock.getStockName()+", count of stocks = "+stockCount);
                    return true;
                }catch (SQLException e) {
                    System.out.println("Error when update StockCount, "+ e.getMessage());
                    return false;
                }
            }else {
                System.out.println("This user Before don't invest this stock");
            }
        } catch (SQLException e) {
            System.out.println("Error when try to find suers stock, "+ e.getMessage());
            return false;
        }


        String query3 = "INSERT INTO stockInvestors(stock_id, user_id, stock_count) VALUES(?, ?, ?)";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query3)){
            statement.setInt(1, stockID);
            statement.setInt(2, userID);
            statement.setInt(3, stockCount);
            statement.execute();
            System.out.println("user "+user.getUserInfo().getFirstName()+", invest the stock "+stock.getStockName()+", count od stocks = "+stockCount);
            return true;
        } catch (SQLException e) {
            System.out.println("Error when try add StockInvestor :(, "+ e.getMessage());
            return false;
        }
    }

    public boolean withdrawStockCountFromInvestor(int userID, int stockID, int stockCount){
        String query = "SELECT * FROM StockInvestors WHERE user_id = ? AND stock_id = ?";
        try (PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, userID);
            statement.setInt(2, stockID);
            ResultSet table = statement.executeQuery();

            if(!table.next()){
                System.out.println("Investor does not have the this stock!!");
                return false;
            }
            int usersStockCount = table.getInt("stock_count");
            if(usersStockCount < stockCount){
                System.out.println("you don't have enough stock");
                return false;
            }

            if(usersStockCount == stockCount){
                removeInvestorFromStock(userID, stockID);
                return true;
            }

            String query1 = "UPDATE StockInvestors SET stock_count = stock_count - ? WHERE user_id = ? AND stock_id = ?";
            try(PreparedStatement statement1 = dataBase.getConnection().prepareStatement(query1)){
                statement1.setInt(1, stockCount);
                statement1.setInt(2, userID);
                statement1.setInt(3, stockID);
                statement1.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("Error while withdrawing stock from user, "+e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error while checking usersStock, "+e.getMessage());
        }
        return false;
    }
    public boolean removeInvestorFromStock(int userID, int stockID){
        String query = "DELETE FROM StockInvestors WHERE user_id = ? AND stock_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, userID);
            statement.setInt(2, stockID);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error while removing Investor from Stock, "+e.getMessage());
            return false;
        }

    }

    public List<StockPriceHistory> getStockHistory(int stockID) {
        if(!checkStockID(stockID)){
            return null;
        }

        List<StockPriceHistory> histories = new ArrayList<>();
        String query = "SELECT * FROM stockPriceHistory WHERE stock_id = ? ORDER BY updated_date;";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, stockID);
            ResultSet table = statement.executeQuery();

            while(table.next()){
                Timestamp history_date = table.getTimestamp("updated_date");
                int price = table.getInt("updated_price");

                StockPriceHistory history = new StockPriceHistory(stockID, history_date.toLocalDateTime(), price);
                histories.add(history);
            }
            return histories;

        } catch (SQLException e) {
            System.out.println("Error while trying to get Stock HISTORY, "+e.getMessage());
        }

        return histories;
    }

    public boolean updateStockHistory(int stockID,int newPrice){
        if(!checkStockID(stockID))
            return false;

        String query = "INSERT INTO stockPriceHistory (stock_id,updated_price,updated_date) VALUES (?,?,current_timestamp)";

        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, stockID);
            statement.setInt(2,newPrice);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error while updating Stock HISTORY, "+e.getMessage());
            return false;
        }

        System.out.println("Stock History Updated");
        return true;
    }

    private boolean checkStockID(int stockID) {
        String query = "SELECT * FROM Stocks WHERE stock_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1,stockID);
            ResultSet table = statement.executeQuery();
            if(table.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error while trying to get Stock ID,"+e.getMessage());
        }
        System.out.println("Stock with such id doesn't exist!!!");
        return false;
    }

    public List<Integer> getStockInvestorsID(int stockID) {
        String query = "SELECT * FROM stockInvestors WHERE stock_id = ?";

        List<Integer> investorIDs = new ArrayList<>();

        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, stockID);
            ResultSet table = statement.executeQuery();

            while (table.next()){
                investorIDs.add(table.getInt("user_id"));
            }
            return investorIDs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Stock> getInvestorStocks(int investorID) {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM stockInvestors WHERE user_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, investorID);
            ResultSet table = statement.executeQuery();

            while(table.next()){
                int stockID = table.getInt("stock_id");
                Stock stock = getStockWithID(stockID);
                stock.setStockCount(table.getInt("stock_count"));
                stocks.add(stock);
            }

        } catch (SQLException e) {
            System.out.println("ERROR shen try to get investor stocks, " + e.getMessage());
        }
        return stocks;
    }

}

