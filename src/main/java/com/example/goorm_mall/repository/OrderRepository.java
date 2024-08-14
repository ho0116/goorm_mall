package com.example.goorm_mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.goorm_mall.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
