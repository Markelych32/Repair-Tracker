package ru.solonchev.backend.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.persistence.entity.User;
import ru.solonchev.backend.exception.user.UserAlreadyExistsException;
import ru.solonchev.backend.exception.user.UserErrorCode;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.jwt.UserDetailsImpl;
import ru.solonchev.backend.persistence.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND_BY_EMAIL));
        return UserDetailsImpl.build(user);
    }

    public void checkEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(UserErrorCode.USER_WITH_SUCH_EMAIL_ALREADY_EXISTS);
        }
    }
}
