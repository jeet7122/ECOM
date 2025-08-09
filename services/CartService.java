package com.ecommerce.EcomApplication.services;

import com.ecommerce.EcomApplication.dtos.CartItemRequest;
import com.ecommerce.EcomApplication.models.CartItem;
import com.ecommerce.EcomApplication.models.Product;
import com.ecommerce.EcomApplication.models.User;
import com.ecommerce.EcomApplication.repos.CartItemRepository;
import com.ecommerce.EcomApplication.repos.ProductRepository;
import com.ecommerce.EcomApplication.repos.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productExists = productRepository.findById(request.getProductId());
        if (productExists.isEmpty()) {
            return false;
        }
        if(productExists.get().getQuantity() < request.getQuantity()){
            return false;
        }
        Product product = productExists.get();
        Optional<User> userExists = userRepository.findById(Long.valueOf(userId));
        if (userExists.isEmpty()) {
            return false;
        }
        User user = userExists.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }
        else {
            CartItem newCartItem = new CartItem();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(newCartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productExists = productRepository.findById(productId);

        Optional<User> userExists = userRepository.findById(Long.valueOf(userId));
        if(productExists.isPresent() && userExists.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(userExists.get(), productExists.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCartByUserId(String userId) {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isPresent()) {
            User userExists = user.get();
            return cartItemRepository.findByUser(userExists);
        }
        return null;
    }
}
