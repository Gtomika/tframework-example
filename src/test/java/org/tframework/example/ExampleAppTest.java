package org.tframework.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tframework.example.model.Person;
import org.tframework.test.commons.annotations.SetProfiles;
import org.tframework.test.junit5.TFrameworkTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TFrameworkTest // launches the application before running the tests, closes it after
@SetProfiles("test") //will activate the 'properties-test.yaml' file in the test resources folder
public class ExampleAppTest {

    private static final String BASE_URL = "http://localhost:8080";

    private static final String EXISTING_PERSON_NAME = "Test John";
    private static final String NON_EXISTING_PERSON_NAME = "Some Body";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        // The application is already running at this point, but the Java HTTP server may not be ready yet
        Thread.sleep(500);
    }

    @Test
    public void getPersonByName_shouldReturnExistingPerson() throws Exception {
        String encodedName = URLEncoder.encode(EXISTING_PERSON_NAME, StandardCharsets.UTF_8);
        String existingPersonPath = "/person/{name}".replace("{name}", encodedName);

        String rawResponse = makeHttpRequest(existingPersonPath, "GET");
        Person person = objectMapper.readValue(rawResponse, Person.class);

        assertEquals(EXISTING_PERSON_NAME, person.name());
        // Add more assertions here if needed
    }

    @Test
    public void getPersonByName_shouldReturn404ForNonExistingPerson() throws Exception {
        try {
            String encodedName = URLEncoder.encode(NON_EXISTING_PERSON_NAME, StandardCharsets.UTF_8);
            String nonExistingPersonPath = "/person/{name}".replace("{name}", encodedName);
            makeHttpRequest(nonExistingPersonPath, "GET");
            fail("Expected exception not thrown.");
        } catch (RuntimeException e) {
            assertEquals("404", e.getMessage());
        }
    }

    // Other endpoints can be tested in a similar way

    // normally, we'd use some HTTP client library for this
    private String makeHttpRequest(String path, String requestMethod) throws Exception {
        URL url = URI.create(BASE_URL + path).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(requestMethod);
        int responseCode = connection.getResponseCode();
        if(responseCode != 200) {
            throw new RuntimeException(String.valueOf(responseCode));
        }

        try(var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
    }
}
