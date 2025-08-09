package com.ecommerce.EcomApplication.services;

import com.ecommerce.EcomApplication.dtos.AddressDto;
import com.ecommerce.EcomApplication.dtos.UserRequest;
import com.ecommerce.EcomApplication.dtos.UserResponse;
import com.ecommerce.EcomApplication.models.Address;
import com.ecommerce.EcomApplication.repos.UserRepository;
import com.ecommerce.EcomApplication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void save(UserRequest request) {
        User user = new User();
        updateUserFromRequest(user, request);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest request) {
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        if (request.getAddress() != null) {
            Address address = new Address();
            address.setCity(request.getAddress().getCity());
            address.setCountry(request.getAddress().getCountry());
            address.setStreet(request.getAddress().getStreet());
            address.setState(request.getAddress().getState());
            address.setZip(request.getAddress().getZip());
            user.setAddress(address);
        }
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public String updateUser(Long id, UserRequest userToSave) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return "User not found";
        }
        updateUserFromRequest(existingUser, userToSave);
        userRepository.save(existingUser);
        return "User updated";
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId().toString());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        userResponse.setRole(user.getRole().toString());
        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZip(user.getAddress().getZip());
            addressDto.setCountry(user.getAddress().getCountry());
            userResponse.setAddress(addressDto);
        }
        return userResponse;
    }

}
