

import java.io.Serializable;

class Electronics extends Product implements Serializable {
    private String brand;
    private double warrantyPeriod;

    public Electronics() {
    }

    public Electronics(String productId, String productName, int availableItems, double price, String brand, double warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getProductType() {
        return "Electronics ";
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getWarrantyPeriod() {
        return this.warrantyPeriod;
    }

    public void setWarrantyPeriod(double warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}

