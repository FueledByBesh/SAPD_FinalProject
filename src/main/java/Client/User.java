package Client;


import Client.Messages.Message;
import Client.Stock.Stock;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int userID;
    private String phone;
    private String password;
    private String email;
    private int balance;
    private List<Message> messages = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();
    private UserInfo userInfo;


    public User(int userID, String phone, String password, String email,  String firstName, String lastName, LocalDate date) {
        this.userID = userID;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userInfo = new UserInfo(firstName, lastName, date);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getFullName(){
        return getUserInfo().getFirstName()+" "+getUserInfo().getLastname();
    }
    public String getShortenFullName(){
        return getUserInfo().getFirstName()+" "+getUserInfo().getLastname().charAt(0)+".";
    }

    public String getEmail() {
        return email;
    }public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "\nuserID=" + userID +
                ",\nphone='" + phone + '\'' +
                ", \npassword='" + password + '\'' +
                ", \nbalance=" + balance +
                ", \nmessages=" + messages.toString() +
                ", \nstocks=" + stocks.toString() +
                ", \nuserInfo=" + userInfo +
                '}';
    }
}
