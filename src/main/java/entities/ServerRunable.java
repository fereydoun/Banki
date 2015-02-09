package entities;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerRunable{

    private static final int BUFFER_SIZE = 32;  //size of receiver buffer
    private Socket socket;

    public ServerRunable(Socket socket) {
        this.socket = socket;
    }

    public void run(){
        try {
            Transaction transaction = new Transaction();
            DepositHandler depositHandler = new DepositHandler();
            transaction = transaction.convertTransMsgToTransObject(receiveMessage());
            depositHandler.executeClientRequest(transaction);
            sendMessage(transaction.getResult());

        } catch (Exception ex) {
            LogBuilder.createLog(Server.LOG_FILE_NAME, ex.getMessage());
            //write to logger file
        }
    }

    public void run1() {
        try {
            Transaction transaction = new Transaction();
            DepositHandler depositHandler = new DepositHandler();
            transaction = transaction.convertTransMsgToTransObject(receiveMessage());
            depositHandler.executeClientRequest(transaction);
            sendMessage(transaction.getResult());

        } catch (Exception ex) {
            LogBuilder.createLog(Server.LOG_FILE_NAME, ex.getMessage());
            //write to logger file
        }
    }

    public void start(){
//        Thread thread = new Thread(this);
//        thread.start();
    }

    public String receiveMessage(){

        String receivedMessage = "";
        byte[] byteBuffer = new byte[BUFFER_SIZE];
        try {
            InputStream inputStream = this.socket.getInputStream();
            while ((inputStream.read(byteBuffer)) != -1) {
                String s = new String(byteBuffer);
                receivedMessage += s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receivedMessage;
    }

    public void sendMessage(String sendMessage){
        try {
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write(sendMessage.getBytes());

        } catch (Exception ex) {
            System.out.println();
        }
    }
}
