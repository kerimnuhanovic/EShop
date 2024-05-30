package com.eshop.core.domain.usecase

import com.eshop.core.domain.repository.FavouriteShopRepository
import com.eshop.core.util.Result
import javax.inject.Inject

class CheckIsShopFavouriteUseCase @Inject constructor(
    private val favouriteShopRepository: FavouriteShopRepository
) {
    suspend operator fun invoke(shopId: String): Result<Boolean> {
        return favouriteShopRepository.checkIsShopFavourite(shopId)
    }
}