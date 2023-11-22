

package manager;

import entity.Client;
import java.util.Scanner;

public class ClientManager {
    private final Scanner scanner;

    public ClientManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public Client addClient(Client[] clients) {
        Client client = new Client();
        System.out.println("----- Add client -----");
        System.out.print("First Name: ");
        client.setFirstName(scanner.nextLine());
        System.out.print("Last Name: ");
        client.setLastName(scanner.nextLine());
        System.out.print("Phone: ");
        client.setPhoneNumber(scanner.nextLine());
        System.out.print("Balance: ");
        client.setBalance(scanner.nextDouble());
        System.out.println("New client added!");
        return client;
    }

    public void printListClients(Client[] clients) {
        System.out.println("----- List clients -----");
        for (int i = 0; i < clients.length; i++) {
            System.out.printf("%d. %s %s. (%s) - Balance: $%.2f%n",
                    i + 1,
                    clients[i].getFirstName(),
                    clients[i].getLastName(),
                    clients[i].getPhoneNumber(),
                    clients[i].getBalance()
            );
        }
    }
}

