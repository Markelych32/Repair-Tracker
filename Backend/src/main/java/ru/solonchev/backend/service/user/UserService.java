package ru.solonchev.backend.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.user.request.UserSaveDto;
import ru.solonchev.backend.dto.user.response.UserTasksDto;
import ru.solonchev.backend.persistence.entity.User;
import ru.solonchev.backend.exception.user.UserErrorCode;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.mapper.TaskResponseDtoMapper;
import ru.solonchev.backend.mapper.UserSaveDtoMapper;
import ru.solonchev.backend.persistence.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSaveDtoMapper userSaveDtoMapper;
    private final TaskResponseDtoMapper taskResponseDtoMapper;

    public void saveUser(UserSaveDto request, String encodedPassword) {
        User user = userSaveDtoMapper.map(request);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND_BY_EMAIL));
    }

    public UserTasksDto getUserTasks(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND_BY_ID));
        return UserTasksDto.builder()
                .id(user.getId())
                .tasks(user.getTasks().stream().map(taskResponseDtoMapper::map).toList())
                .build();
    }

    public void deleteUser(Integer userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND_BY_ID);
        }
        userRepository.deleteById(userId);
    }
}
