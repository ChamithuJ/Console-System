

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    private static void openGui(WestminsterShoppingManager shoppingManager) {
        new MyFrame(shoppingManager);
    }

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            shoppingManager.displayMenu();

            try {
                System.out.print("Enter Number to continue : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 0:
                        shoppingManager.saveProductsToFile();
                        System.out.println("Exiting...");
                        break;
                    case 1:
                        shoppingManager.addProductFromConsole(scanner);
                        break;
                    case 2:
                        shoppingManager.deleteProductFromConsole(scanner);
                        break;
                    case 3:
                        shoppingManager.printProductList();
                        break;
                    case 4:
                        shoppingManager.saveProductsToFile();
                        break;
                    case 5:
                        shoppingManager.loadProductsFromFile();
                        break;
                    case 6:
                        openGui(shoppingManager);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException var5) {
                System.err.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                choice = -1;
            }
        } while(choice != 0);

        scanner.close();
    }
}
