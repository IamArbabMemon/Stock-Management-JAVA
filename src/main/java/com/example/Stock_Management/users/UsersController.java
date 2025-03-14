package com.example.Stock_Management.users;

import com.example.Stock_Management.users.dtos.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto user , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            HashMap<String ,String> errors = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors())
                errors.put(fieldError.getField(),fieldError.getDefaultMessage());
            return ResponseEntity.badRequest().body(errors);
        }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteUserById(@PathVariable Long id){
        return ResponseEntity.ok(usersService.deleteUserById(id));
    }



}
