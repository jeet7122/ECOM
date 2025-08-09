package com.ecommerce.EcomApplication.repos;

import com.ecommerce.EcomApplication.models.CartItem;
import com.ecommerce.EcomApplication.models.Product;
import com.ecommerce.EcomApplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {


    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);


    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
