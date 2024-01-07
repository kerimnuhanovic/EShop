package com.eshop.productoverview_domain.model

data class AddProductValidationResult(
    val isInputValid: Boolean,
    val errorMessageId: Int? = null
)
