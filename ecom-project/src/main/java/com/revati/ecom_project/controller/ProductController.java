package com.revati.ecom_project.controller;


import com.revati.ecom_project.model.Products;
import com.revati.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
     private ProductService service;



    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts(){
            return  new ResponseEntity<>(service.getALlProducts(), HttpStatus.OK);
    }



    @GetMapping("/products/{id}")
    public  ResponseEntity<Products> getProduct(@PathVariable int id){

        Products products = service.getProductById(id);

        if (products != null)
            return new ResponseEntity<>(products,HttpStatus.OK);
        else
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @PostMapping(
        value = "/products",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(

            @RequestPart("products") Products products,
            @RequestPart(value = "image", required = false) MultipartFile imageFile){

    try{

            Products products1 =service.addProduct(products, imageFile);
            return new ResponseEntity<>(products1,HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

        Products products = service.getProductById(productId);
        byte[] imageFile = products.getImageDate();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(products.getImageType()))
                .body(imageFile);

    }


    @PutMapping(
            value = "/products/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> updateProduct(
            @PathVariable int id,
            @RequestPart("products") Products products,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            Products updatedProduct = service.updateProduct(id, products, imageFile);
            return ResponseEntity.ok(updatedProduct);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update product");

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }



    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Products  products = service.getProductById(id);
        if (products != null){
            service.deleteProduct(id);
            return  new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }
    }



}
