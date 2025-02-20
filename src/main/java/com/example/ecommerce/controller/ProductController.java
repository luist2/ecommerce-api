package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Obtener todos los productos
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    // Obtener un producto por su id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // Buscar productos por nombre o descripción
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query){
        return ResponseEntity.ok(productService.searchProducts(query));
    }

}
