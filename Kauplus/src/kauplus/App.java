package kauplus;

import entity.Client;
import entity.Product;
import entity.History;
import java.util.ArrayList;
import manager.SaveManager;
import manager.ClientManager;
import manager.ProductManager;
import manager.HistoryManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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
    public void printTotalProfit(History[] histories) {
        double totalProfit = 0;
        for (History history : histories) {
            if (history.getPurchaseDate() != null) {
                // Calculate profit only for returned products
                double purchaseCost = history.getProduct().getPrice();
                totalProfit += purchaseCost;
            }
        }
        System.out.printf("Total Profit: $%.2f%n", totalProfit);
    }
    

    public void run() {
        boolean repeat = true;
        System.out.println("------- Store -------");
        do {
            System.out.println("List tasks:");
            System.out.println("0. Exit");
            System.out.println("1. Edit products");
            System.out.println("2. Print list products");
            System.out.println("3. Edit clients");
            System.out.println("4. Print list clients");
            System.out.println("5. Make a purchase");
            System.out.println("6. Print list purchases");
            System.out.println("7. Total profit");
            System.out.println("8. Client rating");
            
            System.out.print("Enter task number: ");
            int task = InputProtection.intInput(0, 8);
            
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    System.out.println("0. Exit");
                    System.out.println("1. Add new product");
                    System.out.println("2. Edit product");
                    System.out.print("Enter task number: ");
                    int choice = InputProtection.intInput(0, 2);
                    switch (choice) {
                        case 1:
                            this.products = Arrays.copyOf(this.products, this.products.length + 1);
                            this.products[this.products.length - 1] = productManager.addProduct();
                            SaveManager.saveProducts(this.products);
                            break;
                        case 2:
                            productManager.printListProducts(products);
                            System.out.print("Enter number product from list: ");
                            int numberProduct = InputProtection.intInput(1, products.length);
                            products[numberProduct - 1] = productManager.addProduct();
                            SaveManager.saveProducts(this.products);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Select from the list of tasks!");
                    }
                    break;

                case 2:
                    productManager.printListProducts(products);
                    break;
                case 3:
                    System.out.println("0. Exit");
                    System.out.println("1. Add new client");
                    System.out.println("2. Edit client");
                    System.out.println("3. Add balance");
                    System.out.print("Enter task number: ");
                    choice = InputProtection.intInput(0, 3);
                    switch (choice) {
                        case 1:
                            this.clients = Arrays.copyOf(this.clients, this.clients.length + 1);
                            this.clients[this.clients.length - 1] = clientManager.addClient(clients);
                            SaveManager.saveClients(clients);
                            break;
                        case 2:
                            clientManager.printListClients(clients);
                            System.out.print("Enter number client from list: ");
                            int numberClient = InputProtection.intInput(1, clients.length);
                            clients[numberClient - 1] = clientManager.addClient(clients);
                            SaveManager.saveClients(clients);
                            break;
                        case 3:
                            clientManager.printListClients(clients);
                            System.out.print("Enter number client from list: ");
                            numberClient = InputProtection.intInput(1, clients.length);
                            System.out.print("Balance: ");                                    
                            clients[numberClient - 1].setBalance(scanner.nextDouble());
                            SaveManager.saveClients(clients);
                            break;
                        case 0: 
                            break;
                        default:
                            System.out.println("Select from the list of tasks!");
                            break;
                    }
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
                        boolean found = false;

                        Product[] updatedProducts = new Product[this.products.length - 1];
                        int index = 0;

                        for (Product product : this.products) {
                            if (!found && product.equals(purchasedProduct)) {
                                found = true;
                                continue;  // Skip the first occurrence
                            }
                            updatedProducts[index++] = product;
                        }

                        this.products = updatedProducts;



                        // Сохраняем новый баланс клиента и обновленный список продуктов
                        SaveManager.saveClients(clients);
                        SaveManager.saveProducts(this.products);


                    }
                    break;
                case 6:
                    HistoryManager.printListPurchases(Histories);
                    break;
                case 7:
                    printTotalProfit(Histories);
                    break;
                case 8:
                    List<Client> sortedClientsByPurchaseAmount = new ArrayList<>(Arrays.asList(clients));

                    // Сортировка с использованием компаратора
                    Collections.sort(sortedClientsByPurchaseAmount, Comparator.comparing(Client::getPurchaseAmount).reversed());

                    System.out.println("---Rating of clients by purchase amount---");
                    for (Client client : sortedClientsByPurchaseAmount) {
                        System.out.printf("%s %s. (%s) - Balance: $%.2f Purchases: %s %n",
                                client.getFirstName(),
                                client.getLastName(),
                                client.getPhoneNumber(),
                                client.getBalance(),
                                client.getPurchaseAmount()
                        );
                    }
                    break;

                default:
                    System.out.println("Select from the list of tasks!");
            }
            System.out.println("------------------------");
        } while (repeat);
        System.out.println("Goodbye!");
    }
}
