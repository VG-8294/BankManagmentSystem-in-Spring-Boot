package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.LoginReqDto;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginReqDto loginReqDto){
        Boolean loggedInUser = userService.login(loginReqDto);
        if(!loggedInUser){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email or password");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logged in successfully!");
    }

    @PostMapping("/v1/login")
    public ResponseEntity<String> loginV1User(@RequestBody LoginReqDto loginReqDto){
        Boolean loggedInUser = userService.loginV1(loginReqDto);
        if(!loggedInUser){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email or password");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logged in successfully!");
    }



    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Boolean isDeleted = userService.deleteUserById(id);
        if(!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record deleted");
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User updatedUser = userService.updateUserById(id, user);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedUser);
    }



}
