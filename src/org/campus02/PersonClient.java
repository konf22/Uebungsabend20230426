package org.campus02;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PersonClient {

    public static void main(String[] args) {

        try (Socket server = new Socket("localhost", 1111);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
             ObjectInputStream ois = new ObjectInputStream(server.getInputStream())
        ) {

            // schicke id 25 und schreibe person auf die kommandozeile
            bw.write("get 25");
            bw.newLine(); // !!!!
            bw.flush(); // !!!!!
            // command an server gesendet
            // verarbeite antwort

            Person person25 = (Person) ois.readObject();
            System.out.println(person25);

            bw.write("GETALL");
            bw.newLine();
            bw.flush();

            // hier bekomme ich eine liste
            //ArrayList<Person> people = (ArrayList<Person>) ois.readObject();
            //System.out.println(people);


            // Jede Person wurde einzeln geschickt, bis schlussendlich ein null Objekt kommt
            Person person;
            while ((person = (Person) ois.readObject()) != null) {
                System.out.println(person);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
