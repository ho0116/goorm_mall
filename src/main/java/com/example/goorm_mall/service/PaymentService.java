package com.example.goorm_mall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.goorm_mall.model.Member;
import com.example.goorm_mall.model.Payment;
import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.repository.MemberRepository;
import com.example.goorm_mall.repository.PaymentRepository;
import com.example.goorm_mall.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final ProductRepository productRepository;
	private final ProductService productService;
	private final MemberRepository memberRepository;
	
	public Payment createPayment(Long productId, String username, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
				
		double totalPrice = product.getPrice() * quantity;
		Payment payment = new Payment();
		payment.setMember(member);
		payment.setProduct(product);
		payment.setQuantity(quantity);
		payment.setTotalPrice(totalPrice);
		payment.setPaymentStatus("결제 진행 중");
		
		return paymentRepository.save(payment);
	}
	
	public Payment getPayment(Long paymentId) {
		return paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + paymentId));
	}
	
	public Payment complatePayment(
			Long id, String recipientName, int postalCode, String address, String addressDetail, String phone, String additionalInfo) {
		Payment payment = getPayment(id);
		payment.setRecipientName(recipientName);
		payment.setPostalCode(postalCode);
		payment.setAddress(address);
		payment.setAddressDetail(addressDetail);
		payment.setPhone(phone);
		payment.setAdditionalInfo(additionalInfo);
		payment.setPaymentStatus("결제 완료");
		
		// 상품 수량 감소
		productService.reduceQuantity(payment.getProduct().getId(), payment.getQuantity());
		
		return paymentRepository.save(payment);
	}
	
	public List<Payment> getCompletedPaymentsByUsername(String username) {
		Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        return paymentRepository.findCompletedPaymentsByUsername(member);
    }
}
