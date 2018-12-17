package application.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Tree{
    private String type;
    private Map<String, String[]> steps;

    public Tree(String type)
    {
        this.type = type;
        Construction();
    }

    public void Search(){

    }
    private void Construction() {
        switch (type){
            case "Computer":
                ComputerTreeConstruction();
                break;
            case "Cellphone":
                break;
            case "Earphone":
                break;
        }
    }

    private void ComputerTreeConstruction(){
        Node root = new Node(ReaderFileJson(""));
        //Quel est l’usage que vous allez faire de votre ordinateur ?
        steps.put("PC or Mac", new String[]{"PC", "Mac"});

        steps.put("Usage", new String[]{"Bureautic", "Polyvalent", "Gamer"});

    }

    private List<Item> ReaderFileJson(String fileName) {
        JSONParser jsonParser = new JSONParser();
        List<Item> itemStorage = new ArrayList<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            FileReader reader = new FileReader(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
            Object obj = jsonParser.parse(reader);
            JSONArray productList = (JSONArray) obj;
            for (Object product : productList) {
                if (product instanceof JSONObject) {
                    Item nouveau = parseProductObject((JSONObject) product);
                    if (nouveau != null) itemStorage.add(nouveau);
                }
            }
        } catch (IOException | org.json.simple.parser.ParseException | JSONException e) {
            e.printStackTrace();
        }
        return itemStorage;
    }

    private Item parseProductObject(JSONObject product) throws JSONException {
        Item item = null;
        JSONObject productObject = (JSONObject) product.get("product");
        String name = (String) productObject.get("name");
        int quality = Integer.parseInt(productObject.get("quality").toString());
        int sellIn = Integer.parseInt(productObject.get("sellIn").toString());
        String creation_date = productObject.get("creation_date").toString();
        /*switch((String) productObject.get("type")){
            case "Aged_Brie":
                item = new Aged_Brie(name, sellIn, quality, creat0ion_date);
                break;
            case "Backstage_Passes":
                item = new Backstage_Passes(name, sellIn, quality, creation_date);
                break;
            case "Conjured_Mana_Cake" :
                item = new Conjured_Mana_Cake(name, sellIn, quality, creation_date);
                break;
            default:
                System.out.println("Le type de l'item : "+ name + " est introuvable: l'item n'a pa été ajouté!");
        }*/
        // TODO Read each items of the Json File
        return item;
    }

}