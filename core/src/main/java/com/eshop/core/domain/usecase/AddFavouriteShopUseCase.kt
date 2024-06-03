package com.eshop.core.domain.usecase

import com.eshop.core.domain.models.FavouriteShop
import com.eshop.core.domain.repository.FavouriteShopRepository
import com.eshop.core.util.Result
import javax.inject.Inject

class AddFavouriteShopUseCase @Inject constructor(
    private val favouriteShopRepository: FavouriteShopRepository
) {
    suspend operator fun invoke(shopId: String): Result<FavouriteShop> {
        return favouriteShopRepository.addShop(shopId)
    }
}