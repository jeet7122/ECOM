package com.ecommerce.EcomApplication.controllers;

import com.ecommerce.EcomApplication.dtos.CartItemRequest;
import com.ecommerce.EcomApplication.models.CartItem;
import com.ecommerce.EcomApplication.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartService cartService;


    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request){
        if(!cartService.addToCart(userId, request)) return ResponseEntity.badRequest().body("Product Out of Stock or User not found");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean result = cartService.deleteItemFromCart(userId, productId);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Product Not Found");
    }


    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-ID") String userId){
        return new ResponseEntity<>(cartService.getCartByUserId(userId), HttpStatus.OK);
    }
}
