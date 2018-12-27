package Model;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tree {

    public Tree(){
        Node computerRoot = new Node(ReaderFileJson("data.json", "Ordinateur"));
        computerRoot.PrintNode();
        Node cellphoneRoot = new Node(ReaderFileJson("data.json", "Telephone"));
        cellphoneRoot.PrintNode();
    }

    private List<Item> ReaderFileJson(String fileName, String type) {
        JSONParser jsonParser = new JSONParser();
        List<Item> itemStorage = new ArrayList<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            FileReader reader = new FileReader(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
            Object obj = jsonParser.parse(reader);
            JSONArray productList = (JSONArray) ((JSONObject)obj).get(type);
            for (Object product : productList) {
                if (product instanceof JSONObject) {
                    Item nouveau = parseProductObject((JSONObject) product, type);
                    if (nouveau != null) itemStorage.add(nouveau);
                }
            }
        } catch (IOException | org.json.simple.parser.ParseException | JSONException e) {
            e.printStackTrace();
        }
        return itemStorage;
    }

    private Item parseProductObject(JSONObject productObject, String type) throws JSONException {
        Item item = null;
        String name = (String) productObject.get("name");
        String brand = productObject.get("marque").toString();
        double price = Double.parseDouble(productObject.get("prix").toString());
        double size;
        switch(type){
            case "Ordinateur":
                //String OS = productObject.get("").toString();
                int ram = Integer.parseInt(productObject.get("ram").toString());
                String capacityO = productObject.get("stockage").toString();
                size = Double.parseDouble(productObject.get("taille_ecran").toString());
                int weigth = Integer.parseInt(productObject.get("poids").toString());
                String processor = productObject.get("processeur").toString();
                String GPU = productObject.get("carte graphique").toString();
                item = new Computer(name, brand, price, ram, capacityO, size, weigth, processor, GPU);
                break;
            case "Telephone":
                size = Double.parseDouble(productObject.get("taille").toString());
                int capacity = Integer.parseInt(productObject.get("memoire").toString());
                item = new Cellphone(name, brand, price, size, capacity);
                break;
            default:
                System.out.println("Le type de l'item : "+ name + " est introuvable: l'item n'a pa été ajouté!");
        }
        return item;
    }



}
