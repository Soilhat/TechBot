package Model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Item> possibilities;
    private String specificity;
    private List<Node> children;

    Node(List<Item> pos){
        possibilities = pos;
        specificity = null;
        children = new ArrayList<>();
    }

    Node(List<Item> possibilities, String specificity)
    {
        this.possibilities = possibilities;
        this.specificity = specificity;
        children = new ArrayList<>();
    }

    public List<Item> getPossibilities() {
        return possibilities;
    }

    public void NewChild(Node child)
    {
        children.add(child);
    }

    public void PrintNode() {
        for(Item i : possibilities)
            System.out.println(i);
    }
}