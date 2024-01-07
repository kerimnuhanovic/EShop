package com.eshop.productoverview_domain.usecase

import android.net.Uri
import com.eshop.productoverview_domain.R
import com.eshop.productoverview_domain.model.AddProductValidationResult
import javax.inject.Inject

class AddProductInputValidationUseCase @Inject constructor() {
    operator fun invoke(
        title: String,
        description: String,
        price: String,
        categories: List<String>,
        images: List<Uri>,
    ): AddProductValidationResult {
        return when {
            title.length < 2 -> AddProductValidationResult(false, R.string.product_title_too_short)
            description.isEmpty() -> AddProductValidationResult(
                false,
                R.string.product_description_too_short
            )

            price.toDoubleOrNull() == null -> AddProductValidationResult(
                false,
                R.string.product_price_invalid_format
            )

            categories.isEmpty() -> AddProductValidationResult(
                false,
                R.string.product_category_not_selected
            )

            images.isEmpty() -> AddProductValidationResult(
                false,
                R.string.no_product_image_selected
            )

            else -> AddProductValidationResult(true)
        }
    }
}