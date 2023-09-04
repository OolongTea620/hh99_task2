package com.task.task2.service;

import com.task.task2.dto.auth.AuthRequestDto;
import com.task.task2.dto.auth.AuthRequestDto.Login;
import com.task.task2.dto.auth.AuthResponseDto;
import com.task.task2.entity.User;
import com.task.task2.entity.UserRoleEnum;
import com.task.task2.jwt.JwtUtil;
import com.task.task2.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthResponseDto signup(AuthRequestDto.SignUp requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        
        // 중복회원 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(username, password, role);
        userRepository.save(user);

        return AuthResponseDto.builder()
            .msg("회원가입 성공")
            .statusCode(HttpStatus.CREATED.value())
            .build();
    }

    public AuthResponseDto login(Login requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return AuthResponseDto.builder()
            .msg("로그인 성공")
            .statusCode(HttpStatus.OK.value())
            .build();
    }

}
