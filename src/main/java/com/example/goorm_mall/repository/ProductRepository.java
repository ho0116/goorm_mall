package com.example.goorm_mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.goorm_mall.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
