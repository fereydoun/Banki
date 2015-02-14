package entities;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogBuilder{

    private Logger logger= Logger.getLogger("dotin");
    private FileHandler fileHandler;
    public LogBuilder(String fileName){
        createFile(fileName);
    }

    private void createFile(String fileName){

        try {
            this.fileHandler = new FileHandler(fileName,true);
            this.logger.addHandler(this.fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            this.fileHandler.setFormatter(formatter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeToLog(String text) {
        synchronized (this.logger) {
            this.logger.info(text);
        }
    }

    public void closeFile()
    {
        this.fileHandler.close();
    }
}
