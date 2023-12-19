package com.adil.blog.services.impl;

import com.adil.blog.entities.User;
import com.adil.blog.exceptions.ResourceNotFoundException;
import com.adil.blog.payloads.UserDto;
import com.adil.blog.repositories.UserRepo;
import com.adil.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        int userId = userDto.getId();
        User user = userRepo.findById(userId)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("User", "id", userId)
                );
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepo.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("User", "id", userId)
                );
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("User", "id", userId)
                );
        userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
