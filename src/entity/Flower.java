package entity;

public class Flower {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Flower(int id, String name, double price, int stock) {
        this(name, price, stock);

        setId(id);
    }

    public Flower(String name, double price, int stock) {
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return id + ". " + name + " - " + price + "tg";
    }
}
