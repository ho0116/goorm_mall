package com.example.goorm_mall.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.goorm_mall.model.Member;
import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.model.ProductLike;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
	Optional<ProductLike> findByProductAndMember(Product product, Member member);

	Long countByProductId(Long productId);
	
	@Query("SELECT sl.product FROM ProductLike sl WHERE sl.member = :member")
    List<Product> findProductsByMember(@Param("member") Member member);
}
