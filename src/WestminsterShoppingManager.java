import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private static final int MAX_PRODUCTS = 50; // final used to make the variable constant
    private List<Product> productList; //  declare the productList

    public List<Product> getProductList() {
        return productList;
    }
    public WestminsterShoppingManager() { // constructor
        this.productList = new ArrayList<>();
        loadProductsFromFile();
    }

    public void displayMenu() {     // display the menu


        System.out.println(""" 
                
                ------ Product Management Menue Options ------
                
                1. Add a new Product
                2. Remove a Product
                3. Display Product List
                4. Save to file
                5. Add to Shopping Cart
                6. Launch GUI
                0. Exit
               
                """);
    }




    void addProductFromConsole(Scanner scanner) { // add product from console
        boolean validProductType = false; // boolean variable

        do {
            try {
                System.out.println("Enter product type Clothing or Electronics (  type C or E) :");
                String productType = scanner.next();

                if (productType.equalsIgnoreCase("C") || productType.equalsIgnoreCase("E")) {
                    validProductType = true;
                    System.out.print("Enter the product id: ");
                    String productID = scanner.next();
                    System.out.print("Enter the product name: ");
                    String name = scanner.next();

                    int availableItem;
                    while (true) {
                        try {
                            System.out.print("Enter the available number of items: ");
                            availableItem = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer for available items.");
                            scanner.nextLine(); // Clear the buffer
                        }
                    }

                    double price;
                    while (true) {
                        try {
                            System.out.print("Enter price: ");
                            price = scanner.nextDouble();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid price.");
                            scanner.nextLine(); // Clear the buffer
                        }
                    }

                    if (productType.equalsIgnoreCase("C")) {
                        String[] validSizes = {"xs", "s", "m", "l", "xl", "xxl"};
                        String size;
                        while (true) {
                            System.out.print("Enter a valid size (" + String.join(", ", validSizes) + "): ");
                            size = scanner.next().toLowerCase();
                            if (java.util.Arrays.asList(validSizes).contains(size)) {
                                break;
                            } else {
                                System.out.println("Please enter a valid size.");
                            }
                        }

                        String color = "";
                        System.out.print("Enter color: ");
                        color = scanner.next();


                        Product product = new Clothing(productID, name, availableItem, price, size, color);
                        addProduct(product);
                    } else if (productType.equalsIgnoreCase("E")) {
                        System.out.print("Enter the brand here : ");
                        String brand = scanner.next();

                        Double warrantyPeriod;
                        while (true) {
                            try {
                                System.out.print("Enter warranty period : ");
                                warrantyPeriod = scanner.nextDouble();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid integer for the warranty period.");
                                scanner.nextLine(); // Clear the buffer
                            }
                        }

                        Product product = new Electronics(productID, name, availableItem, price, brand, warrantyPeriod);
                        addProduct(product);
                    }
                } else {
                    System.out.println("Please enter a valid product type!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter the correct data type.");
                scanner.nextLine(); // Clear the buffer
            }
        } while (!validProductType);
    }

    @Override
    public void addProduct(Product product) {
        if (product != null) {
            if (productList.size() < MAX_PRODUCTS) {
                productList.add(product);
                System.out.println("Product added: " + product.getProductName());
            } else {
                System.out.println("Cannot add more products. Maximum limit reached.");
            }
        } else {
            System.out.println("Cannot add a null product.");
        }
    }

    @Override
    public void deleteProductFromConsole(Scanner scanner) {
        try {
            System.out.print("Enter the product ID to delete: ");
            String productIdToDelete = scanner.next();
            Product productToDelete = findProductById(productIdToDelete);

            if (productToDelete != null) {
                removeProduct(productToDelete);
            } else {
                System.out.println("Product not found :" + productIdToDelete);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct ID.");
            scanner.nextLine(); // Clear the buffer
        }
    }


    @Override
    public void removeProduct(Product product) {
        if (productList.remove(product)) {
            System.out.println("Product removed: " + product.getProductName());
            System.out.println("Total number of products left: " + productList.size());
        } else {
            System.out.println("Product not found: " + product.getProductName());
        }
    }

    @Override
    public void printProductList() {
        System.out.println("Product List:");

        try {
            List<Product> productList = getAllProducts();
            Collections.sort(productList, Comparator.comparing(Product::getProductId));

            for (Product product : productList) {
                System.out.print("Type: " + product.getProductType() + " ID: " + product.getProductId() +
                        ", Name: " + product.getProductName() +
                        ", Available Items: " + product.getAvailableItems() +
                        ", Price: " + product.getPrice());

                if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    System.out.println(", Size: " + clothing.getSize() + ", Color: " + clothing.getColor());
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    System.out.println(", Brand: " + electronics.getBrand() + ", Warranty Period: " + electronics.getWarrantyPeriod() + " years");
                } else {
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing the product list: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public void saveProductsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.dat"))) {
            List<Product> productsToSave = new ArrayList<>();

            for (Product product : productList) {
                if (product instanceof Clothing || product instanceof Electronics) {
                    productsToSave.add(product);
                }
            }

            oos.writeObject(productsToSave);
            System.out.println("Products saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving products to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadProductsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.dat"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                List<Product> loadedProducts = (List<Product>) obj;

                productList.clear(); // Clear the existing list
                for (Product product : loadedProducts) {
                    productList.add(product);
                }

                System.out.println("Products loaded from file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }
    }

    @Override
    public Product findProductById(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
}

