package com.example.final_client_code;

import Client.Connection;
import Client.Messages.Message;
import Client.Queryes.ServerQueryType;
import Client.Stock.Stock;
import Client.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MyUser {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void refreshAll(){
        try {
            System.out.println("Refresh All Info In MyUser...");
            Connection connection = Connection.getInstance();
            System.out.println(connection.getSocket().isConnected());
            ObjectOutputStream out = connection.getOut();
//            System.out.println(out);
            ObjectInputStream in = connection.getIn();
//            System.out.println(in);

            out.writeInt(ServerQueryType.GET_USER_ALL_INFO);
            out.flush();

            out.writeInt(currentUser.getUserID());
            out.flush();

            User user = (User) in.readObject();
//            System.out.println(user);

            setCurrentUser(user);
            System.out.println("| Refresh All Info Finished |");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void refreshNotAll(){
        try {
            System.out.println("Try to get Not all info about User");
            Connection connection = Connection.getInstance();
            ObjectOutputStream out = connection.getOut();
//            System.out.println(out);
            ObjectInputStream in = connection.getIn();
//            System.out.println(in);

            out.writeInt(ServerQueryType.GET_USER_NOT_ALL_INFO);
            out.flush();

            out.writeInt(currentUser.getUserID());
            out.flush();

            User user = (User) in.readObject();

            setCurrentUser(user);
            System.out.println("| Not all info about User updated |");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void refreshUserMessages(){
        try {
            Connection connection = Connection.getInstance();
            ObjectOutputStream out = connection.getOut();
//            System.out.println(out);
            ObjectInputStream in = connection.getIn();
//            System.out.println(in);

            out.writeInt(ServerQueryType.GET_USER_MESSAGES);
            out.flush();

            out.writeInt(currentUser.getUserID());
            out.flush();

            List<Message> messages = (List<Message>) in.readObject();
            for (Message message : messages) {
                System.out.println(message);
            }
            currentUser.setMessages(messages);
            System.out.println("| Messages updated ;) |");

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void refreshUserStocks(){
        try {
            Connection connection = Connection.getInstance();
            ObjectOutputStream out = connection.getOut();
//            System.out.println(out);
            ObjectInputStream in = connection.getIn();
//            System.out.println(in);
            out.writeInt(ServerQueryType.GET_USER_STOCKS);
            out.flush();

            out.writeInt(currentUser.getUserID());
            out.flush();

            List<Stock> stocks = (List<Stock>) in.readObject();
            currentUser.setStocks(stocks);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
