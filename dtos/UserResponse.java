package com.ecommerce.EcomApplication.dtos;

import com.ecommerce.EcomApplication.models.Role;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private AddressDto address;
}
