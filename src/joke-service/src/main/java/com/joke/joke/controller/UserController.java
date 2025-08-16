package com.joke.joke.controller;

import com.joke.common.dto.ApiResponse;
import com.joke.common.dto.CreateUserRequest;
import com.joke.common.model.User;
import com.joke.joke.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/api/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public HttpResponse<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return HttpResponse.ok(ApiResponse.success(users));
    }

    @Get("/{id}")
    public HttpResponse<ApiResponse<User>> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(user -> HttpResponse.ok(ApiResponse.success(user)))
                .orElse(HttpResponse.notFound());
    }

    @Get("/username/{username}")
    public HttpResponse<ApiResponse<User>> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(user -> HttpResponse.ok(ApiResponse.success(user)))
                .orElse(HttpResponse.notFound());
    }

    @Post
    public HttpResponse<ApiResponse<User>> createUser(@Body @Valid CreateUserRequest request) {
        User user = userService.createUser(request);
        return HttpResponse.created(ApiResponse.success("User created successfully", user));
    }

    @Put("/{id}")
    public HttpResponse<ApiResponse<User>> updateUser(
            @PathVariable String id,
            @Body @Valid CreateUserRequest request) {

        User user = userService.updateUser(id, request);
        return HttpResponse.ok(ApiResponse.success("User updated successfully", user));
    }

    @Delete("/{id}")
    public HttpResponse<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return HttpResponse.ok(ApiResponse.success("User deleted successfully", null));
    }
}
