//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.List;
import java.util.Scanner;

interface ShoppingManager {
    void addProduct(Product var1);

    void deleteProductFromConsole(Scanner var1);

    void removeProduct(Product var1);

    void printProductList();

    List<Product> getAllProducts();

    void saveProductsToFile();

    Product findProductById(String var1);
}

