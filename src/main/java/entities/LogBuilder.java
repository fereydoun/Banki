package entities;


import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogBuilder {

    private Logger logger= Logger.getLogger("dotin");
    private FileHandler fileHandler;

    private LogBuilder(String fileName)
    {
        createFile(fileName);
    }


    // This method configure the logger with handler and formatter
    private   void  createFile(String fileName) {
        try {

            if (new File(fileName).exists())
                this.fileHandler = new FileHandler(fileName, true);
            else
                this.fileHandler = new FileHandler(fileName,true);

            this.logger.addHandler(this.fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            this.fileHandler.setFormatter(formatter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private   void writeToLog(String text)
    {
        this.logger.info(text);
    }

    public static synchronized void createLog(String logFileName,String text)//thread safe
    {
        LogBuilder logBuilder=new LogBuilder(logFileName);
        logBuilder.writeToLog(text);

    }

}
