package com.eshop.core.domain.usecase

import com.eshop.core.domain.models.Product
import com.eshop.core.domain.repository.FavouriteProductRepository
import com.eshop.core.util.Result
import javax.inject.Inject

class GetFavouriteProductsUseCase @Inject constructor(
    private val favouriteProductRepository: FavouriteProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return favouriteProductRepository.listProducts()
    }
}