package com.umc.meetpick.controller;

import com.umc.meetpick.dto.ReviewDTO;
import com.umc.meetpick.entity.Review;
import com.umc.meetpick.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.repository.MemberRepository;
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    public ReviewController(ReviewService reviewService, MemberRepository memberRepository) {
        this.reviewService = reviewService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/written/{writerId}")
    public ResponseEntity<List<ReviewDTO>> getMyReviews(@PathVariable Long writerId) {
        List<Review> reviews = reviewService.getMyReviews(writerId);
        List<ReviewDTO> reviewDTOs = convertToDTO(reviews);
        return ResponseEntity.ok(reviewDTOs);
    }

    @GetMapping("/received/{matchingMemberId}")
    public ResponseEntity<List<ReviewDTO>> getReceivedReviews(@PathVariable Long matchingMemberId) {
        List<Review> reviews = reviewService.getReceivedReviews(matchingMemberId);
        List<ReviewDTO> reviewDTOs = convertToDTO(reviews);
        return ResponseEntity.ok(reviewDTOs);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        Review savedReview = reviewService.createReview(review);
        return ResponseEntity.ok(convertToDTO(savedReview));
    }

    // DTO 변환 메소드들
    private List<ReviewDTO> convertToDTO(List<Review> reviews) {
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .senderID(review.getWriter().getId())
                .receiverID(review.getMatchingMember().getId())
                .reviewContent(review.getContent())
                .build();
    }

    private Review convertToEntity(ReviewDTO dto) {
        Member writer = memberRepository.findById(dto.getSenderID())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));

        Member matchingMember = memberRepository.findById(dto.getReceiverID())
                .orElseThrow(() -> new IllegalArgumentException("받는 사람을 찾을 수 없습니다."));

        return Review.builder()
                .writer(writer)
                .matchingMember(matchingMember)
                .content(dto.getReviewContent())
                .build();
    }
}