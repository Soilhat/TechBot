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
    private Node computerRoot;
    private Node cellphoneRoot;

    public Tree(){
        computerRoot = new Node(ReaderFileJson("Ordinateur"), "Computer");
        cellphoneRoot = new Node(ReaderFileJson("Telephone"), "Cellphone");
        ComputerConstruction();
    }

    private void ComputerConstruction() {
        Node officeNode = new Node(OfficeComputers(computerRoot.getPossibilities()), "Office");
        computerRoot.NewChild(officeNode);
        Node polyvalentNode = new Node(PolyvalentComputers(computerRoot.getPossibilities()), "Polyvalent");
        computerRoot.NewChild(polyvalentNode);
        Node gamerNode = new Node(GamerComputers(computerRoot.getPossibilities()), "Gamer");
        computerRoot.NewChild(gamerNode);
    }

    private List<Item> GamerComputers(List<Item> tab){
        List<Item> retour = new ArrayList<>();
        for(Item computer : tab){
            Computer comp = (Computer) computer;
            if(comp.processor.contains("i5") || comp.processor.contains("i7"))
                if(comp.RAM  >= 8)
                    if(comp.GPU.contains("GeForce GTX"))
                        retour.add(computer);
        }
        return retour;
    }

    private List<Item> PolyvalentComputers(List<Item> tab){
        List<Item> retour = new ArrayList<>();
        for(Item computer : tab){
            Computer comp = (Computer) computer;
            if(comp.processor.contains("i5") || comp.processor.contains("i7"))
                if(comp.RAM == 4 || comp.RAM  == 8)
                    retour.add(computer);
        }
        return retour;
    }

    private List<Item> OfficeComputers(List<Item> tab){
        List<Item> retour = new ArrayList<>();
        for(Item computer : tab){
            if(((Computer)computer).processor.contains("Pentium") && ((Computer)computer).RAM == 4)
                retour.add(computer);
        }
        return retour;
    }

    private List<Item> ReaderFileJson( String type) {
        JSONParser jsonParser = new JSONParser();
        List<Item> itemStorage = new ArrayList<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            FileReader reader = new FileReader(Objects.requireNonNull(classLoader.getResource("data.json")).getFile());
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