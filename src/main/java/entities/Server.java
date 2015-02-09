package entities;

import exceptions.JSONException;
import org.json.simple.JSONObject;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static String serverIP;
    public static int serverPort;
    public static final String LOG_FILE_NAME ="src/main/resources/log/ServerLog.log";

    public void main() {
        DepositHandler depositHandler = new DepositHandler();
        try {

            loadServerConfigFromJSON("src/main/resources/core.json");
            depositHandler.loadDepositsFromJSONFile("src/main/resources/core.json");
            Listen();

        } catch (Exception ex) {
            return;
        }

    }

    public static void loadServerConfigFromJSON(String fileName) throws Exception
    {
        JSONReader jsonReader=new JSONReader();
        try {
            JSONObject jsonObject=jsonReader.readJSONFile(fileName);

            serverPort=Integer.parseInt(jsonObject.get("port").toString());

        }catch (Exception e)
        {
            throw new JSONException("server config can not load");
        }
    }


    public void Listen() {
        try {


            Runnable runnable = new Runnable() {


                ServerSocket serverSocket = new ServerSocket(Server.serverPort);

                @Override
                public void run() {

                    for (; ; ) {
                        try {

                            Socket socket = serverSocket.accept();
                            //create thread
                            ServerRunable serverRunable = new ServerRunable(socket);
                            serverRunable.start();
//                            Thread thread = new Thread(serverRunable);
//                            thread.start();

                        } catch (Exception ex) {
                            //write to logger file
                        }
                    }//for (; ; )

                }
            };

            Thread serverThread=new Thread(runnable);
            serverThread.start();

        } catch (Exception e) {

            //write to logger file
        }
    }



}
