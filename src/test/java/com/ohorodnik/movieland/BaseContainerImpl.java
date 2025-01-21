package com.ohorodnik.movieland;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.spring.api.DBRider;
import com.ohorodnik.movieland.utils.TestConfigurationToCountAllQueries;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.github.database.rider.core.api.configuration.Orthography.LOWERCASE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DBRider
@DBUnit(caseInsensitiveStrategy = LOWERCASE,
        cacheConnection = false,
        schema = "movieland")
@Import(TestConfigurationToCountAllQueries.class)
public class BaseContainerImpl {

    private static final PostgreSQLContainer<?> container;

    static {
        container = new PostgreSQLContainer<>("postgres:latest")
                .withUsername("test")
                .withPassword("password")
                .withDatabaseName("movieland")
                .withInitScript("db/scripts/create_schema_init.sql");

        container.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    protected String getResponseAsString(String jsonPath) {
        URL resource = getClass().getClassLoader().getResource(jsonPath);
        try {
            return FileUtils.readFileToString(new File(resource.toURI()), StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Unable to find file: " + jsonPath, e);
        }
    }
}
