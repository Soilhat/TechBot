package Model;

public class Cellphone extends Item{

    double size;
    int capacity;

    public Cellphone(String name, String brand, double price, double size, int capacity) {
        super(name, brand, price);
        this.size = size;
        this.capacity = capacity;
    }
}