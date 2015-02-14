package entities;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerRunable implements Runnable{

    private Socket socket;

    public ServerRunable(Socket socket){
        this.socket = socket;
    }

    public void run(){
        receiveMessage();
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public void receiveMessage() {
        String receivedMessage = "";
        byte[] byteBuffer = new byte[Server.BUFFER_SIZE];
        try {
            InputStream inputStream = this.socket.getInputStream();
            while ((inputStream.read(byteBuffer)) != -1) {

                String s = new String(byteBuffer);
                receivedMessage += s.trim();
                String patternExit = receivedMessage.substring(receivedMessage.length() - Server.END_OF_MSG_LEN);
                if (patternExit.trim().compareTo(Server.END_OF_MSG.trim()) == 0) {
                    processingClientRequest(receivedMessage);
                    receivedMessage ="";
                }
            }
        } catch (Exception ex) {
            LogBuilder logBuilder = new LogBuilder(Server.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
            logBuilder.closeFile();
        }
    }

    private void processingClientRequest(String receivedMessage){

        Transaction transaction = new Transaction();
        try {
            DepositHandler depositHandler = new DepositHandler();
            transaction = transaction.convertTransMsgToTransObject(receivedMessage);
            depositHandler.executeClientRequest(transaction);
            sendMessage(transaction.getResult());
        } catch (Exception ex) {
            sendMessage(transaction.getResult());
            LogBuilder logBuilder=new LogBuilder(Server.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
            logBuilder.closeFile();
        }
    }

    public void sendMessage(String sendMessage){
        try {
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write((sendMessage + Server.END_OF_MSG.trim()).getBytes());
        } catch (Exception ex) {
            LogBuilder logBuilder=new LogBuilder(Server.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
            logBuilder.closeFile();
        }
    }
}
