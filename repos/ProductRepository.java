package com.ecommerce.EcomApplication.repos;

import com.ecommerce.EcomApplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
    @Query("SELECT p FROM products p WHERE p.active = true AND p.quantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%' ) )")
    List<Product> searchProducts(@Param("name") String name);
}
