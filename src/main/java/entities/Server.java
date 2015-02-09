package entities;

import exceptions.JSONException;
import org.json.simple.JSONObject;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static String serverIP;
    public static int serverPort;
    public static final String LOG_FILE_NAME = "src/main/resources/log/ServerLog.log";

    public void main(){
        DepositHandler depositHandler = new DepositHandler();
        try{
            loadServerConfigFromJSON("src/main/resources/core.json");
            depositHandler.loadDepositsFromJSONFile("src/main/resources/core.json");
            Listen();
        }catch (Exception ex) {
            return;
        }
    }

    public static void loadServerConfigFromJSON(String fileName) throws Exception{
        JSONReader jsonReader = new JSONReader();
        try {
            JSONObject jsonObject = jsonReader.readJSONFile(fileName);
            serverPort = Integer.parseInt(jsonObject.get("port").toString());
        } catch (Exception e) {
            throw new JSONException("server config can not load");
        }
    }


    public void Listen(){
        ServerSocket serverSocket;
        try {
            serverSocket= new ServerSocket(Server.serverPort);
        }catch (Exception ex){return;}

        while(true) {
            try {

                Socket socket = serverSocket.accept();
                ServerRunable serverRunable = new ServerRunable(socket);
                //serverRunable.start();
                serverRunable.run1();
            } catch (Exception ex) {

                LogBuilder.createLog(Server.LOG_FILE_NAME, ex.getMessage());
            }
        }
    }


}
