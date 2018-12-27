package Model;

public abstract class Item {
    private String name;
    private String brand;
    private double price;

    public Item(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : " + name;
    }
}
