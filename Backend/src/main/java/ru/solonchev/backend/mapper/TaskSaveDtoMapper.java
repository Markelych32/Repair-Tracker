package ru.solonchev.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.solonchev.backend.dto.task.request.TaskSaveDto;
import ru.solonchev.backend.persistence.entity.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskSaveDtoMapper {
    @Mapping(source = "taskId", target = "id")
    Task map(TaskSaveDto source);
}
