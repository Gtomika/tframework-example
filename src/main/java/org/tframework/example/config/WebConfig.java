package org.tframework.example.config;

import io.javalin.Javalin;
import lombok.RequiredArgsConstructor;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectProperty;
import org.tframework.core.elements.postprocessing.annotations.PostInitialization;
import org.tframework.example.controller.PersonController;

@Element
@RequiredArgsConstructor
public class WebConfig {

    @InjectProperty("web.port")
    private int port;

    private final PersonController personController;

    @Element
    public Javalin createJavalinServer() {
        return Javalin.create()
                .get(PersonController.PERSON_BY_NAME_ENDPOINT, personController.getPersonByNameEndpoint())
                .get(PersonController.PERSONS_OLDER_THAN_ENDPOINT, personController.getPersonsOlderThanEndpoint())
                .start(port);
    }
}
