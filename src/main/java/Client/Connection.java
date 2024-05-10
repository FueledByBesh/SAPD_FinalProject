package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private static Connection instance;
    private Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    private Connection() {
        connectToServer();
        try {
            out = new ObjectOutputStream(this.socket.getOutputStream());
            in = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getInstance() {
        if(instance == null){
            instance = new Connection();
        }
        return instance;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    private void connectToServer(){
        try {
            socket = new Socket("127.0.0.1", 9999);
        } catch (IOException e) {
            System.out.println("? Error when try connect to Server ?");
        }
    }
    void disconnectFromServer(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
