package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//Clase que gestiona la conexión entre servidor y cliente
public class GesConection {

    private Integer port = null;
    private Socket socket = null;

    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    private Object obj;

    public GesConection(Integer port) {
        this.port = port;
    }

    /*
        Utiliza el socket input para recibir un objeto
        desde el cliente y devuelve dicho objeto
     */
    public Object getObjectFromClient() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            socket = serverSocket.accept();
            inputStream = new ObjectInputStream(socket.getInputStream());
            obj = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /*
        Envia un objeto al cliente
    */
    public void sendObject(Object obj) {

        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(obj);
            outputStream.flush();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public int getPort() {
        return port;
    }

}
