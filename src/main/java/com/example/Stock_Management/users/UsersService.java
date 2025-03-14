package com.example.Stock_Management.users;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Users addUser(Users user){
        if(user==null)
            throw new IllegalArgumentException("User object cannot be null");

        return userRepository.save(user);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found by id " + id));
    }


}
