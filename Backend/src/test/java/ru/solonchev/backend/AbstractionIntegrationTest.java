package ru.solonchev.backend;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootTest(classes = {BackendApplication.class})
@ContextConfiguration(initializers = AbstractionIntegrationTest.Initializer.class)
@Testcontainers
@Slf4j
public class AbstractionIntegrationTest {

    protected static final PostgreSQLContainer<?> postgres;


    static {
        postgres = new PostgreSQLContainer<>(
                DockerImageName.parse("postgres:14"))
                .withDatabaseName("backed-db")
                .withUsername("postgres")
                .withPassword("password")
                .withExposedPorts(5432)
                .withReuse(true);

        postgres.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Docker containers stopping...");
            postgres.stop();
        }));
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            final Map<String, String> testPropertyValuesMap = new HashMap<>();
            testPropertyValuesMap.put("spring.datasource.password", "password");
            testPropertyValuesMap.put("spring.datasource.username", "postgres");

            Optional.of(postgres)
                    .map(PostgreSQLContainer::getJdbcUrl)
                    .filter(StringUtils::isNotBlank)
                    .ifPresent(url -> {
                        log.debug("spring.datasource.url={}", url);
                        testPropertyValuesMap.put("spring.datasource.url", url);
                    });
            TestPropertyValues.of(testPropertyValuesMap).applyTo(applicationContext);
        }
    }
}
