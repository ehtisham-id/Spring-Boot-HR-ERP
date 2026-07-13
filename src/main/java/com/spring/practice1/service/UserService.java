package com.spring.practice1.service;

import com.spring.practice1.dto.UserRequestDTO;
import com.spring.practice1.dto.UserResponseDTO;
import com.spring.practice1.entity.User;
import com.spring.practice1.mapper.UserMapper;
import com.spring.practice1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    public List<UserResponseDTO> getUsers(){
        return userMapper.getAll(userRepository.findAll());
    }

    public UserResponseDTO getUserById(Long id){
        return userMapper.toResponse(userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not Found !")));
    }

    public  void addUser(UserRequestDTO user){
        user.setPassword(encoder.encode(user.getPassword()));
        User temp = userMapper.toEntity(user);

        userRepository.save(temp);
    }

    public void updateUser(Long id, UserRequestDTO user){
        User temp = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        userMapper.updateEntity(user, temp);
        temp.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(temp);
    }

    public void deleteUser(Long id){
        userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        userRepository.deleteById(id);
    }
}
