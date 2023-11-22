package manager;

import entity.Client;
import entity.Product;
import entity.History;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveManager {

    private final String PRODUCT_FILENAME = "products";
    private final String CLIENT_FILENAME = "clients";
    private final String HISTORY_FILENAME = "Histories";

    public Product[] loadProducts() {
        Product[] products = new Product[0];
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(PRODUCT_FILENAME);
            ois = new ObjectInputStream(fis);
            products = (Product[]) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.out.printf("File \"%s\" not found!%n", PRODUCT_FILENAME);
        } catch (IOException ex) {
            System.out.println("Error I/O!");
        } catch (ClassNotFoundException ex) {
            System.out.printf("Class \"%s\" not found!%n", PRODUCT_FILENAME);
        }
        return products;
    }

    public void saveProducts(Product[] products) {
        ObjectOutputStream oos;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(PRODUCT_FILENAME);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(products);
            oos.flush();
        } catch (FileNotFoundException ex) {
            System.out.printf("File \"%s\" not found!%n", PRODUCT_FILENAME);
        } catch (IOException ex) {
            System.out.println("Error I/O!");
        }
    }

    public Client[] loadClients() {
        Client[] clients = new Client[0];
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(CLIENT_FILENAME);
            ois = new ObjectInputStream(fis);
            clients = (Client[]) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.out.printf("File \"%s\" not found!%n", CLIENT_FILENAME);
        } catch (IOException ex) {
            System.out.println("Error I/O!");
        } catch (ClassNotFoundException ex) {
            System.out.printf("Class \"%s\" not found!%n", CLIENT_FILENAME);
        }
        return clients;
    }

    public void saveClients(Client[] clients) {
        ObjectOutputStream oos;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(CLIENT_FILENAME);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(clients);
            oos.flush();
        } catch (FileNotFoundException ex) {
            System.out.printf("File \"%s\" not found!%n", CLIENT_FILENAME);
        } catch (IOException ex) {
            System.out.println("Error I/O!");
        }
    }

    public History[] loadHistories() {
        History[] Histories = new History[0];
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(HISTORY_FILENAME);
            ois = new ObjectInputStream(fis);
            Histories = (History[]) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.out.printf("File \"%s\" not found!%n", HISTORY_FILENAME);
        } catch (IOException ex) {
            System.out.println("Error I/O!");
        } catch (ClassNotFoundException ex) {
            System.out.printf("Class \"%s\" not found!%n", HISTORY_FILENAME);
        }
        return Histories;
    }

    public void saveHistories(History[] Histories) {
        ObjectOutputStream oos;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(HISTORY_FILENAME);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Histories);
            oos.flush();
        } catch (FileNotFoundException ex) {
            System.out.printf("File \"%s\" not found!%n", HISTORY_FILENAME);
        } catch (IOException ex) {
            System.out.println("Error I/O!");
        }
    }
}

