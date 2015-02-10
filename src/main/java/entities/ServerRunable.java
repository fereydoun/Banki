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
        Transaction transaction = new Transaction();
        try {
            DepositHandler depositHandler = new DepositHandler();
            transaction = transaction.convertTransMsgToTransObject(receiveMessage());
            depositHandler.executeClientRequest(transaction);
            sendMessage(transaction.getResult());
        } catch (Exception ex) {
            sendMessage(transaction.getResult());
            LogBuilder logBuilder=new LogBuilder(Server.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
        }
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public String receiveMessage(){
        String receivedMessage = "";
        byte[] byteBuffer = new byte[Server.BUFFER_SIZE];
        boolean completed = false;
        try {
            InputStream inputStream = this.socket.getInputStream();
            while ((!completed) && (inputStream.read(byteBuffer)) != -1 ) {

                String s = new String(byteBuffer);
                receivedMessage += s.trim();
                String patternExit = receivedMessage.substring(receivedMessage.length() - Server.END_OF_MSG_LEN);
                if(patternExit.trim().compareTo(Server.END_OF_MSG.trim()) == 0)
                    completed=true;
            }
        } catch (Exception ex) {
            LogBuilder logBuilder=new LogBuilder(Server.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
        }
        return receivedMessage;
    }

    public void sendMessage(String sendMessage){
        try {
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write((sendMessage + Server.END_OF_MSG.trim()).getBytes());
            this.socket.close();
        } catch (Exception ex) {
            LogBuilder logBuilder=new LogBuilder(Server.LOG_FILE_NAME);
            logBuilder.writeToLog(ex.getMessage());
        }
    }
}
