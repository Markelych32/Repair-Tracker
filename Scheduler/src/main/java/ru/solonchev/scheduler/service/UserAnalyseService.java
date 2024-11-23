package ru.solonchev.scheduler.service;

import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.scheduler.config.dto.AnalyseResponseDto;
import ru.solonchev.scheduler.config.dto.DoneTasksDto;
import ru.solonchev.scheduler.config.dto.NotDoneTasksDto;
import ru.solonchev.scheduler.entity.User;
import ru.solonchev.scheduler.repository.TaskRepository;


@Service
@RequiredArgsConstructor
public class UserAnalyseService {

    private final TaskRepository taskRepository;

    public AnalyseResponseDto analyse(User user) {
        final List<String> finishedTitles = new LinkedList<>();
        final List<String> activeTitles = new LinkedList<>();
        user.getTasks().forEach(task -> {
            if (task.getIsDone()) {
                finishedTitles.add(task.getTitle());
                taskRepository.deleteById(task.getId());
            } else {
                activeTitles.add(task.getTitle());
            }
        });
        return AnalyseResponseDto.builder()
                .email(user.getEmail())
                .finished(new DoneTasksDto(finishedTitles.size(), finishedTitles))
                .active(new NotDoneTasksDto(activeTitles.size(), activeTitles))
                .build();
    }
}
