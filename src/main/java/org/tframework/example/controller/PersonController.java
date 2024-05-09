package org.tframework.example.controller;

import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.elements.annotations.Element;
import org.tframework.example.service.PersonService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Element
@RequiredArgsConstructor
public class PersonController {

    public static final String PERSON_BY_NAME_ENDPOINT = "/person/{name}";
    public static final String PERSONS_OLDER_THAN_ENDPOINT = "/person/older-than/{age}";

    private final PersonService personService;

    public Handler getPersonByNameEndpoint() {
        return ctx -> {
            var name = URLDecoder.decode(ctx.pathParam("name"), StandardCharsets.UTF_8);
            ctx.json(personService.getPersonByName(name));
        };
    }

    public Handler getPersonsOlderThanEndpoint() {
        return ctx -> {
            var age = Integer.parseInt(ctx.pathParam("age"));
            ctx.json(personService.getPersonsOlderThan(age));
        };
    }

}
