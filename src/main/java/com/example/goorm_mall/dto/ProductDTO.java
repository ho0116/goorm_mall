package com.example.goorm_mall.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	private String name;
	private String description;
	private double price;
	private int quantity;
	private List<MultipartFile> images;
}
