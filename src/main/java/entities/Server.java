package entities;

import org.json.simple.JSONObject;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static String serverIP;
    public static int serverPort;
    public static final String LOG_FILE_NAME ="ServerLog.log";


    public static void loadServerConfigFromJSON(String fileName)
    {
        JSONReader jsonReader=new JSONReader();
        try {
            JSONObject jsonObject=jsonReader.readJSONFile(fileName);

            serverPort=Integer.parseInt(jsonObject.get("port").toString());

        }catch (Exception e)
        {

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

                            Thread thread = new Thread(serverRunable);
                            thread.start();

                        } catch (Exception ex) {
                            //write to logger file
                        }
                    }

                }//for (; ; )
            };

            Thread serverThread=new Thread(runnable);
            serverThread.start();

        } catch (Exception e) {

            //write to logger file
        }
    }



}
