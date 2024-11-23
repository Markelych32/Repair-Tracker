package ru.solonchev.backend.controller.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.solonchev.backend.dto.user.request.UserLoginDto;
import ru.solonchev.backend.dto.user.request.UserSaveDto;
import ru.solonchev.backend.dto.user.response.UserLoginResponseDto;
import ru.solonchev.backend.jwt.JwtCore;
import ru.solonchev.backend.service.user.UserDetailsServiceImpl;
import ru.solonchev.backend.service.user.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class SecurityControllerImpl implements SecurityController {

    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;

    @Override
    public ResponseEntity<?> login(@RequestBody UserLoginDto request) {
        Authentication authentication = null;
        final String email = request.email();
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(
                new UserLoginResponseDto(
                        userService.findUserByEmail(email).getId(),
                        email,
                        jwtCore.generateToken(authentication)
                )
        );
    }

    @Override
    public ResponseEntity<?> register(@RequestBody UserSaveDto request) {
        userDetailsService.checkEmailExists(request.email());
        userService.saveUser(request, passwordEncoder.encode(request.password()));
        return ResponseEntity.ok("User has been registered.");
    }
}
