package org.tframework.example.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.core.elements.postprocessing.annotations.PostInitialization;
import org.tframework.example.model.Person;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Element
public class PersonRepository {

    @InjectElement
    private RepositoryProperties props;

    @Getter
    private List<Person> persons;

    @PostInitialization
    public void loadPersons() throws Exception {
        //read file from filePath from the resources folder
        //parse the file and create a list of persons
        try (var file = getClass().getClassLoader().getResourceAsStream(props.getFilePath())) {
            if(file != null) {
                persons = new BufferedReader(new InputStreamReader(file))
                        .lines()
                        .map(this::csvLineToPerson)
                        .toList();
            } else {
                log.error("File not found: {}", props.getFilePath());
                persons = List.of();
            }
        }
    }

    private Person csvLineToPerson(String line) {
        String[] parts = line.split(props.getSeparator());
        return new Person(parts[0], Integer.parseInt(parts[1]), parts[2]);
    }
}
