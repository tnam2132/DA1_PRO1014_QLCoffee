package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SocketClient {

    static Socket socket;
    static DataInputStream dis;
    static DataOutputStream dos;
    static boolean isConnect;
    static String host = "54.169.70.214";
    static int port = 8888;
    static IReadMessage iReadMessage;

    public static void connect() {
        try {
            socket = new Socket(host, port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            isConnect = true;
            SocketClient.start();
            System.out.println("Connected!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setIReadMessage(IReadMessage iReadMessage) {
        SocketClient.iReadMessage = iReadMessage;
    }

    public static void sendMessage(String text) {
        if (isConnect) {
            try {
                dos.writeUTF(text);
                dos.flush();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void close() {
        try {
            isConnect = false;
            dis.close();
            dos.close();
            socket.close();
            System.out.println("Disconnect!");
        } catch (Exception e) {
        }
    }

    static void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isConnect) {
                    try {
                        String text = dis.readUTF();
                        iReadMessage.readMessage(text);
                    } catch (Exception e) {
                        SocketClient.close();
                    }
                }
            }
        }).start();
    }
}
