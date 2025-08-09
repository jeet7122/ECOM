package com.ecommerce.EcomApplication.controllers;


import com.ecommerce.EcomApplication.dtos.UserRequest;
import com.ecommerce.EcomApplication.dtos.UserResponse;
import com.ecommerce.EcomApplication.services.UserService;
import com.ecommerce.EcomApplication.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserRequest user){
        userService.save(user);
        return new ResponseEntity<>("User has been saved successfully", HttpStatus.CREATED) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponse>> getUserById(@PathVariable Long id) {
        Optional<UserResponse> findUser = userService.getUserById(id);
        if(findUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
        return new ResponseEntity<>(findUser, HttpStatus.OK) ;
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest updateUser){
        String success = userService.updateUser(id, updateUser);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
