package Server.Worker_classes;

import Client.Stock.Stock;
import Client.*;
import Client.Stock.StockPriceHistory;
import Server.DataBaseSingleton;
import Server.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void addNewStock(String stockName, String stock_description, int price) {
        String query = "INSERT INTO Stocks(stock_name, stock_price, stock_description) VALUES (?, ?, ?)";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setString(1, stockName);
            statement.setInt(2, price);
            statement.setString(3, stock_description);
            statement.execute();
            System.out.println("New Stock added :)");
        } catch (SQLException e) {
            System.out.println("Error when addNewStock, "+e.getMessage());
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
//        byte[] stockIcon = table.getBytes("stock_icon");

        return new Stock(stockID, stockName, stock_price, getStockHistory(stockID), getStockInvestorsID(stockID).size(), stockDescription);
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
                    System.out.println("user "+user.getUserInfo().getFirstName()+", invest the stock "+stock.getStockName()+", count od stocks = "+stockCount);
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
                System.out.println("you don't have the enough stock");
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
                System.out.println("Error withdraw stock from user, "+e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error when check usersStock, "+e.getMessage());
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
            System.out.println("Error when remove the investorFromStock, "+e.getMessage());
            return false;
        }

    }

    public List<StockPriceHistory> getStockHistory(int stockID) {
        if(!checkStockID(stockID)){
            return null;
        }

        List<StockPriceHistory> histories = new ArrayList<>();
        String query = "SELECT * FROM stockPriceHistory WHERE stock_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, stockID);
            ResultSet table = statement.executeQuery();

            while(table.next()){
                int historyID = table.getInt("history_id");
                Timestamp history_date = table.getTimestamp("history_date");
                int price = table.getInt("price");
                StockPriceHistory history = new StockPriceHistory(stockID, historyID, history_date.toLocalDateTime().toLocalDate(), price);
                histories.add(history);
            }
            return histories;

        } catch (SQLException e) {
            System.out.println("Error when try to get Stock HISTORY, "+e.getMessage());
        }

        return histories;
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
            System.out.println("Error when try to get Stock,"+e.getMessage());
        }
        System.out.println("Stock with this id doesn't exist!!!");
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

