package entities;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

    public JSONObject readJSONFile(String fileName) throws Exception
    {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            Object object = jsonParser.parse(new FileReader(fileName));
            jsonObject=(JSONObject)object;

        }catch (FileNotFoundException e){
            e.printStackTrace();
            throw new FileNotFoundException(e.getMessage());
        }catch (IOException e){
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }catch (ParseException e){
            e.printStackTrace();
            throw new ParseException(e.getPosition());
        }
        return jsonObject;
    }
}
