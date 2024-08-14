package com.example.goorm_mall.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.goorm_mall.model.Image;
import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.repository.ImageRepository;

@Service
public class ImageService {
	private final Path fileStorageLocation;	
	private final ImageRepository imageRepository;
	
	public ImageService(@Value("${file.upload-dir}") String uploadDir, ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
		fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", e);
		}
	}
	
	public List<Image> storeImages(List<MultipartFile> files, Product product) {
        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            try {
                // 파일 저장 경로 설정
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                // 이미지 엔티티 생성 및 상품과 연결
                Image image = new Image();
                image.setFileName(fileName);
                image.setFilePath("/uploads/" + fileName);
                image.setProduct(product);

                imageRepository.save(image);
            } catch (IOException ex) {
                throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
            }
        }
        return images;
    }
	
	public Image getImage(Long id) {
		return imageRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }
}
