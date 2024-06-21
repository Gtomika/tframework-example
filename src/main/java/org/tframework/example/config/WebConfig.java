package org.tframework.example.config;

import io.javalin.Javalin;
import io.javalin.router.Endpoint;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectProperty;
import org.tframework.core.events.CoreEvents;
import org.tframework.core.events.annotations.Subscribe;

/**
 * The web config collects all {@link Endpoint} elements and stats the Javalin server with them.
 * Port is injected from the properties file. Note the {@link Subscribe} annotation on the method:
 * it will run after we are sure that all elements are initialized.
 */
@Element
public class WebConfig {

    @InjectProperty("web.port")
    private int port;

    @Subscribe(CoreEvents.APPLICATION_INITIALIZED)
    public void createJavalinServer(Application application) {
        var server = Javalin.create();
        application.getElementsContainer().getElementContextsWithType(Endpoint.class)
                .stream()
                .map(context -> (Endpoint) context.requestInstance())
                .forEach(server::addEndpoint);
        server.start(port);
    }
}
