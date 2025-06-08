package com.detallesunicos.v1.controllers;

import com.detallesunicos.model.MediaFileIdDTO;
import com.detallesunicos.model.ReductedProduct;
import com.detallesunicos.v1.model.MediaFile;
import com.detallesunicos.v1.model.Product;
import com.detallesunicos.v1.repositories.ProductRepository;
import com.detallesunicos.v1.repositories.MediaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.detallesunicos.v1.model.MediaFile;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MediaFileRepository mediaFileRepository;

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
            @RequestPart("mediaFiles") List<MultipartFile> mediaFiles
    ) {
        try {
            Product product = new Product();
            product.setTitle(title);
            product.setDescription(description);
            product.setCategory(category);

            List<MediaFile> fileList = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                MediaFile media = new MediaFile();
                media.setFileName(file.getOriginalFilename());
                media.setContentType(file.getContentType());
                media.setData(file.getBytes());
                media.setProduct(product);
                media.setProductId(product.getId());
                fileList.add(media);
            }

            product.setMediaFiles(fileList);
            productRepository.save(product);

            return ResponseEntity.ok("Producto guardado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar el producto");
        }
    }

    @GetMapping("/product/{productId}/media/{mediaId}")
    public ResponseEntity<byte[]> getMediaFile(
            @PathVariable Long productId,
            @PathVariable Long mediaId
    ) {
        Optional<MediaFile> media = mediaFileRepository.findById(mediaId);
        if (media.isEmpty() || !media.get().getProduct().getId().equals(productId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(media.get().getContentType()))
                .body(media.get().getData());
    }

    @GetMapping("/product/{productId}/files")
    public ResponseEntity<List<MediaFileIdDTO>> getMediaFileIds(@PathVariable Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<MediaFileIdDTO> ids = optionalProduct.get()
                .getMediaFiles()
                .stream()
                .map(media -> new MediaFileIdDTO(media.getId()))
                .toList();

        return ResponseEntity.ok(ids);
    }
}