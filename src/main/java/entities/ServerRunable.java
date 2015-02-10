package entities;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerRunable implements Runnable{

    private static final int BUFFER_SIZE = 64;  //size of receiver buffer
    private static final String END_OF_MSG = "#EXIT#";
    private static final int END_OF_MSG_LEN = 6;
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
        byte[] byteBuffer = new byte[BUFFER_SIZE];
        boolean completed = false;
        try {
            InputStream inputStream = this.socket.getInputStream();
            while ((!completed) && (inputStream.read(byteBuffer)) != -1 ) {
                String s = new String(byteBuffer);
                receivedMessage += s.trim();

                String patternExit = receivedMessage.substring(receivedMessage.length() - END_OF_MSG_LEN);
                if(patternExit.trim().compareTo(END_OF_MSG.trim()) == 0)
                    completed=true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return receivedMessage;
    }

    public void sendMessage(String sendMessage){
        try {
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write((sendMessage + END_OF_MSG.trim()).getBytes());
            this.socket.close();
        } catch (Exception ex) {
            System.out.println();
        }
    }
}
