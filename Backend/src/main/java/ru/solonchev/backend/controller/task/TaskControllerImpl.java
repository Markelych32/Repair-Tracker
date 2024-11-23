package ru.solonchev.backend.controller.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.solonchev.backend.dto.task.request.TaskSaveDto;
import ru.solonchev.backend.service.task.TaskService;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public ResponseEntity<?> addTask(@RequestBody TaskSaveDto request) {
        taskService.addTaskToUser(request);
        return ResponseEntity.ok().body("Task successfully added to user");
    }

    @Override
    public ResponseEntity<?> updateTask(@RequestBody TaskSaveDto request) {
        taskService.updateTaskById(request.taskId(), request);
        return ResponseEntity.ok().body("Task updated successfully");
    }

    @Override
    public ResponseEntity<?> deleteTask(@RequestHeader(name = "taskId") UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().body("Task deleted successfully");
    }

    @Override
    public ResponseEntity<?> getTypesTitles() {
        return ResponseEntity.ok(taskService.findAllTypes());
    }
}
