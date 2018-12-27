package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.ArrayList;

public class Bot extends JFrame {

        private JTextField textEnter = new JTextField();
        private JTextArea textArea = new JTextArea();
        private JButton ordi = new JButton("Computer");
        ArrayList<String> keyWordListS = new ArrayList<String>(); //smartphone
        ArrayList<String> keyWordListC = new ArrayList<String>(); //computer

        public Bot() {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(600,600);
            this.setVisible(true);
            this.setResizable(false);
            this.setTitle("Tech Bot");
            this.setLayout(null);
            this.setBackground(Color.blue);

            this.keyWordListS.add("samsung");
            this.keyWordListS.add("smartphone");
            this.keyWordListS.add("telephone");

            this.keyWordListC.add("ordinateur");
            this.keyWordListC.add("ram");
            this.keyWordListC.add("gb");


            textEnter.setLocation(2,540);
            textEnter.setSize(590,30);
            this.KeyWordSmartphone(textEnter.getText());
            textEnter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    // TODO Auto-generated method stub
                    String uText = textEnter.getText();
                    textArea.append("You : " + uText + "\n");
                    if (uText.contains("?")) botSay("Fine and you?");
                    textEnter.setText("");
                    KeyWordSmartphone(uText);
                }
            });

            textArea.setLocation(15,5);
            textArea.setSize(540,510);
            this.add(textEnter);
            this.add(textArea);
            //textArea.setLayout(null);

            botSay("Hello");
            //textArea.colo

		/*JButton ordi = new JButton("Computer");
		ordi.setLocation(500,20);
		ordi.setSize(20,20);

		ordi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botSay("Cool you chose Computeur");
			}
		});
		this.add(ordi);*/
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

        public void KeyWordSmartphone(String userSay) {
            userSay = userSay.toLowerCase();
            userSay = sansAccents(userSay);
            String num = extractNumber(userSay);
            String result ="";
            //botSay("find 2 : " + userSay2);
            for (String keyWord : keyWordListS) {
                if (userSay.contains(keyWord)) {
                    result = result + " " + keyWord;
                    botSay("keywords : " + keyWord +" "+ num);

                    textEnter.setText("");
                }
            }

        }

        public ArrayList<String> listKeywords(String userSay)
        {
            ArrayList<String> list = new ArrayList<String>();
            userSay = userSay.toLowerCase();
            userSay = sansAccents(userSay);
            int count = 0;
            for (String keyWord : keyWordListS) {
                if (userSay.contains(keyWord)) {
                    list.add(keyWord);
                }
            }
            return list;
        }



        public String accent(String word) {
            String mot = "";
            for (int i =0; i< word.length(); i++) {
                if(word.substring(i,i+1) == "é") {
                    if (i != 0)
                        mot= word.substring(0,i-1) + "e" + word.substring(i+1,word.length());
                    else mot = "e" + word.substring(i+1,word.length());
                }
                else continue; //{mot+= word.substring(i+1,word.length()); break;}
            }
            return mot;
        }

        static public String sansAccents(String s) {

            s = Normalizer.normalize(s, Normalizer.Form.NFD);
            s= 	s.replaceAll("[^\\p{ASCII}]", "");
            return s;
        }

        public static String extractNumber(final String str) {

            if(str == null || str.isEmpty()) return "";

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

            return sb.toString();
        }


        public void botSay(String bot) {
            textArea.append("Bot : "+ bot + "\n");
        }

        public static void main(String[] args) {
            new Bot();

        }



}
