package com.eshop.core.domain.usecase

import com.eshop.core.domain.models.FavouriteProduct
import com.eshop.core.domain.repository.FavouriteProductRepository
import com.eshop.core.util.Result
import javax.inject.Inject

class AddFavouriteProductUseCase @Inject constructor(
    private val favouriteProductRepository: FavouriteProductRepository
) {
    suspend operator fun invoke(productId: String): Result<FavouriteProduct> {
        return favouriteProductRepository.addProduct(productId)
    }
}