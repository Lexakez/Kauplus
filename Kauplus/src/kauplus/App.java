package kauplus;

import entity.Client;
import entity.Product;
import entity.History;
import manager.SaveManager;
import manager.ClientManager;
import manager.ProductManager;
import manager.HistoryManager;
import java.util.Arrays;
import java.util.Scanner;
import tools.InputProtection;

public class App {
    private final Scanner scanner; 
    private Product[] products;
    private Client[] clients;
    private History[] Histories;
    private final ProductManager productManager;
    private final ClientManager clientManager;
    private final HistoryManager HistoryManager;
    private final SaveManager SaveManager;

    public App() {
        this.scanner = new Scanner(System.in);
        this.SaveManager = new SaveManager();
        this.products = SaveManager.loadProducts();
        this.clients = SaveManager.loadClients();
        this.Histories = SaveManager.loadHistories();
        this.productManager = new ProductManager(scanner);
        this.clientManager = new ClientManager(scanner);
        this.HistoryManager = new HistoryManager(scanner, clientManager, productManager);
    }
    
    public void run() {
        boolean repeat = true;
        System.out.println("------- Store -------");
        do {
            System.out.println("List tasks:");
            System.out.println("0. Exit");
            System.out.println("1. Add new product");
            System.out.println("2. Print list products");
            System.out.println("3. Add new client");
            System.out.println("4. Print list clients");
            System.out.println("5. Make a purchase");
            System.out.println("6. Print list purchases");
            
            System.out.print("Enter task number: ");
            int task = InputProtection.intInput(0, 6);
            
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    this.products = Arrays.copyOf(this.products, this.products.length + 1);
                    this.products[this.products.length - 1] = productManager.addProduct();
                    SaveManager.saveProducts(this.products);
                    break;
                case 2:
                    productManager.printListProducts(products);
                    break;
                case 3:
                    this.clients = Arrays.copyOf(this.clients, this.clients.length + 1);
                    this.clients[this.clients.length - 1] = clientManager.addClient(clients);
                    SaveManager.saveClients(clients);
                    break;
                case 4:
                    clientManager.printListClients(clients);
                    break;
                case 5:
                    History newPurchase = HistoryManager.makePurchase(products, clients);
                    if (newPurchase != null) {
                        // Если покупка была успешной (возможно, стоит добавить проверку на null),
                        // то обновляем баланс клиента
                        double purchaseCost = newPurchase.getProduct().getPrice();
                        Client client = newPurchase.getClient();
                        client.setBalance(client.getBalance() - purchaseCost);
                        
                        // Добавляем новую покупку в историю
                        this.Histories = Arrays.copyOf(this.Histories, this.Histories.length + 1);
                        this.Histories[this.Histories.length - 1] = newPurchase;
                        SaveManager.saveHistories(Histories);
                        
                        // Удаляем продукт из списка продуктов
                        Product purchasedProduct = newPurchase.getProduct();
                        this.products = Arrays.stream(this.products)
                            .filter(product -> !product.equals(purchasedProduct))
                            .toArray(Product[]::new);

                        // Сохраняем новый баланс клиента и обновленный список продуктов
                        SaveManager.saveClients(clients);
                        SaveManager.saveProducts(this.products);


                    }
                    break;
                case 6:
                    HistoryManager.printListPurchases(Histories);
                    break;
                default:
                    System.out.println("Select from the list of tasks!");
            }
            System.out.println("-----------------------");
        } while (repeat);
        System.out.println("Goodbye!");
    }
}
