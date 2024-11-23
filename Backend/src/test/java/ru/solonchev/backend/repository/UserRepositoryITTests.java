package ru.solonchev.backend.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.solonchev.backend.AbstractionIntegrationTest;
import ru.solonchev.backend.persistence.entity.User;
import ru.solonchev.backend.persistence.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration Test")
@Sql(
        scripts = {"classpath:sql/clean_all.sql", "classpath:sql/user/init_values_user.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        scripts = "classpath:sql/clean_all.sql",
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.DEFAULT),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class UserRepositoryITTests extends AbstractionIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should return not empty Optional with User by email")
    void shouldReturnCorrectUserByEmail() {
        final String emailToFind = "solonchev@mail.com";

        Optional<User> user = userRepository.findByEmail(emailToFind);

        assertAll(() -> {
            assertTrue(user.isPresent(), "Optional should not be empty");
            assertEquals("Mark", user.get().getFirstName());
            assertEquals("Solonchev", user.get().getLastName());
        });
    }

    @Test
    @DisplayName("Should return empty Optional when User is absent")
    void shouldReturnEmptyOptionalUser() {
        final String emailToFind = "no_email@vk.com";
        Optional<User> user = userRepository.findByEmail(emailToFind);
        assertTrue(user.isEmpty());
    }
}
