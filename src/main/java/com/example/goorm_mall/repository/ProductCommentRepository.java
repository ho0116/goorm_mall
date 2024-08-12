package com.example.goorm_mall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.goorm_mall.model.ProductComment;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
	List<ProductComment> getCommentsByProductId(Long id); 
}
