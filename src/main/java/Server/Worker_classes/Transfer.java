package Server.Worker_classes;

import Client.Stock.Stock;
import Client.User;
import Server.DataBaseSingleton;
import Server.Server;
import Server.Worker_classes.Messages.MessageAdmission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transfer implements Worker{
    DataBaseSingleton dataBase;
    Admission admission;
    MessageAdmission messageSender;
    StockAdmission stockAdmission;

    public Transfer(DataBaseSingleton dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void getServer(Server server) {
        this.admission = server.getAdmission();
        this.messageSender = server.getMessageSender();
        this.stockAdmission = server.getStockAdmission();
    }

    public boolean transferTo(int transferFromID, int transferToID, int moneyAmount, String message) {
        User fromUser = this.admission.getUserInfoWithID(transferFromID, false);
        User toUser  = this.admission.getUserInfoWithID(transferToID, false);
        if(fromUser == null || toUser == null){
            return false;
        }

        if(fromUser.getBalance() < moneyAmount){
            System.out.println("Users don't have enough money to transfer");
            return false;
        }

        String query = "INSERT INTO transfers(transfer_from_id, transfer_from_phone, transfer_from_name, transfer_to_id, transfer_to_phone, transfer_to_name, money_amount, message,transfer_date) " +
                "VALUES (?, ? ,?, ?, ?, ?, ?, ?, current_timestamp)";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)) {
            statement.setInt(1, transferFromID);
            statement.setString(2, fromUser.getPhone());
            statement.setString(3, fromUser.getUserInfo().getFirstName()+" "+fromUser.getUserInfo().getLastname().charAt(0)+".");
            statement.setInt(4, transferToID);
            statement.setString(5, toUser.getPhone());
            statement.setString(6, toUser.getUserInfo().getFirstName()+" "+toUser.getUserInfo().getLastname().charAt(0)+".");
            statement.setInt(7, moneyAmount);
            statement.setString(8, message);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("transfer error, "+e.getMessage());
            return false;
        }
        // TODO: 31.12.2023 (Message sends auto in SQL)!!! -> PS: бля нахуя?
    }

    public boolean buyStock(int userID, int stockID, int stockCount){
        User user = this.admission.getUserInfoWithID(userID, false);
        Stock stock = this.stockAdmission.getStockWithID(stockID);

        if(user == null || stock == null){
            System.out.println("Stock or user not found!!!");
            return false;
        }

        int newBalance = user.getBalance()-(stockCount*stock.getStockPrice());

        if(newBalance<0)
            newBalance=0;

        String query = "UPDATE Users SET balance = ? WHERE user_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, newBalance);
            statement.setInt(2, userID);

            statement.execute();
        } catch (SQLException e) {
            System.out.println("--> Transfer.buyStock --> Error while updating balance");
            return false;
        }

        System.out.println("Stock bought !!!");
        stockAdmission.addInvestorToStock(userID, stockID, stockCount);
        messageSender.sendStockBuyMessage(userID, stockID,"You've successfully bought " +stockCount+ " stock on '"+stock.getStockName()+ "'",stockCount*stock.getStockPrice());
        return true;

        // TODO: 31.12.2023 Birinshi add investor to stock zhasau kerek potom SQL tranzakciamen aksha alym message zhiberemiz
        // TODO: i tura solai sellStock pen !!!
        
    }
    public boolean sellStock(int userID, int stockID, int stockCount){
        User user = this.admission.getUserInfoWithID(userID, false);
        Stock stock = this.stockAdmission.getStockWithID(stockID);

        if(user == null || stock == null){
            System.out.println("Stock or user not found!!!");
            return false;
        }

        int newBalance = user.getBalance()+(stockCount*stock.getStockPrice());

        String query = "UPDATE Users SET balance = ? WHERE user_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, newBalance);
            statement.setInt(2, userID);

            statement.execute();
        } catch (SQLException e) {
            System.out.println("--> Transfer.sellStock --> Error while updating balance");
            return false;
        }

        boolean isWithdraw = stockAdmission.withdrawStockCountFromInvestor(userID, stockID, stockCount);
        if(isWithdraw){
            messageSender.sendStockSellMessage(userID, stockID,"You have successfully sold stock '"+stock.getStockName()+ "', count of stock = "+stockCount,stockCount*stock.getStockPrice());
            return true;
        }
        return false;

    }
}
