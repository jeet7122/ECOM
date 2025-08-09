package com.ecommerce.EcomApplication.services;

import com.ecommerce.EcomApplication.dtos.ProductRequest;
import com.ecommerce.EcomApplication.dtos.ProductResponse;
import com.ecommerce.EcomApplication.models.Product;
import com.ecommerce.EcomApplication.repos.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        updateProductFromRequest(product, request);
        productRepository.save(product);
        return mapToProductResponse(product);
    }

    public Boolean updateProduct(ProductRequest request,@PathVariable Long id){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return false;
        }
        updateProductFromRequest(product, request);
        productRepository.save(product);
        mapToProductResponse(product);
        return true;
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setCategory(product.getCategory());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setActive(product.isActive());
        return productResponse;
    }

    private void updateProductFromRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setQuantity(request.getQuantity());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        return mapToProductResponse(product);
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return false;
        }
        productRepository.delete(product);
        return true;
    }

    public List<ProductResponse> searchProducts(String name) {
        return productRepository.searchProducts(name)
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
