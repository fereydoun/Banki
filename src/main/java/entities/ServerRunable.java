package entities;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerRunable implements  Runnable {

    private static final int BUFFER_SIZE = 32;  //size of receiver buffer
    private Socket socket;
    private Thread thread;

    private  Transaction transaction;
    private DepositHandler depositHandler;

    public ServerRunable(Socket socket)
    {
        this.socket = socket;
    }

    public void  run() {

        try {
            transaction = new Transaction();
            depositHandler=new DepositHandler();

            transaction = transaction.convertTransMsgToTransObject(receiveMessage());
            depositHandler.executeClientRequest(transaction);


            sendMessage("server: "+transaction.getDepositID());

        } catch (Exception ex) {
            LogBuilder.createLog(Server.LOG_FILE_NAME, ex.getMessage());
            //write to logger file
        }
    }

    public void start()
    {
        this.thread=new Thread(this);
        this.thread.start();
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
                receivedMessage += s;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return receivedMessage;
    }

    public void sendMessage(String sendMessage)
    {
        try
        {
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write(sendMessage.getBytes());

        }catch (Exception ex){}
    }
}
