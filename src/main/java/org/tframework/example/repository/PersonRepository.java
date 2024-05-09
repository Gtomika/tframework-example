package org.tframework.example.repository;

import lombok.Getter;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectProperty;
import org.tframework.core.elements.postprocessing.annotations.PostInitialization;
import org.tframework.example.model.Person;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Element
public class PersonRepository {

    @InjectProperty("repository.file-path")
    private String filePath;

    @Getter
    private List<Person> persons;

    @PostInitialization
    public void loadPersons() throws Exception {
        //read file from filePath from the resources folder
        //parse the file and create a list of persons
        try (var file = getClass().getClassLoader().getResourceAsStream(filePath)) {
            persons = new BufferedReader(new InputStreamReader(file))
                    .lines()
                    .map(this::csvLineToPerson)
                    .toList();
        }
    }

    private Person csvLineToPerson(String line) {
        String[] parts = line.split(",");
        return new Person(parts[0], Integer.parseInt(parts[1]), parts[2]);
    }

}
