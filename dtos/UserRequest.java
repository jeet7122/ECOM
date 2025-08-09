package com.ecommerce.EcomApplication.dtos;


import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AddressDto address;
}
