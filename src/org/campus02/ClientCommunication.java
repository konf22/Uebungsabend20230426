package org.campus02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCommunication implements Runnable {

    private Socket client;

    public ClientCommunication(Socket client) {
        this.client = client;
    }

    private void handleCommands() throws PersonLoadException {
        ArrayList<Person> people = new PersonLoader("data/persons.csv").load();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
             ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream())
        ) {
            // zum Schreiben von Objekten verwenden wir den ObjectOutputStream
            // zum Schreiben von Textdaten verwenden wir BufferedWriter/PrintWriter
            String command;
            while ((command = br.readLine()) != null) {
                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("client wants to exit");
                    break;
                }

                if (command.equalsIgnoreCase("getall")) {
                    // GETALL | geTAll | getall
                    //command.equals("GETALL") ==> "GETALL"

                    // schicken ganze Liste
                    //oos.writeObject(people);

                    for (Person person : people) {
                        oos.writeObject(person);
                    }
                    oos.writeObject(null);
                } else {
                    // Fälle: id 1
                    // id 24
                    // id 48
                    String[] cmds = command.split(" ");
                    if (cmds.length != 2) {
                        System.out.println("command error");
                        oos.writeObject(null);
                    } else {
                        int desiredId = Integer.parseInt(cmds[1]);
                        for (Person person : people) {
                            if (person.getId() == desiredId) {
                                oos.writeObject(person);
                            }
                        }
                    }
                }
                oos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            handleCommands();
        } catch (PersonLoadException e) {
            e.printStackTrace();
        }
    }
}
