package org.tframework.example.service;

import io.javalin.http.NotFoundResponse;
import lombok.RequiredArgsConstructor;
import org.tframework.core.elements.annotations.Element;
import org.tframework.example.model.Person;
import org.tframework.example.repository.PersonRepository;

import java.util.List;

@Element
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person getPersonByName(String name) {
        return personRepository.getPersons().stream()
                .filter(person -> person.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotFoundResponse("Person not found: " + name));
    }

    public List<Person> getPersonsOlderThan(int age) {
        return personRepository.getPersons().stream()
                .filter(person -> person.age() > age)
                .toList();
    }
}
