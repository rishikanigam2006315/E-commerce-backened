package com.rishika.ecommerce.auth;

import com.rishika.ecommerce.exception.AuthException;
import com.rishika.ecommerce.security.JwtService;
import com.rishika.ecommerce.user.Role;
import com.rishika.ecommerce.user.User;
import com.rishika.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
           throw new AuthException("Invalid password");
        }
        return jwtService.generateToken(user.getEmail(),
                user.getRole().name());
    }
}
