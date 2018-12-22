package Model;

import java.util.List;

public class Node {
    private List<Item> possibilities;
    private String question;
    private Node[] Children;

    Node(List<Item> pos){
        possibilities = pos;
    }

    public List<Item> getPossibilities() {
        return possibilities;
    }
}