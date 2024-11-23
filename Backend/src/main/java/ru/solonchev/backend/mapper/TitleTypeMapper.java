package ru.solonchev.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.solonchev.backend.persistence.entity.TitleTypeEntity;
import ru.solonchev.backend.dto.task.response.TitleTypeDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TitleTypeMapper {
    TitleTypeDto map(TitleTypeEntity entity);

    TitleTypeEntity map(TitleTypeDto dto);

    List<TitleTypeDto> listMap(List<TitleTypeEntity> list);
}
