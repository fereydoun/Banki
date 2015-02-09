package entities;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogBuilder{

    private Logger logger= Logger.getLogger("dotin");

    public LogBuilder(String fileName){
        createFile(fileName);
    }

    // This method configure the logger with handler and formatter
    private void createFile(String fileName){
        FileHandler fileHandler;
        try {
            if (new File(fileName).exists())
                fileHandler = new FileHandler(fileName);
            else
                fileHandler = new FileHandler(fileName,true);
            this.logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeToLog(String text){
        this.logger.info(text);
    }

//    public static  void createLog(String logFileName,String text)//thread safe
//    {
//        LogBuilder logBuilder=new LogBuilder(logFileName);
//        logBuilder.writeToLog(text);
//
//    }

}
