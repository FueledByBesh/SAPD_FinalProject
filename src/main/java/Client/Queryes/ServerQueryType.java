package Client.Queryes;

import java.io.Serializable;

public class ServerQueryType implements Serializable {
    public static final int LOG_IN = 1;
    public static final int REGISTER = 2;
    public static final int GET_USER_ALL_INFO = 3;
    public static final int GET_USER_NOT_ALL_INFO = 4;
    public static final int GET_USER_MESSAGES = 5;
    public static final int GET_USER_STOCKS = 6;
    public static final int TRANSFER = 7;
    public static final int GET_USER_BY_PHONE = 8;
    public static final int GET_USER_BY_ID = 9;
    public static final int GET_ALL_STOCKS = 10;
    public static final int BUY_STOCK = 11;
    public static final int SELL_STOCK = 12;
}
