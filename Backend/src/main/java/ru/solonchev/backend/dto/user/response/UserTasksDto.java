package ru.solonchev.backend.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.solonchev.backend.dto.task.response.TaskResponseDto;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTasksDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("tasks")
    private List<TaskResponseDto> tasks;
}
