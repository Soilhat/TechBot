package Controller;

import Model.Cellphone;
import Model.Computer;
import Model.Item;
import Model.Tree;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot extends JFrame {

        private JTextField textEnter = new JTextField();
        private JTextArea textArea = new JTextArea();
        private JButton ordi = new JButton("Computer");
        ArrayList<String> keyWordListS = new ArrayList<String>(); //smartphone
        ArrayList<String> keyWordListC = new ArrayList<String>(); //computer
        private static final String CURRENCY_SYMBOLS= "\\p{Sc}\u0024\u060B\u20ac";

        public Bot() {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(1000,600);
            this.setVisible(true);
            this.setResizable(false);
            this.setTitle("Tech Bot");
            this.setLayout(null);
            this.setBackground(Color.blue);

            this.keyWordListS.add("samsung");
            this.keyWordListS.add("smartphone");
            this.keyWordListS.add("telephone");

            //this.keyWordListC.add("ordinateur");
            this.keyWordListC.add("gb");
            this.keyWordListC.add("asus");
            this.keyWordListS.add("stockage");
            this.keyWordListC.add("zen");
            this.keyWordListC.add("nvidia");
            String[] comp = {"intel", "hd", "i5","i7","apple", "mac","lenovo","hp","acer", "dell","microsoft","pentium", "graphics"};
            this.keyWordListC.addAll(Arrays.asList(comp));

            textEnter.setLocation(2,540);
            textEnter.setSize(590,30);
            //this.KeyWordSmartphone(textEnter.getText());
            textEnter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    // TODO Auto-generated method stub
                    String uText = textEnter.getText();
                    textArea.append("You : " + uText + "\n");
                    if (uText.contains("?")) {
                        //Tree t = new Tree();
                        botSay("Fine and you?");
                        /*List<Item> l;
                        l = ReaderFileJson("data.json", "Ordinateur");
                        for(Item i: l){
                            if (i.toString().contains("Asus")) botSay(i.toString());
                        }*/

                    }
                    textEnter.setText("");
                    KeyWordComputer(uText);
                }
            });

            textArea.setLocation(15,5);
            textArea.setSize(940,510);
            this.add(textEnter);
            this.add(textArea);
            //textArea.setLayout(null);

            botSay("Hello");

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

        public int countKeywords(String userSay) {
            userSay = userSay.toLowerCase();
            userSay = sansAccents(userSay);
            int count = 0;
            for (String keyWord : keyWordListS) {
                if (userSay.contains(keyWord)) {
                    count ++;
                }
            }
            return count;
        }

        public void KeyWordComputer(String userSay) {

            ArrayList<String> listKeys = listKeywords(userSay);
            String result = "";
            List<Item> l = ReaderFileJson("data.json", "Ordinateur");
            List<Item> newL = filter(l,listKeys);
            ArrayList<Computer> comp = new ArrayList<Computer>();
            for (Item i : newL){
                if(i instanceof Computer) {
                    comp.add((Computer)i);
                }
            }
            //ArrayList<Computer> li = keyWordCapacity(userSay, comp);
            keyWordRam(userSay,keyWordCapacity(userSay, comp));
            ;
            /*for(Item i: comp){
                botSay(i.toString());
            }*/



        }



        public ArrayList<Computer> keyWordCapacity(String userSay, ArrayList<Computer> comp){
            botSay("CAPACITY");
            ArrayList<Computer> result = new ArrayList<Computer>();
            if (findCapacity(userSay) != null && findCapacity(userSay) != "0To"){

                for (Computer i : comp){
                    if(i.getCapacity().equals(findCapacity(userSay))){
                        //botSay(i.toString());
                        result.add(i);
                    }
                    else continue;
                }
                if (result.size() ==0) botSay("vide " + userSay);

                else {
                    for(Item i: result){
                        botSay(i.toString());
                    }
                }

            }
            else {
                result = comp;
                botSay("Pas de resultat pour " + userSay);
                for(Computer i: comp){
                    botSay(i.toString());
                }
            }
            return result;
        }

        public void keyWordRam(String userSay, ArrayList<Computer> comp){
            botSay("RAM");
            if (findRam(userSay) !=-1 && findRam(userSay) !=0){
                ArrayList<Computer> newLRam = new ArrayList<Computer>();
                for (Computer i : comp){
                    if(i.getRAM() == findRam(userSay)){
                        //botSay(i.toString());
                        newLRam.add(i);
                    }
                    else continue;
                }
                if (newLRam.size() ==0) botSay("vide " + userSay);

                else {
                    for(Item i: newLRam){
                        botSay(i.toString());
                    }
                }

            }
            else {
                botSay("Pas de resultat pour " + userSay);
                for(Computer i: comp){
                    botSay(i.toString());
                }
            }
        }


        public ArrayList<String> listKeywords(String userSay)
        {
            ArrayList<String> list = new ArrayList<String>();
            userSay = userSay.toLowerCase();
            userSay = sansAccents(userSay);
            int count = 0;
            for (String keyWord : keyWordListC) {
                if (userSay.contains(keyWord)) {
                    list.add(keyWord);
                }
            }
            return list;
        }


        public int findNum(int start, int end, String[] words){
            int num =0;
            for(int i = start; i<=end; i++){

                try {
                    int d = Integer.parseInt(words[i]);
                    num =d;
                    break;
                } catch (NumberFormatException | NullPointerException nfe) {

                }

            }
            return num;

        }

        public int findRam (String userSay){
            String[] words = userSay.split(" ");
            int ram =-1;
            for(int i =0; i< words.length; i++){
                if(words[i].contains("ram") && i>=3 && i<words.length-3){
                    ram = findNum(i-3,i+3, words);
                }
                if(words[i].contains("ram") && i>=3 && i>words.length-3){ // if the key is at the end of the sentence
                    ram = findNum(i-3,words.length-1, words);
                }
                if(words[i].contains("ram") && i<=3 && i<words.length-3){
                    ram = findNum(i-1,i+3, words);
                }
            }
            return ram;
        }

        public int findWeight(String userSay){
            int weight = 0;
            String pattern ="-?\\d+"+" kilo";
            String pattern1 ="-?\\d+"+"kilo";
            String pattern2 ="-?\\d+"+"kg";
            String pattern3 ="-?\\d+"+" kg";
            String pattern4 ="-?\\d+"+"g";
            String pattern5 ="-?\\d+"+" g";
            String pattern6 ="-?\\d+"+" gramme";
            String pattern7 ="-?\\d+"+" grammes";
            String[] words = userSay.split(" ");
            String[] weightSynonyms = {"kilo","kg"};

                for(int i =0; i< words.length; i++) {
                    if(words[i].matches(pattern2) | words[i].matches(pattern1) | userSay.matches(pattern3) | userSay.matches(pattern)) {
                        weight = extractNumber(words[i]) *1000;
                        break;

                    }

                    if(words[i].matches(pattern4) || userSay.matches(pattern5) || userSay.matches(pattern6) || userSay.matches(pattern7)){
                        weight = extractNumber(words[i]);
                        break;
                    }
                    if (inList(weightSynonyms,words[i]) && i >= 3 && i < words.length - 3) {
                        weight = findNum(i - 1, i + 1, words);
                        break;
                    }
                    if (inList(weightSynonyms,words[i]) && i >= 3 && i > words.length - 3) { // if the key is at the end of the sentence
                        weight = findNum(i - 1, words.length - 1, words);
                        break;
                    }
                    if (inList(weightSynonyms,words[i]) && i <= 3 && i < words.length - 3) {
                        weight = findNum(i - 1, i + 1, words);
                        break;
                    }


                }
                if (weight<=3) weight = weight *1000;


            return weight;
        }



        public int findPrice (String userSay){
            String[] words = userSay.split(" ");
            int price = -1;
            String[] priceSynonyms = {"\u20ac","euros","eu","euro"};
            for(int i =0; i< words.length; i++){
                if(inList(priceSynonyms,words[i]) && i>=3 && i<words.length-3){
                    price = findNum(i-3,i+3, words);
                }
                if(inList(priceSynonyms,words[i]) && i>=3 && i>words.length-3){ // if the key is at the end of the sentence
                    price = findNum(i-3,words.length-1, words);
                }
                if(inList(priceSynonyms,words[i]) && i<=3 && i<words.length-3){
                    price = findNum(i-1,i+3, words);
                }

                String pattern ="-?\\d+"+"\u20ac";
                if(words[i].matches(pattern)){ price = extractNumber(words[i]);}

            }



            return price;
        }

        public boolean inList(String[] list, String word ){
            boolean inList = false;
            for (String s : list){
                if (s.contains(word)){
                    inList = true;
                    break;
                }
            }
            return inList;

        }


        public String findCapacity (String userSay){
            String[] words = userSay.split(" ");
            String capacityString= null;
            int capacity =-1;
            String[] capacitySynonyms = {"capacité","stockage", "espace", "memoire", "d'espace"};
            for(int i =0; i< words.length; i++){
                if(inList(capacitySynonyms,words[i]) && i>=3 && i<words.length-3){
                    capacity = findNum(i-3,i+3, words);
                }
                if(inList(capacitySynonyms,words[i]) && i>=3 && i>words.length-3){ // if the key is at the end of the sentence
                    capacity = findNum(i-3,words.length-1, words);
                }
                if(inList(capacitySynonyms,words[i]) && i<=3 && i<words.length-3){
                    capacity = findNum(i-1,i+3, words);
                }

            }
            if (capacity>2) capacityString = capacity+"Go";
            else capacityString = capacity+"To";

            return capacityString;
        }

        static public String sansAccents(String s) {

            s = Normalizer.normalize(s, Normalizer.Form.NFD);
            s= 	s.replaceAll("[^\\p{ASCII}]", "");
            return s;
        }

        public static int extractNumber(final String str) {

            if(str == null || str.isEmpty()) return -1;

            StringBuilder sb = new StringBuilder();
            boolean found = false;
            for(char c : str.toCharArray()){
                if(Character.isDigit(c)){
                    sb.append(c);
                    found = true;
                } else if(found){
                    // If we already found a digit before and this char is not a digit, stop looping
                    break;
                }
            }

            return Integer.parseInt(sb.toString());
        }

        public List<Item> filter (List<Item> items, ArrayList<String> keywords){

            if (keywords.size() == 0) return items;

            else {
                Iterator<Item> it = items.iterator();

                while (it.hasNext()){
                    Item i = it.next();
                    for (String s : keywords){
                        if (!(i.toString().toLowerCase()).contains(s)) {
                            //printItems(items);
                            it.remove();
                                //System.out.println(s);
                                //printItems(items);

                            break;
                        }
                    }
                }
            }
            return items;
        }

        public void printItems(List<Item> items){
            for (Item i : items){
                System.out.println(i);
            }
        }




        public void botSay(String bot) {
            textArea.append("Bot : "+ bot + "\n");
        }

        public static void main(String[] args) {
            Bot b = new Bot();
            /*List<Item> l = b.ReaderFileJson("data.json", "Ordinateur");
            ArrayList<String> keys = new ArrayList<String>();
            keys.add("computer");
            keys.add("mac");
            keys.add("air");
            //b.printItems(b.filter(l, b.keyWordListC,0));
            b.printItems(b.filter(l, keys));*/
            String words = "je cherche un ordi de 2 kg 14kilos 15 g";
            String words2 = "je cherche 16 giga de ram et 32 giga de memoire dell de new";
            //System.out.println(b.findNum(0,3, words));
            System.out.println(b.findWeight(words));
            //System.out.println(b.findCapacity(words2));
            Pattern p = Pattern.compile("[" +CURRENCY_SYMBOLS + "][\\d,]+");

            String s = "₦13,000 or to someone in Saudi Arabia for 45€ or Afghanistan for ؋350";
            //System.out.println(b.findPrice(s));

            /*String input="25\u20ac";
            String pattern ="-?\\d+"+"\u20ac";
            if(input.matches(pattern)){ // any positive or negetive integer or not!
                System.out.println(extractNumber(input));
            }*/
        }


}




