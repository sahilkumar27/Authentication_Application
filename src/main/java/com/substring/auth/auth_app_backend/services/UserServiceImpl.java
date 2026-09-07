package com.substring.auth.auth_app_backend.services;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.entities.Provider;
import com.substring.auth.auth_app_backend.entities.User;
import com.substring.auth.auth_app_backend.exceptions.ResourceNotFoundException;
import com.substring.auth.auth_app_backend.helpers.userHelper;
import com.substring.auth.auth_app_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        //if you have extra checks __put here....
        User user = modelMapper.map(userDto, User.class);
        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);

        //role assign here to user___for authorization
        //TODO:
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given email id: " + email));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uuid = userHelper.parseUUID(userId); // Validate the userId format
        User existingUser = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));

        //we are bit not going to change the email id for this project
        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            existingUser.setName(userDto.getName());
        }

        if (userDto.getImage() != null && !userDto.getImage().isBlank()) {
            existingUser.setImage(userDto.getImage());
        }

        if (userDto.getProvider() != null) {
            existingUser.setProvider(userDto.getProvider());
        }

        //TODO: change password update logic....
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            existingUser.setPassword(userDto.getPassword());
        }

        existingUser.setEnable(userDto.isEnable());
        existingUser.setUpdatedAt(Instant.now());
        userRepository.save(existingUser);
        return modelMapper.map(existingUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
       UUID uuid =  userHelper.parseUUID(userId); // Validate the userId format
       User user =  userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userHelper.parseUUID(userId)).orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}