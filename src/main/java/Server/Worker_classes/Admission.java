package Server.Worker_classes;

import Client.User;
import Server.DataBaseSingleton;
import Server.Server;
import Server.Worker_classes.Messages.MessageAdmission;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Admission implements Worker{
    DataBaseSingleton dataBase;
    MessageAdmission messageSender;
    StockAdmission stockAdmission;
    public Admission(DataBaseSingleton dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void getServer(Server server) {
        this.messageSender = server.getMessageSender();
        this.stockAdmission = server.getStockAdmission();
    }

    public User login(String phone, String password) throws SQLException {
        User user = getUserWithPhone(phone, false);

        if(user == null){
            System.out.println("User with this phone not found!");
            return null;
        }
        if(!password.equals(user.getPassword())){
            System.out.println("Password is not correct!");
            return null;
        }

        return user;
    }
    public String signUp(String phone, String password, String firstName, String lastName, String email, LocalDate birthDate) {
        User user = getUserWithPhone(phone, false);
        if(user != null){
            return "User with this phone number is already exist!";
        }
        String query = "INSERT INTO Users(phone_number, password, first_name, last_name, email, birth_date) VALUES (?,?,?,?,?,?);";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setString(1, phone);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, email);

            Date sqlDate = Date.valueOf(birthDate);
            System.out.println(sqlDate);

            statement.setDate(6, sqlDate);

            int updateRowsCount = statement.executeUpdate();
            statement.close();
            if(updateRowsCount > 0){
                return "Correct";
            }
            return "ERROR!";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User getUserWithPhone(String phone, boolean isFullInfo) {
        String query = "SELECT * FROM Users WHERE phone_number = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setString(1,phone);
            ResultSet table = statement.executeQuery();
            if(table.next()){
                if(isFullInfo){
                    return returnAllInfoFromResultSet(table);
                }else {
                    return returnNotAllInfoFromResultSet(table);
                }
            }else {
                System.out.println("Phone not found!");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User getUserInfoWithID(int userID, boolean isFullInfo){
        String query = "SELECT * FROM Users WHERE user_id = ?";
        try(PreparedStatement statement = dataBase.getConnection().prepareStatement(query)){
            statement.setInt(1, userID);
            ResultSet table = statement.executeQuery();
            if(table.next()){
                if(isFullInfo){
                    return returnAllInfoFromResultSet(table);
                }else {
                    return returnNotAllInfoFromResultSet(table);
                }
            }else {
                System.out.println("User with this id not found!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error when try get User with user ID");
        }
        return null;
    }

    public User returnNotAllInfoFromResultSet(ResultSet table) throws SQLException {
        int user_id = table.getInt("user_id");
        String phone_number = table.getString("phone_number");
        String password = table.getString("password");
        String email = table.getString("email");
        String firstName = table.getString("first_name");
        String lastName = table.getString("last_name");
        int balance = table.getInt("balance");

        Timestamp birthDateTimeStamp = table.getTimestamp("birth_date");
        LocalDateTime birthDateLocalDateTime = birthDateTimeStamp.toLocalDateTime();
        LocalDate birthDateLocalDate = birthDateLocalDateTime.toLocalDate();

        User user = new User(user_id, phone_number, password, email, firstName, lastName, birthDateLocalDate);
        user.setBalance(balance);
        return user;
    }
    public User returnAllInfoFromResultSet(ResultSet table) throws SQLException {
        // TODO: 16.12.2023 zhazu kerek message stock tan keiyn
        User user = returnNotAllInfoFromResultSet(table);

        user.setMessages(messageSender.getListOfMessagesWithUserID(user.getUserID()));
        user.setStocks(stockAdmission.getInvestorStocks(user.getUserID()));
        return user;
    }

}
