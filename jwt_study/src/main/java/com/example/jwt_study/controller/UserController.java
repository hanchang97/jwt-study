package com.example.jwt_study.controller;

import com.example.jwt_study.dto.UserDto;
import com.example.jwt_study.entity.User;
import com.example.jwt_study.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }

    @PutMapping("/user/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> updateUserInfo(@Valid @RequestBody UserDto userDto){
        User user = userService.getMyUserWithAuthorities().get();
        // 이 user를 어떻게 해볼까??
        return ResponseEntity.ok(userService.updateInfo(user, userDto.getUsername(), userDto.getPassword(), userDto.getNickname()));

    }
}