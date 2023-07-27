import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ManageFile {
    public static final String FILE_NAME = "toys.json";

    public static void saveToJson(Toy toy) {

        JSONObject toyDetails = new JSONObject();

        toyDetails.put("toy id", toy.getId());
        toyDetails.put("toy name", toy.getName());
        toyDetails.put("toy quantity", toy.getQuantity());
        toyDetails.put("toy weight", toy.getWeight());

        File f = new File(FILE_NAME);
        JSONArray toysArray = new JSONArray();
        if (f.exists() && !f.isDirectory()) {
            toysArray = getJsonArray();
            if (toysArray == null) throw new NullPointerException("Ошибка!");
        }
        toysArray.add(toyDetails);
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(toysArray.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static JSONArray getJsonArray() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray toysArray = (JSONArray) obj;
            return toysArray;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Toy> readFile() {
        JSONArray jsonArray = getJsonArray();
        List<Toy> toysList = new ArrayList<>();
        if (jsonArray != null) {
            for(Object o: jsonArray){
                if ( o instanceof JSONObject ) {
                    JSONObject toy = (JSONObject) o;
                    int toyID = (int)(long) toy.get("toy id");
                    String toyName = (String) toy.get("toy name");
                    int toyQuantity = (int)(long) toy.get("toy quantity");
                    int toyWeight = (int)(long) toy.get("toy weight");
                    toysList.add(new Toy(toyID,toyName, toyQuantity, toyWeight));
                }
            }

        }

        return toysList;
    }
}
