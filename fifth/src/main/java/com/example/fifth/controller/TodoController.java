package com.example.fifth.controller;

import com.example.fifth.config.security.LoginMember;
import com.example.fifth.model.todo.TodoSaveRequest;
import com.example.fifth.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity saveTodos(@LoginMember User user, @RequestBody TodoSaveRequest request) {

        String email = user.getUsername();

        todoService.saveTodo(email, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
