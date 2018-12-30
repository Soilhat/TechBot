package Model;

public class Computer extends Item{
    String OS;
    int RAM;
    String capacity;
    double size;
    int weight;
    String processor;
    String GPU;

    public Computer(String name, String brand, double price, int RAM, String capacity, double size, int weight, String processor, String GPU) {
        super(name, brand, price);
        this.RAM = RAM;
        this.capacity = capacity;
        this.size = size;
        this.weight = weight;
        this.processor = processor;
        this.GPU = GPU;
    }

    @Override
    public String toString() {
        return super.toString() + /*", OS : " + OS + */", RAM : " + RAM + "Go, capacity : " + capacity + ", size : " + size + ", processor : " + processor + ", GPU : " + GPU;
    }
}