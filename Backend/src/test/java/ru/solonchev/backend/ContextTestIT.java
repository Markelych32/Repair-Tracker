package ru.solonchev.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Integration Test")
public class ContextTestIT extends AbstractionIntegrationTest {

    @Test
    @DisplayName("PostgreSQL Container should be running")
    void postgreSqlContainerIsRunning() {
        Assertions.assertTrue(postgres.isRunning(), "PostgreSQL container is not running...");
    }
}
