package com.umc.meetpick.service;

import com.umc.meetpick.entity.Review;
import com.umc.meetpick.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // 내가 작성한 후기 조회
    public List<Review> getMyReviews(Long writerId) {
        return reviewRepository.findByWriter_Id(writerId);
    }

    // 나에 대한 후기 조회
    public List<Review> getReceivedReviews(Long matchingMemberId) {
        return reviewRepository.findByMatchingMember_Id(matchingMemberId);
    }

    // 후기 작성
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
}