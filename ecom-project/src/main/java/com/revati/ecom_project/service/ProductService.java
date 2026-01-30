package com.revati.ecom_project.service;


import com.revati.ecom_project.model.Products;
import com.revati.ecom_project.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Products> getALlProducts() {
        return repo.findAll();
    }


    public Products getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Products addProduct(Products products, MultipartFile imageFile) throws IOException {

        if (imageFile != null && !imageFile.isEmpty()) {
            products.setImageName(imageFile.getOriginalFilename());
            products.setImageType(imageFile.getContentType());
            products.setImageDate(imageFile.getBytes());
        }

        return repo.save(products);
    }



    public Products updateProduct(int id, Products newProduct, MultipartFile imageFile) throws IOException {


        Products existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        existing.setName(newProduct.getName());
        existing.setPrice(newProduct.getPrice());
        existing.setDescription(newProduct.getDescription());
        existing.setCategory(newProduct.getCategory());


        if (imageFile != null && !imageFile.isEmpty()) {
            existing.setImageDate(imageFile.getBytes());
            existing.setImageName(imageFile.getOriginalFilename());
            existing.setImageType(imageFile.getContentType());
        }

        return repo.save(existing);
    }


    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

}
