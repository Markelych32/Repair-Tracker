package ru.solonchev.backend.service.user;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.solonchev.backend.AbstractionIntegrationTest;
import ru.solonchev.backend.dto.user.request.UserSaveDto;
import ru.solonchev.backend.persistence.entity.User;
import ru.solonchev.backend.persistence.repository.TaskRepository;
import ru.solonchev.backend.persistence.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration Test")
@Sql(
        scripts = {
                "classpath:sql/clean_all.sql",
                "classpath:sql/user/init_values_user.sql",
                "classpath:sql/task/init_values_tasks.sql"
        },
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        scripts = "classpath:sql/clean_all.sql",
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.DEFAULT),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class UserServiceTestIT extends AbstractionIntegrationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService serviceUnderTest;

    @Test
    @DisplayName("Should correct save new user")
    void shouldCorrectSaveNewUser() {
        final UserSaveDto request = new UserSaveDto(
                "Mike",
                "Tomson",
                "mail@test.com",
                "test_password"
        );
        serviceUnderTest.saveUser(request, "encoded_password");
        Optional<User> user = userRepository.findByEmail(request.email());
        assertAll(() -> {
            assertTrue(user.isPresent());
            assertEquals("Mike", user.get().getFirstName());
            assertEquals("Tomson", user.get().getLastName());
            assertEquals("mail@test.com", user.get().getEmail());
        });
    }
}
