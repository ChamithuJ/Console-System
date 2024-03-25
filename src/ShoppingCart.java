

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private List<Product> items = new ArrayList();
    private Map<String, Integer> categoryCounts = new HashMap();
    private boolean firstPurchase = true;

    public ShoppingCart() {
    }

    public List<Product> getItems() {
        return new ArrayList(this.items);
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;

        Product item;
        for(Iterator var3 = this.items.iterator(); var3.hasNext(); totalPrice += item.getPrice()) {
            item = (Product)var3.next();
        }

        totalPrice -= this.calculateCategoryDiscount();
        totalPrice -= this.calculateFirstPurchaseDiscount();
        return Math.max(totalPrice, 0.0);
    }

    private double calculateCategoryDiscount() {
        double categoryDiscount = 0.0;
        Iterator var3 = this.categoryCounts.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry)var3.next();
            int itemCount = (Integer)entry.getValue();
            if (itemCount >= 3) {
                categoryDiscount += (double)itemCount * 0.2 * this.items.stream().filter((product) -> {
                    return product.getProductType().equalsIgnoreCase((String)entry.getKey());
                }).mapToDouble(Product::getPrice).sum();
            }
        }

        return categoryDiscount;
    }

    private double calculateFirstPurchaseDiscount() {
        if (this.firstPurchase) {
            this.firstPurchase = false;
            return 0.1 * this.items.stream().mapToDouble(Product::getPrice).sum();
        } else {
            return 0.0;
        }
    }

    public void addProduct(Product product) {
    }

    public List<Product> getCartItems() {
        return null;
    }

    public Collection<Object> addItem() {
        return null;
    }
}
