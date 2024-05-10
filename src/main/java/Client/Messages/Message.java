package Client.Messages;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;

public abstract class Message implements Serializable {
    private  int messageID;
    private int messageType;
    private Timestamp messageTime;
    private String message;
    private String messageFromName;
    private int messageToUserID;
    private String messageToName;

    public Message(){

    }

    public Message(int messageID, int messageType, Timestamp messageTime, String message, String messageFromName, int messageToUserID, String messageToName) {
        this.messageID = messageID;
        this.messageType = messageType;
        this.messageTime = messageTime;
        this.message = message;
        this.messageFromName = messageFromName;
        this.messageToUserID = messageToUserID;
        this.messageToName = messageToName;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageFromName() {
        return messageFromName;
    }

    public void setMessageFromName(String messageFromName) {
        this.messageFromName = messageFromName;
    }

    public int getMessageToUserID() {
        return messageToUserID;
    }

    public void setMessageToUserID(int messageToUserID) {
        this.messageToUserID = messageToUserID;
    }

    public String getMessageToName() {
        return messageToName;
    }

    public void setMessageToName(String messageToName) {
        this.messageToName = messageToName;
    }

    public String getMessageHour(){
        Date date = new Date(messageTime.getTime());
        return date.getHours()+"";
    }
    public String getMessageMinute(){
        Date date = new Date(messageTime.getTime());
        return date.getMinutes()+"";
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", messageType=" + messageType +
                ", messageTime=" + messageTime +
                ", message='" + message + '\'' +
                ", messageFromName='" + messageFromName + '\'' +
                ", messageToUserID=" + messageToUserID +
                ", messageToName='" + messageToName + '\'' +
                '}';
    }
}

