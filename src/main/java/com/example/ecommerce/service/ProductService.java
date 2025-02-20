package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Obtener todos los productos
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    // Obtener un producto por id
    public Product getProduct(long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));
    }

    // Guardar un producto
    @Transactional
    public Product saveProduct(Product product){
        if(product.getPrice() <  0 || product.getStock() < 0){
            throw new IllegalArgumentException("Price and Stock must be greater than 0");
        }
        return productRepository.save(product);
    }

    // Actualizar un producto
    @Transactional
    public Product updateProduct(long id, Product newProduct){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));

        existingProduct.setName(newProduct.getName());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setStock(newProduct.getStock());
        return productRepository.save(existingProduct);

    }

    // Eliminar un producto
    @Transactional
    public void deleteProduct(long id){
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    // Buscar productos por nombre o descripción
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }
}
