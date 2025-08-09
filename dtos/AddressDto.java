package com.ecommerce.EcomApplication.dtos;

import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
