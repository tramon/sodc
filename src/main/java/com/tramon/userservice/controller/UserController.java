package com.tramon.userservice.controller;

import com.tramon.userservice.entity.UserEntity;
import com.tramon.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // -- @RequestBody UserEntity user gets a JSON-object of the user from the request body
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        log.info("Create User {}", user);
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        log.info("Get All Users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // -- @GetMapping("/{id}") — works as GET /users/{id}.
    // -- @PathVariable Long id — gets the id of the user from the URL.
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.getUserById(id);
        log.info("Get User {}", user);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Method update user
    // -- @PutMapping("/{id}") — works PUT /users/{id}
    // -- @PathVariable Long id — gets id of the user from the URL
    // -- @RequestBody UserEntity user —  gets the updated data of the user form the request body
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(id, user);
        log.info("Update User {}", user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) :
                ResponseEntity.notFound().build();
    }

    // -- @DeleteMapping("/{id}") — works as DELETE /users/{id}
    // -- @PathVariable Long id — gets id of the user from the URL
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        log.info("Delete User with Id {}", id);
        return ResponseEntity.noContent().build();
    }

}
