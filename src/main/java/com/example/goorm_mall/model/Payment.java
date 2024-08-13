package com.example.goorm_mall.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private double totalPrice;
	
	@Column(nullable = false)
	private String paymentStatus;
	
	// 결제 완료시 추가되는 데이터
	private int postalCode;
	private String recipientName;
	private String address;
	private String addressDetail;
	private String phone;
	private String additionalInfo;
	
	@Column(nullable = false)
	private LocalDateTime paymentDate;
	
	@PrePersist
	protected void onCreate() {
		paymentDate = LocalDateTime.now();
	}
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
}
