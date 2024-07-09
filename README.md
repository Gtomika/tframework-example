# TFramework example

This is a demo app that uses the [TFramework](https://github.com/Gtomika/tframework-core), 
a Java application development framework, created by me. It reads some data from a file
and makes it available with an API.

## How to run

> :warning: Please make sure to use Java 21 or higher.

Execute the following command to run the app:

```bash
./gradlew run
```

It will initialize the TFramework and also the [Javalin](https://javalin.io/) server, and wait for requests.

## Notes

- The TFramework has no web module (yet?), so [Javalin](https://javalin.io/) was used as a replacement.
- By default, log4j2 is used and the log level is info. If you want to see more logs, you can find a *DEBUG*
configuration in the [log4j2.xml](./devtools/log4j2.xml) file, which you can copy into `src/main/resources`.
- For the endpoints, see [PersonController](./src/main/java/org/tframework/example/controller/PersonController.java)
  and [WebConfig](./src/main/java/org/tframework/example/config/WebConfig.java).
- Be sure to download the Javadoc and sources of the TFramework to see the documentation in your IDE. 

## Tests

The TFramework comes with a default test module, [tframework-test](https://github.com/Gtomika/tframework-test).
It is used in this project to boot the application and send requests to it during the tests.
The test class can be found [here](./src/test/java/org/tframework/example/ExampleAppTest.java).
Standard unit tests could be written as well, but they are not the focus of this example.

To run the tests, execute the following command:

```bash
./gradlew test
```

