package Model;

public abstract class Item {
    private String name;
    private String brand;
    private double price;

    private String getName() {
        return name;
    }

    Item(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : " + name;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equality = false;
        if(this.getClass().equals(obj.getClass())){
            Item item = (Item) obj;
            if(item.getName().equals(name) && brand.equals(item.brand))
                equality = true;
        }
        return equality;
    }
}