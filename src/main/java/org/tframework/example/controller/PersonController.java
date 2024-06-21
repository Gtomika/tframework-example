package org.tframework.example.controller;

import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import io.javalin.router.Endpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.elements.annotations.Element;
import org.tframework.example.service.PersonService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * The person controller element defines related {@link Endpoint} elements. These
 * will be later picked up in {@link org.tframework.example.config.WebConfig} element.
 */
@Slf4j
@Element
@RequiredArgsConstructor
public class PersonController {

    public static final String PERSON_BY_NAME_ENDPOINT = "/person/{name}";
    public static final String PERSONS_OLDER_THAN_ENDPOINT = "/person/older-than/{age}";

    private final PersonService personService;

    @Element(name = "getPersonByNameEndpoint")
    public Endpoint getPersonByNameEndpoint() {
        Handler handler = ctx -> {
            var name = URLDecoder.decode(ctx.pathParam("name"), StandardCharsets.UTF_8);
            ctx.json(personService.getPersonByName(name));
        };
        return new Endpoint(HandlerType.GET, PERSON_BY_NAME_ENDPOINT, handler);
    }

    @Element(name = "getPersonsOlderThanEndpoint")
    public Endpoint getPersonsOlderThanEndpoint() {
        Handler handler = ctx -> {
            var age = Integer.parseInt(ctx.pathParam("age"));
            ctx.json(personService.getPersonsOlderThan(age));
        };
        return new Endpoint(HandlerType.GET, PERSONS_OLDER_THAN_ENDPOINT, handler);
    }

}
