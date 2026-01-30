package com.revati.ecom_project.repo;

import com.revati.ecom_project.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepo extends JpaRepository<Products, Integer> {



}
