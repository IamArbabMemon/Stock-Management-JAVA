package com.example.Stock_Management.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users user){
        System.out.println(user);
        return ResponseEntity.ok(usersService.addUser(user));
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(usersService.getUserById(id));
    }



}
