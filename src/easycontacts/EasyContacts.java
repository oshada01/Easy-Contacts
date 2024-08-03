
package easycontacts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
      
    }
    
}
