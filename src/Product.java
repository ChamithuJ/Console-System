

import java.io.Serializable;

abstract class Product implements Serializable {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    public Product() {
    }

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return this.availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void someAbstractMethod() {
        System.out.println("Default implementation of someAbstractMethod in Product");
    }

    public abstract String getProductType();

    public static Product createProduct(String type, String productId, String productName, int availableItems, double price) {
        switch (type.toLowerCase()) {
            case "clothing":
                return new Clothing(productId, productName, availableItems, price, "Medium", "Blue");
            case "electronics":
                return new Electronics(productId, productName, availableItems, price, "Brand", 1.0);
            default:
                throw new IllegalArgumentException("Invalid product type: " + type);
        }
    }
}

