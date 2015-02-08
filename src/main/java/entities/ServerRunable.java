package entities;

import java.io.InputStream;
import java.net.Socket;

public class ServerRunable implements  Runnable {

    private static final int BUFFER_SIZE = 32;  //size of receiver buffer
    private Socket socket;

    public ServerRunable(Socket socket)
    {
        this.socket = socket;
    }

    public void  run() {
        Transaction transaction = new Transaction();
        try {
            transaction = transaction.convertTransMsgToTransObject(receiveMessage());


        } catch (Exception e) {

            //write to logger file
        }
    }



    public String receiveMessage() {

        String receivedMessage ="";
        int receiveMessageSize ;
        byte[] byteBuffer=new byte[BUFFER_SIZE];

        try {
            InputStream inputStream = this.socket.getInputStream();

            while ((receiveMessageSize = inputStream.read(byteBuffer)) != -1)
            {
                String s = new String(byteBuffer);
                receivedMessage.concat(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return receivedMessage;
    }

    public void sendMessage(Socket socket){}
}
