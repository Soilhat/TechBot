package Model;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tree {
    private Node computerRoot;
    private Node cellphoneRoot;

    public Tree(){
        computerRoot = new Node(ReaderFileJson("Ordinateur"), "Computer");
        cellphoneRoot = new Node(ReaderFileJson("Telephone"), "Cellphone");
        ComputerConstruction();
    }

    private void ComputerConstruction() {
        Node officeNode = new Node(OfficeComputers(computerRoot.getPossibilities()), "Office");;
        computerRoot.NewChild(officeNode);
        Node polyvalentNode = new Node(PolyvalentComputers(computerRoot.getPossibilities()), "Polyvalent");
        System.out.println("\n"+ polyvalentNode.getPossibilities().size()+" Polyvalent Computers : \n");
        //polyvalentNode.PrintNode();
        //computerRoot.NewChild(polyvalentNode);

        Node gamerNode = new Node(GamerComputers(computerRoot.getPossibilities()), "Gamer");
        System.out.println("\n"+gamerNode.getPossibilities().size()+" Gamer Computers : \n");
        //gamerNode.PrintNode();
        //computerRoot.NewChild(gamerNode);
    }

    private Map<String, List<Item>> CapacitySplit(List<Item> tab) {
        Map<String, List<Item>> retour = new HashMap<>();
        for(Item item : tab){
            Computer computer = (Computer) item;
            retour.putIfAbsent(computer.capacity, new ArrayList<>());
            retour.get(computer.capacity).add(computer);
        }
        return retour;
    }

    private List<Item> GamerComputers(List<Item> tab){
        List<Item> retour = new ArrayList<>();
        for(Item computer : tab){
            Computer comp = (Computer) computer;
            if(comp.processor.contains("I5") || comp.processor.contains("I7"))
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
            if(comp.processor.contains("I5") || comp.processor.contains("I7"))
                //if(comp.RAM == 4 || comp.RAM  == 8)
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
                    if (nouveau != null && !itemStorage.contains(nouveau)) itemStorage.add(nouveau);
                }
            }
        } catch (IOException | org.json.simple.parser.ParseException | JSONException e) {
            e.printStackTrace();
        }
        return itemStorage;
    }

    private Item parseProductObject(JSONObject productObject, String type) throws JSONException {
        Item item = null;
        try {

            String name = (String) productObject.get("name");
            String brand = productObject.get("marque").toString();
            double price = Double.parseDouble(productObject.get("prix").toString().replace(',', '.'));
            double size;
            switch (type) {
                case "Ordinateur":
                    //String OS = productObject.get("").toString();
                    int ram = Integer.parseInt(productObject.get("ram").toString().replaceAll("Go", ""));
                    String capacityO = productObject.get("stockage").toString();
                    size = Double.parseDouble(productObject.get("taille_ecran").toString().replace(',', '.'));
                    int weigth = Integer.parseInt(productObject.get("poids").toString());
                    String processor = productObject.get("processeur").toString();
                    String GPU = productObject.get("carte graphique").toString();
                    item = new Computer(name, brand, price, ram, capacityO, size, weigth, processor, GPU);
                    break;
                case "Telephone":
                    size = Double.parseDouble(productObject.get("taille").toString().replace(',', '.'));
                    int capacity = Integer.parseInt(productObject.get("memoire").toString().replaceAll("Go", ""));
                    item = new Cellphone(name, brand, price, size, capacity);
                    break;
                default:
                    System.out.println("Le type de l'item : " + name + " est introuvable: l'item n'a pa été ajouté!");
            }
        }
        catch (Exception e){
            System.out.println("L'element n'a pas été rajouté");
        }
        return item;
    }
}