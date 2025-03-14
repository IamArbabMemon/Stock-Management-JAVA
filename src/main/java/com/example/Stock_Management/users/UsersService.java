package com.example.Stock_Management.users;

import com.example.Stock_Management.users.dtos.UserDto;
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

    public Users addUser(UserDto user){
        if(user==null)
            throw new IllegalArgumentException("User object cannot be null");
        var userToBeInserted = new Users();
        userToBeInserted.setName(user.getName());
        userToBeInserted.setEmail(user.getEmail());
        userToBeInserted.setPassword(user.getPassword());
        userToBeInserted.setDob(user.getDob());
        userToBeInserted.setRole(user.getRole());

        return userRepository.save(userToBeInserted);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found by id " + id));
    }

    public Users deleteUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id " + id));
        userRepository.delete(user);
        return user; // Return the deleted user
    }


}
