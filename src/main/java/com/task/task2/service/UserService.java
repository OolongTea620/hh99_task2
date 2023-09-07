package com.task.task2.service;

import com.task.task2.dto.auth.AuthRequestDto;
import com.task.task2.dto.auth.AuthResponseDto;
import com.task.task2.entity.User;
import com.task.task2.entity.UserRoleEnum;
import com.task.task2.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthResponseDto signup(AuthRequestDto.SignUp requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return AuthResponseDto.builder()
                    .msg("회원가입 실패")
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username, password, role);
        userRepository.save(user);

        return AuthResponseDto.builder()
            .msg("회원가입 성공")
            .statusCode(HttpStatus.CREATED.value())
            .build();
        
    }
}
