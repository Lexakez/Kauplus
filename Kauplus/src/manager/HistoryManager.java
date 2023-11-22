

package manager;

import entity.Client;
import entity.Product;
import entity.History;
import java.util.GregorianCalendar;
import java.util.Scanner;
import tools.InputProtection;

public class HistoryManager {

    private final Scanner scanner;
    private final ProductManager productManager;
    private final ClientManager clientManager;
    
    public HistoryManager(
            Scanner scanner,
            ClientManager clientManager,
            ProductManager productManager) {
        this.scanner = scanner;
        this.clientManager = clientManager;
        this.productManager = productManager;
    }


    public History makePurchase(Product[] products, Client[] clients) {
        History History = new History();
        productManager.printListProducts(products);
        System.out.print("Enter number product from list: ");
        int numberProduct = InputProtection.intInput(1, products.length);
        History.setProduct(products[numberProduct - 1]);
        clientManager.printListClients(clients);
        System.out.print("Enter number client from list: ");
        int numberClient = InputProtection.intInput(1, clients.length);
        History.setClient(clients[numberClient - 1]);
        clients[numberClient - 1].increase(1);
        History.setPurchaseDate(new GregorianCalendar().getTime());

        return History;
    }

    public void printListPurchases(History[] Histories) {
    System.out.println("----- List purchases -----");
    for (int i = 0; i < Histories.length; i++) {
        Product product = Histories[i].getProduct();
        String productName = (product != null) ? product.getName() : "Unknown Product";
        System.out.printf("%d. %s. bought by %s %s%n",
                i + 1,
                productName,
                    Histories[i].getClient().getFirstName(),
                    Histories[i].getClient().getLastName(),
                    Histories[i].getPurchaseDate()
            );
        }
    }
}

