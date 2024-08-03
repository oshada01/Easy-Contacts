
package easycontacts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;


class Contact{
    String name;
    String phoneNumber;
    String email;
    String address;
    String notes;
    
    Contact(String name, String phoneNumber, String email, String address, String notes){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.notes = notes;
    }
    
    @Override
    public String toString(){
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email + ", Address: " + address + ", Notes: " + notes ;
    }
}

class Node {
    Contact contact;
    Node next;

    Node(Contact contact) {
        this.contact = contact;
        this.next = null;
    }
}


class Operations {
    Node head;

    void add(Contact contact) {
        Node newNode = new Node(contact);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    Contact search(String keyword) {
        Node current = head;
        while (current != null) {
            if (current.contact.name.contains(keyword) ||
                current.contact.phoneNumber.contains(keyword) ||
                current.contact.email.contains(keyword) ||
                current.contact.address.contains(keyword)) {
                return current.contact;
            }
            current = current.next;
        }
        return null;
    }

    boolean delete(String name) {
        if (head == null) return false;
        if (head.contact.name.equals(name)) {
            head = head.next;
            return true;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.contact.name.equals(name)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }
    void sort() {
        if (head == null) return;
        ArrayList<Contact> contactsArray = toArray();
        contactsArray.sort(Comparator.comparing(contact -> contact.name));
        fromArray(contactsArray);
    }

    void display() {
        Node current = head;
        while (current != null) {
            System.out.println(current.contact);
            current = current.next;
        }
    }
    ArrayList<Contact> toArray() {
        ArrayList<Contact> array = new ArrayList<>();
        Node current = head;
        while (current != null) {
            array.add(current.contact);
            current = current.next;
        }
        return array;
    }

    void fromArray(ArrayList<Contact> array) {
        head = null;
        for (Contact contact : array) {
            add(contact);
        }
    }
    
        void backup(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Node current = head;
            while (current != null) {
                bw.write(current.contact.name + "," + current.contact.phoneNumber + "," + current.contact.email + "," +
                         current.contact.address + "," + current.contact.notes + "\n");
                current = current.next;
            }
            System.out.println("Contacts backed up successfully.");
        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }

    void restore(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            head = null;
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 5) {
                    add(new Contact(details[0], details[1], details[2], details[3], details[4]));
                } else {
                    System.out.println("Invalid contact format in restore file: " + line);
                }
            }
            System.out.println("Contacts restored successfully.");
        } catch (IOException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }

void importContacts(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 5) {
                    add(new Contact(details[0], details[1], details[2], details[3], details[4]));
                } else {
                    System.out.println("Invalid contact format in import file: " + line);
                }
            }
            System.out.println("Contacts imported successfully.");
        } catch (IOException e) {
            System.out.println("Error during import: " + e.getMessage());
        }
    }
}
  

public class EasyContacts {
    private Operations contacts;
     private Stack<Contact> deleteStack;
    
    public EasyContacts() {
        contacts = new Operations();
        deleteStack = new Stack<>();
        
    }
    public void addContact(String name, String phoneNumber, String email, String address, String notes) {
        Contact newContact = new Contact(name, phoneNumber, email, address, notes);
        if (contacts.search(name) == null) {
            contacts.add(newContact);
            System.out.println("Contact added successfully.");
        } else {
            System.out.println("Duplicate contact. Not added.");
        }
    }

    public void searchContact(String keyword) {
        Contact contact = contacts.search(keyword);
        if (contact != null) {
            System.out.println(contact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void deleteContact(String name) {
        Contact contact = contacts.search(name);
        if (contact != null && contacts.delete(name)) {
            deleteStack.push(contact);
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void undoDelete() {
        if (!deleteStack.isEmpty()) {
            contacts.add(deleteStack.pop());
            System.out.println("Undo successful. Contact restored.");
        } else {
            System.out.println("No contact to undo.");
        }
    }
    
    public void sortContacts() {
        contacts.sort();
        System.out.println("Contacts sorted successfully.");
        contacts.display();
    }

    public void displayContacts() {
        contacts.display();
    }

    public void backupContacts(String filename) {
        contacts.backup(filename);
    }

    public void restoreContacts(String filename) {
        contacts.restore(filename);
    }

     public void importContacts(String filename) {
        contacts.importContacts(filename);
    }
     
    public static void main(String[] args) {
      EasyContacts phonebook = new EasyContacts();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPhonebook Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Undo Delete");
            System.out.println("5. Sort and Display Contacts");
            System.out.println("6. Display All Contacts");
            System.out.println("7. Backup Contacts");
            System.out.println("8. Restore Contacts");
            System.out.println("9. Import Contacts");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter phone number: ");
                        String phoneNumber = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter special notes: ");
                        String notes = scanner.nextLine();
                        phonebook.addContact(name, phoneNumber, email, address, notes);
                        break;
                    case 2:
                        System.out.print("Enter keyword to search: ");
                        String keyword = scanner.nextLine();
                        phonebook.searchContact(keyword);
                        break;
                    case 3:
                        System.out.print("Enter name to delete: ");
                        String nameToDelete = scanner.nextLine();
                        phonebook.deleteContact(nameToDelete);
                        break;
                    case 4:
                        phonebook.undoDelete();
                        break;
                    case 5:
                        phonebook.sortContacts();
                        break;
                    case 6:
                        phonebook.displayContacts();
                        break;
                    case 7:
                        System.out.print("Enter backup file name: ");
                        String backupFile = scanner.nextLine();
                        phonebook.backupContacts(backupFile);
                        break;
                    case 8:
                        System.out.print("Enter backup file name to restore: ");
                        String restoreFile = scanner.nextLine();
                        phonebook.restoreContacts(restoreFile);
                        break;
                    case 9:
                        System.out.print("Enter import file name: ");
                        String importFile = scanner.nextLine();
                        phonebook.importContacts(importFile);
                        break;
                    case 10:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }   catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

        }
    
    }
}
