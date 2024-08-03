
package easycontacts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;


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

    
    public static void main(String[] args) {
      
    }
    
}
