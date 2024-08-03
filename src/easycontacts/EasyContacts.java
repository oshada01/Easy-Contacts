
package easycontacts;


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



public class EasyContacts {

    
    public static void main(String[] args) {
      
    }
    
}
