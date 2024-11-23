package ru.solonchev.backend.service.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.solonchev.backend.AbstractionIntegrationTest;
import ru.solonchev.backend.dto.task.request.TaskSaveDto;
import ru.solonchev.backend.persistence.entity.Task;
import ru.solonchev.backend.persistence.entity.User;
import ru.solonchev.backend.exception.task.TaskNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.persistence.repository.TaskRepository;
import ru.solonchev.backend.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
public class TaskServiceTestIT extends AbstractionIntegrationTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Should add task to user correctly")
    void shouldCorrectAddTaskToUser() {

        final UUID taskId = UUID.randomUUID();
        final TaskSaveDto request = new TaskSaveDto(
                taskId,
                1,
                "title_to_save",
                "payload_to_save",
                false
        );

        taskService.addTaskToUser(request);

        Optional<User> user = userRepository.findById(request.userId());
        Optional<Task> task = taskRepository.findById(taskId);
        assertAll(() -> {
            assertTrue(user.isPresent(), "User should no be empty");
            assertNotNull(user.get().getTasks());
            assertTrue(task.isPresent());
            assertFalse(task.get().getIsDone());
            assertEquals("title_to_save", task.get().getTitle());
            assertEquals("payload_to_save", task.get().getPayload());
            assertFalse(task.get().getIsDone());
            List<Task> tasks = user.get().getTasks();
            assertEquals(2, tasks.size());
            assertFalse(tasks.stream()
                    .filter(t -> t.getTitle().equals(request.title()))
                    .filter(t -> t.getPayload().equals(request.payload()))
                    .toList().isEmpty());
        });
    }

    @Test
    @DisplayName("Should throw UserNotFoundException")
    void shouldThrowExceptionIfUserIsAbsentWhenAddTask() {
        final TaskSaveDto request = new TaskSaveDto(
                UUID.randomUUID(),
                101,
                "title_to_save",
                "payload_to_save",
                false
        );

        assertThrows(UserNotFoundException.class,
                () -> taskService.addTaskToUser(request));
    }

    @Test
    @DisplayName("Should update existing task")
    void shouldUpdateExistingTask() {
        final UUID taskId = UUID.fromString("656711a4-8506-4e38-b359-fd0647a8134e");
        final TaskSaveDto request = new TaskSaveDto(
                taskId,
                1,
                "title_to_save",
                "payload_to_save",
                true
        );

        taskService.updateTaskById(taskId, request);

        Optional<Task> task = taskRepository.findById(taskId);
        assertAll(() -> {
            assertTrue(task.isPresent());
            assertEquals(request.title(), task.get().getTitle());
            assertEquals(request.payload(), task.get().getPayload());
            assertEquals(request.isDone(), task.get().getIsDone());
        });
    }

    @Test
    @DisplayName("Should throw TaskNotFoundException")
    void shouldThrowExceptionIfTaskIsAbsentWhenTaskUpdate() {
        final UUID taskId = UUID.fromString("c116797c-7ce4-46c8-97c8-0340ddcdeeb7");
        final TaskSaveDto request = new TaskSaveDto(
                taskId,
                1,
                "title_to_save",
                "payload_to_save",
                true
        );

        assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTaskById(taskId, request));
    }

    @Test
    @DisplayName("Should correct delete task by id")
    void shouldDeleteTaskById() {
        final UUID taskId = UUID.fromString("656711a4-8506-4e38-b359-fd0647a8134e");
        taskService.deleteTask(taskId);

        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findById(1);
        assertAll(() -> {
            assertTrue(task.isEmpty());
            assertTrue(user.isPresent());
            assertTrue(user.get().getTasks().isEmpty());
        });
    }
}
