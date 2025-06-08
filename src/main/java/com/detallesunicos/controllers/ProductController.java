package com.detallesunicos.v1.controllers;

import com.detallesunicos.model.ReductedProduct;
import com.detallesunicos.v1.model.Product;
import com.detallesunicos.v1.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<ReductedProduct> getAllProduct() {
        List<Product> products = productRepository.findAll();
        System.out.println("Productos en BD: " + products.size());
        List<ReductedProduct> reductedProducts = new ArrayList<>();

        for (Product product : products) {
            ReductedProduct rp = new ReductedProduct();
            rp.setId(product.getId());
            rp.setTitle(product.getTitle());
            rp.setDescription(product.getDescription());
            rp.setCategory(product.getCategory());
            reductedProducts.add(rp);
            System.out.println(rp);
        }

        return reductedProducts;
    }

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("category") String category,
            @RequestPart("image") MultipartFile image
    ) {
        try {
            Product product = new Product();
            product.setTitle(title);
            product.setDescription(description);
            product.setCategory(category);
            product.setImage(image.getBytes());

            productRepository.save(product);

            return ResponseEntity.ok("Save correctly");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Error when saving product");
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(product.getImage());
    }
}