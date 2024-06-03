package com.eshop.core.domain.usecase

import com.eshop.core.domain.models.Shop
import com.eshop.core.domain.repository.FavouriteShopRepository
import com.eshop.core.util.Result
import javax.inject.Inject

class GetFavouriteShopsUseCase @Inject constructor(
    private val favouriteShopRepository: FavouriteShopRepository
) {
    suspend operator fun invoke(): Result<List<Shop>> {
        return favouriteShopRepository.listShops()
    }
}