package org.tframework.example.config;

import io.javalin.Javalin;
import io.javalin.config.EventConfig;
import io.javalin.router.Endpoint;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.core.elements.annotations.InjectProperty;
import org.tframework.core.elements.context.filter.annotation.RequiredElement;
import org.tframework.core.events.CoreEvents;
import org.tframework.core.events.EventManager;
import org.tframework.core.events.annotations.Subscribe;

import java.util.List;

/**
 * The web config collects all {@link Endpoint} elements and stats the Javalin server with them.
 * Port is injected from the properties file. Note the {@link Subscribe} annotation on the method:
 * it will run after we are sure that all elements are initialized.
 */
@Element
@RequiredElement(type = Endpoint.class)
public class WebConfig {

    public static final String WEB_SERVER_INITIALIZED_EVENT = "web.server.initialized";

    @InjectProperty("web.port")
    private int port;

    @InjectElement
    private EventManager eventManager;

    //will inject a list with all Endpoint elements
    @InjectElement
    private List<Endpoint> endpoints;

    private Javalin server;

    @Subscribe(CoreEvents.APPLICATION_INITIALIZED)
    public void createJavalinServer(Application application) {
        server = Javalin.create()
                .events(this::fireWebServerInitializedEvent);
        endpoints.forEach(server::addEndpoint);
        server.start(port);
    }

    @Subscribe(CoreEvents.APPLICATION_SHUTTING_DOWN)
    public void stopJavalinServer(Application application) {
        if(server != null) {
            server.stop();
        }
    }

    /**
     * Note that {@link EventConfig} is from Javalin, and not from TFramework. However,
     * {@link EventManager} is from the framework, and is injected into this element.
     * We are using it to publish an {@value #WEB_SERVER_INITIALIZED_EVENT} event so that
     * the tests can wait for the server to start.
     */
    private void fireWebServerInitializedEvent(EventConfig eventConfig) {
        eventConfig.serverStarted(() -> eventManager.publish(WEB_SERVER_INITIALIZED_EVENT, ""));
    }
}
