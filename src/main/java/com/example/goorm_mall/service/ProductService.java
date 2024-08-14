package com.example.goorm_mall.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.goorm_mall.model.Member;
import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.model.ProductComment;
import com.example.goorm_mall.model.ProductLike;
import com.example.goorm_mall.repository.MemberRepository;
import com.example.goorm_mall.repository.ProductCommentRepository;
import com.example.goorm_mall.repository.ProductLikeRepository;
import com.example.goorm_mall.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductLikeRepository likeRepository;
    private final ProductCommentRepository commentRepository;
    private final ImageService imageService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    }

    @Transactional
    public Product addProduct(String name, String description, double price, int quantity, List<MultipartFile> images) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setViewCount(0);

        Product savedProduct = productRepository.save(product);

        imageService.storeImages(images, savedProduct);
        // savedProduct.setImages(imageEntities);

        return savedProduct;

    }

    public Product updateProduct(Long id, String name, String description, double price, int quantity) {
        Product product = getProductById(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateViewCount(Long id, HttpSession session) {
        Product product = getProductById(id);
        // 세션에 조회 기록이 있는지 확인
        Map<Long, LocalDateTime> viewedProducts = (Map<Long, LocalDateTime>) session.getAttribute("viewedProducts");
        if (viewedProducts == null) {
            viewedProducts = new HashMap();
        }
        LocalDateTime lastViewTime = viewedProducts.get(id);
        // 이미 조회한 제품이 아니면 조회수 증가
        if (lastViewTime == null || lastViewTime.plusHours(12).isBefore(LocalDateTime.now())) {
            product.setViewCount(product.getViewCount() + 1);
            productRepository.save(product);
            viewedProducts.put(id, LocalDateTime.now());
            session.setAttribute("viewedProducts", viewedProducts);
        }
        return product;
    }

    public void addLike(Long ProductId, String username) {
        Product product = getProductById(ProductId);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));

        if (likeRepository.findByProductAndMember(product, member).isEmpty()) {
            ProductLike productLike = new ProductLike();
            productLike.setProduct(product);
            ;
            productLike.setMember(member);
            likeRepository.save(productLike);
        }
    }

    public void removeLike(Long productId, String username) {
        Product product = getProductById(productId);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));

        likeRepository.findByProductAndMember(product, member).ifPresent(likeRepository::delete);
    }

    public Long countLikesByProductId(Long productId) {
        return likeRepository.countByProductId(productId);
    }

    public List<ProductComment> getCommentsByProductId(Long productId) {
        return commentRepository.getCommentsByProductId(productId);
    }

    public void addComment(Long productId, String username, String content) {
        Product product = getProductById(productId);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        ProductComment comment = new ProductComment();
        comment.setProduct(product);
        comment.setMember(member);
        comment.setContent(content);
        commentRepository.save(comment);
    }

    public List<Product> getLikedProductsByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        return likeRepository.findProductsByMember(member);
    }

    public void reduceQuantity(Long productId, int quantity) {
        Product product = productRepository.findById(productId).get();
        int updateQuantity = product.getQuantity() - quantity;

        if (updateQuantity < 0) {
            throw new RuntimeException("재고가 충분하지 않습니다.");
        }
        product.setQuantity(updateQuantity);
        productRepository.save(product);
    }
}