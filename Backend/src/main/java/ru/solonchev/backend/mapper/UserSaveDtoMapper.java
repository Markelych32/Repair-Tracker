package ru.solonchev.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.solonchev.backend.dto.user.request.UserSaveDto;
import ru.solonchev.backend.persistence.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSaveDtoMapper {
    User map(UserSaveDto source);
}
