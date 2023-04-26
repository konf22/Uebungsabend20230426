package org.campus02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PersonLoader {

    public static void main(String[] args) throws PersonLoadException {
        ArrayList<Person> people = new PersonLoader("data/persons.csv").load();
        System.out.println(people);
    }

    private String pathToFile;

    public PersonLoader(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public ArrayList<Person> load() throws PersonLoadException {
        ArrayList<Person> people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(pathToFile)
        )) {
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                String[] personData = line.split(";");
                // 1;Susi;Sorglos -> line
                // => line.split(";")
                // String[] data = {1;Susi;Sorglos}
                // data[1] == "Susi"

                int id = Integer.parseInt(personData[0]); // eigentlich müsste man hier auch auf die Exception reagieren
                String fn = personData[1];
                String ln = personData[2];
                Person p = new Person(id, fn, ln);
                people.add(p);
            }
            return people;
        } catch (FileNotFoundException e) {
            throw new PersonLoadException("file does not exist", e);
        } catch (IOException e) {
            throw new PersonLoadException("cannot read file", e);
        }
    }
 }
