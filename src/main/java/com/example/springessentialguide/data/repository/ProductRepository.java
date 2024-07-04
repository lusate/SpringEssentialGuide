package com.example.springessentialguide.data.repository;

import com.example.springessentialguide.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
