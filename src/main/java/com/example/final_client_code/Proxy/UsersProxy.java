package com.example.final_client_code.Proxy;

import Client.Connection;
import Client.Queryes.ServerQueryType;
import Client.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class UsersProxy {
    static HashMap<String, ProxyUser > proxyUsers = new HashMap<>();

    public String getUserNameByPhone(String phone){
        ProxyUser proxyUser = proxyUsers.get(phone);
        if(proxyUser != null){
            System.out.println("Returns from hashMap");
            return proxyUser.getShortedFullName();
        }
        User user = getFromDataBase(phone);
        if(user == null){
            System.out.println("User not found");
            return null;
        }
        ProxyUser newProxyUser = new ProxyUser(user.getUserInfo().getFirstName(), user.getUserInfo().getLastname(), user.getUserInfo().getBirthDate());
        proxyUsers.put(phone,newProxyUser);

        return newProxyUser.getShortedFullName();
    }

    public User getFromDataBase(String phone)  {
        try{
//            System.out.println("Connecting...");
            Connection connection = Connection.getInstance();
//            System.out.println("Connected :)");
            ObjectOutputStream out = connection.getOut();
//            System.out.println(out);
            ObjectInputStream in = connection.getIn();
//            System.out.println(in);

            System.out.println("Get User by phone");
            out.writeInt(ServerQueryType.GET_USER_BY_PHONE);
            out.flush();

            System.out.println("Send phone...");
            out.writeUTF(phone);
            out.flush();

            return (User) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("| Get User by phone finished |");
        }
    }
}
