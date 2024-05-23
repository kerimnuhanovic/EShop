package com.eshop.shop_domain.usecase

import com.eshop.core.util.Result
import com.eshop.shop_domain.model.Review
import com.eshop.shop_domain.repository.ShopRepository
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(shopId: String, comment: String, rating: Int): Result<Review> {
        return shopRepository.addReview(shopId, comment, rating)
    }
}