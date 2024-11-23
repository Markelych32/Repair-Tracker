package ru.solonchev.backend.service.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.task.request.TaskSaveDto;
import ru.solonchev.backend.dto.task.response.ListTitleTypeDto;
import ru.solonchev.backend.mapper.TitleTypeMapper;
import ru.solonchev.backend.persistence.entity.Task;
import ru.solonchev.backend.persistence.entity.User;
import ru.solonchev.backend.exception.task.TaskErrorCode;
import ru.solonchev.backend.exception.task.TaskNotFoundException;
import ru.solonchev.backend.exception.user.UserErrorCode;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.mapper.TaskSaveDtoMapper;
import ru.solonchev.backend.persistence.repository.TaskRepository;
import ru.solonchev.backend.persistence.repository.TypeTitleRepository;
import ru.solonchev.backend.persistence.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TypeTitleRepository typeTitleRepository;
    private final UserRepository userRepository;
    private final TaskSaveDtoMapper taskSaveDtoMapper;
    private final TitleTypeMapper titleTypeMapper;

    public void addTaskToUser(TaskSaveDto request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND_BY_ID));
        Task task = taskSaveDtoMapper.map(request);
        task.setUser(user);
        user.getTasks().add(taskSaveDtoMapper.map(request));
        taskRepository.save(task);
        userRepository.save(user);
    }

    public void updateTaskById(UUID taskId, TaskSaveDto request) {
        Task task = findTask(taskId);
        this.updateTask(task, request);
    }

    private void updateTask(Task task, TaskSaveDto request) {
        task.setTitle(request.title());
        task.setPayload(request.payload());
        task.setIsDone(request.isDone());
        taskRepository.save(task);
    }

    public void deleteTask(UUID taskId) {
        findTask(taskId);
        taskRepository.deleteById(taskId);
    }

    public ListTitleTypeDto findAllTypes() {
        var types = typeTitleRepository.findAll();
        return new ListTitleTypeDto(
                String.valueOf(types.size()),
                titleTypeMapper.listMap(types)
        );
    }

    private Task findTask(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(TaskErrorCode.TASK_NOT_FOUND_BY_ID));
    }


}