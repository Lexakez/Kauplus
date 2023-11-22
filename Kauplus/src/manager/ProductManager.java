

package manager;

import tools.InputProtection;
import entity.Product;


import java.util.Scanner;

public class ProductManager {

    private final Scanner scanner;

    public ProductManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public Product addProduct() {
        System.out.println("----- Add product -----");
        Product product = new Product();
        System.out.print("Enter product name: ");
        product.setName(scanner.nextLine());
        System.out.print("Enter product price: ");
        product.setPrice(InputProtection.doubleInput(0, Double.MAX_VALUE));
        System.out.println("Added product: " + product.toString());
        return product;
    }

    public void printListProducts(Product[] products) {
        System.out.println("----- List products -----");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%d. %s - $%.2f%n",
                    i + 1,
                    products[i].getName(),
                    products[i].getPrice()
            );
        }
    }
}
