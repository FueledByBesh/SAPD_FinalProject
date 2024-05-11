package Server;

import Client.Messages.Message;
import Client.Queryes.*;
import Client.Stock.Stock;
import Client.User;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;


public class Main_Server {
    public static void main(String[] args) {
        try {
            Server server = new Server(9999,5433,"newBankDB","postgres","olzhas05");
//            server.getStockChanger().changePrice(1, 10000);

            while (true){
                System.out.println("Waiting...");
                Socket socket = server.getServerSocket().accept();
                System.out.println(socket.getInetAddress()+" connected!");

                createNewThread(server, socket);

            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createNewThread(Server server, Socket socket){
        Thread thread = new Thread(() -> {
            try{
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());


                System.out.println("CreateNewThread");
                while (true) {
                    int type = in.readInt();

                    switch (type) {
                        case ServerQueryType.LOG_IN ->{
                            executeLogIn(server, socket, in, out);
                        }case ServerQueryType.REGISTER -> {
                            executeRegister(server, socket, in, out);
                        }case ServerQueryType.GET_USER_ALL_INFO -> {
                            executeGetAllUserIngo(server, socket, in, out);
                        }case ServerQueryType.GET_USER_NOT_ALL_INFO -> {
                            executeGetNotAllUserIngo(server, socket, in, out);
                        }case ServerQueryType.GET_USER_MESSAGES -> {
                            executeGetUserMessages(server, socket, in, out);
                        }case ServerQueryType.GET_USER_STOCKS -> {
                            executeGetUserStocks(server, socket, in, out);
                        }case ServerQueryType.TRANSFER -> {
                            executeTransfer(server, socket, in, out);
                        }case ServerQueryType.GET_USER_BY_PHONE -> {
                            executeGetNotAllUserInfoWithPhone(server, socket, in, out);
                        }case ServerQueryType.GET_ALL_STOCKS -> {
                            executeGetAllStocks(server, socket, in, out);
                        }case ServerQueryType.BUY_STOCK -> {
                            executeBuyStock(server, socket, in, out);
                        }case ServerQueryType.SELL_STOCK ->{
                            executeSellStock(server, socket, in, out);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }


    public static void executeLogIn(Server server, Socket socket, ObjectInputStream in, ObjectOutputStream out){
        try{
            System.out.println("Execute Login...");
            LoginUserQuery logInUser = (LoginUserQuery) in.readObject();
            System.out.println(logInUser);

            User user = server.getAdmission().login(logInUser.getPhone(), logInUser.getPassword());

            out.writeObject(user);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.out.println("Some error when try LOGIN User, "+e.getMessage());
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            System.out.println("| Login finished |");
        }
    }
    public static void executeRegister(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Register...");

            RegisterUserQuery registerUser = (RegisterUserQuery) in.readObject();

            String answer = server.getAdmission().signUp(
                    registerUser.getPhone(),
                    registerUser.getPassword(),
                    registerUser.getFirstName(),
                    registerUser.getLastName(),
                    registerUser.getEmail(),
                    registerUser.getBirthDate());

            if(answer.equals("Correct")){
                out.writeBoolean(true);
                out.flush();
            }else {
                out.writeBoolean(false);
                out.writeUTF(answer);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Some error when try REGISTER User, "+e.getMessage());
            try {
                out.writeObject("Error");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            System.out.println("| Register finished |");
        }
    }
    public static void executeGetAllUserIngo(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Get All Info...");

            int userID = in.readInt();

            User user = server.getAdmission().getUserInfoWithID(userID, true);

            System.out.println("AFTER send ;)");

            out.writeObject(user);
            out.flush();

        } catch (IOException e) {
            System.out.println("Some error when try to get All info about User, "+e.getMessage());
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            System.out.println("| Get all info finished |");
        }
    }
    private static void executeGetNotAllUserIngo(Server server, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        try {
            System.out.println("Execute Get Not All Info...");

            int userID = in.readInt();

            User user = server.getAdmission().getUserInfoWithID(userID, false);

            out.writeObject(user);
            out.flush();

        } catch (IOException e) {
            System.out.println("Some error when try to get NOT All info about User, "+e.getMessage());
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            System.out.println("| Get Not All info finished!!! |");
        }
    }
    public static void executeGetUserMessages(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Get Messages...");

            int userID = in.readInt();

            List<Message> messages = server.getMessageSender().getListOfMessagesWithUserID(userID);

            out.writeObject(messages);
            out.flush();

        } catch (IOException e) {
            System.out.println("Some error when try to get MESSAGES from User, "+e.getMessage());
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            System.out.println("| Get Messages Finished |");
        }
    }
    public static void executeGetUserStocks(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Get Stocks...");

            int userID = in.readInt();

            List<Stock> stocks = server.getStockAdmission().getInvestorStocks(userID);

            out.writeObject(stocks);
            out.flush();

        } catch (IOException e) {
            System.out.println("Some error when try to get STOCKS from User, "+e.getMessage());
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            System.out.println("| Get Stocks Finished |");
        }
    }
    public static void executeTransfer(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Transfer ...");

            TransferQuery transferQuery = (TransferQuery) in.readObject();

            boolean isTransferred = server.getTransfer().transferTo(transferQuery.getFromUserID(), transferQuery.getToUserID(),transferQuery.getMoneyAmount() , transferQuery.getMessage());

            out.writeBoolean(isTransferred);
            out.flush();


        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Some error when try to get STOCKS from User, "+e.getMessage());
            try {
                out.writeBoolean(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            System.out.println("| Transfer finished! |");
        }
    }
    public static void executeGetNotAllUserInfoWithPhone(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Get Not All Info with phone");

            String phone = in.readUTF();

            User user = server.getAdmission().getUserWithPhone(phone, false);

            out.writeObject(user);
            out.flush();

        } catch (IOException e) {
            System.out.println("Some error when try to get NOT All info about User, "+e.getMessage());
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            System.out.println("| Get Not All Info with phone finished |");
        }
    }
    public static void executeGetAllStocks(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Get All Stocks");

            List<Stock> stocks = server.getStockAdmission().getAllStocks();

            out.writeObject(stocks);
            out.flush();

        } catch (IOException e) {
            System.out.println("? Some error when try to get NOT All info about User, "+e.getMessage()+" ?");
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            System.out.println("| Get All stocks Finished |");
        }
    }
    public static void executeBuyStock(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Buy stock");
            BuyStockQuery buyQuery = (BuyStockQuery) in.readObject();
            System.out.println(buyQuery);

            boolean isBought = server.getTransfer().buyStock(buyQuery.getUserID(), buyQuery.getStockID(), buyQuery.getStockCount());

            out.writeBoolean(isBought);
            out.flush();

        } catch (ClassNotFoundException | IOException e) {
            System.out.println("? Some error when try to Buy stock, "+e.getMessage()+" ?");
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            System.out.println("Buy stock finished");
        }
    }
    public static void executeSellStock(Server server, Socket socket, ObjectInputStream in,  ObjectOutputStream out){
        try {
            System.out.println("Execute Sell stock");
            SellStockQuery sellQuery = (SellStockQuery) in.readObject();

            boolean isBought = server.getTransfer().sellStock(sellQuery.getUserID(), sellQuery.getStockID(), sellQuery.getStockCount());

            out.writeBoolean(isBought);
            out.flush();

        } catch (ClassNotFoundException | IOException e) {
            System.out.println("? Some error when try to Sell stock, "+e.getMessage()+" ?");
            try {
                out.writeObject(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            System.out.println("| Sell stock finished |");
        }
    }

}