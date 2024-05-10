package Server.Worker_classes.Messages.MessageFactory;

import Client.Messages.Message;

import java.sql.ResultSet;

public interface MessageFactory {
    Message getMessage(ResultSet table);
}

