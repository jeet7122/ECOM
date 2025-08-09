package com.ecommerce.EcomApplication.controllers;

import com.ecommerce.EcomApplication.dtos.ProductRequest;
import com.ecommerce.EcomApplication.dtos.ProductResponse;
import com.ecommerce.EcomApplication.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;


    /**
     *
     * @param request
     * @return ProductResponse
     * path for adding products
     */
    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request){
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequest request,@PathVariable Long id){
        boolean isUpdate = productService.updateProduct(request,id);
        if(isUpdate){
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id){
        ProductResponse response = productService.getProductById(id);
        if(response == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        boolean isDeleted = productService.deleteProduct(id);
        if(isDeleted){
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name){
        return new ResponseEntity<>(productService.searchProducts(name), HttpStatus.OK);
    }
}
