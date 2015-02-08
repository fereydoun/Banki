import entities.Server;
import entities.Terminal;

/**
 * Created by Dotin school 5 on 2/8/2015.
 */
public class Banki {

    public static void main(String[] args)
    {
        try{
           // System.in.

        }catch (Exception ex){
            System.out.println("please enter system run type(client/server)");
            return;
        }

        if (args[0].toLowerCase().equals("server"))
        {
            Server server=new Server();
            server.main();

        }else if(args[0].toLowerCase().equals("client"))
        {
            Terminal terminal=new Terminal();
            terminal.main();
        }
    }
}
