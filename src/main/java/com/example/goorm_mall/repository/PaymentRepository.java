package com.example.goorm_mall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.goorm_mall.model.Member;
import com.example.goorm_mall.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	// 특정 유저가 결제 완료한 상품 목록을 조회하는 메서드
	@Query("SELECT p FROM Payment p WHERE p.member = :member AND p.paymentStatus = '결제 완료'")
    List<Payment> findCompletedPaymentsByUsername(@Param("member") Member member);
}
