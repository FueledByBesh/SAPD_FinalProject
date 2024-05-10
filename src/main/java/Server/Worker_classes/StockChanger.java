package Server.Worker_classes;

import Client.Stock.Stock;
import Server.*;
import Server.DataBaseSingleton;
import Server.Server;
import Server.Worker_classes.Messages.MessageAdmission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StockChanger implements Worker{
    DataBaseSingleton dataBase;
    MessageAdmission messageSender;
    Admission admission;
    StockAdmission stockAdmission;

    public StockChanger(DataBaseSingleton dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void getServer(Server server) {
        this.messageSender = server.getMessageSender();
        this.admission = server.getAdmission();
        this.stockAdmission = server.getStockAdmission();
    }

    public void changePrice(int stockID, int newPrice) throws SQLException {
        if(newPrice <= 0){
            return;
        }

        Stock stock = stockAdmission.getStockWithID(stockID);
        if(stock==null){
            return;
        }

        String query = "UPDATE Stocks SET stock_price = ? WHERE stock_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, newPrice);
            statement.setInt(2, stockID);
            statement.execute();
            System.out.println("Stock prise changed");
        } catch (SQLException e) {
            return;
        }

        List<Integer> investors = stockAdmission.getStockInvestorsID(stockID);

        for (Integer investor : investors) {
            this.messageSender.sendSimpleMessage(investor, stock.getStockName(),
                    "Your stock '"+stock.getStockName()+"', was change the price to = "+newPrice);
        }
        System.out.println("All investors was notify ;)");
//        messageSender

    }






}
