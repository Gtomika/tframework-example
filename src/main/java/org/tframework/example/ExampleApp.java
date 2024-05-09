package org.tframework.example;

import org.tframework.core.TFramework;
import org.tframework.core.TFrameworkRootClass;

@TFrameworkRootClass
public class ExampleApp {

    public static void main(String[] args) {
        TFramework.start("example-app", ExampleApp.class, args);
    }
}