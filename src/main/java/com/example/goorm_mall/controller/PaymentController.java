package com.example.goorm_mall.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.goorm_mall.model.Payment;
import com.example.goorm_mall.service.PaymentService;

@Controller
@RequestMapping("/payments")
public class PaymentController {
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/preview")
	public String paymentPreview(
			@RequestParam Long productId,
			@RequestParam int quantity,
			@AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		Payment payment = new Payment();
		payment = paymentService.createPayment(productId, userDetails.getUsername(), quantity);
		return "redirect:/payments/view/" + payment.getId();
	}
	
	@GetMapping("/view/{id}")
	public String paymentPreview(@PathVariable Long id, Model model) {
		Payment payment = paymentService.getPayment(id);
		model.addAttribute("payment", payment);
	    return "payment/view";
	}
	
	@GetMapping("/confirm/{id}")
	public String confirmPayment(@PathVariable Long paymentId, Model model) {
		Payment payment = paymentService.getPayment(paymentId);
		model.addAttribute("payment", payment);
		return "payment/confirm";
	}
	
	@PostMapping("/complete")
	public String paymentComplate(
				@RequestParam Long paymentId,
				@RequestParam String recipientName,
				@RequestParam int postalCode,
				@RequestParam String address,
				@RequestParam String addressDetail,
				@RequestParam String phone,
				@RequestParam String additionalInfo
	) {
		paymentService.complatePayment(
				paymentId, recipientName, postalCode, address, addressDetail, phone, additionalInfo);
		
		return "redirect:/payments/complete/" + paymentId;
	}
	
	@GetMapping("/complete/{id}")
	public String viewPaymentComplate(@PathVariable Long id, Model model) {
		Payment payment = paymentService.getPayment(id);
		
		model.addAttribute(payment);
		return "payment/complete";
	}
	
}
