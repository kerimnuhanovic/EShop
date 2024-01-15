package com.eshop.core.domain.usecase

import javax.inject.Inject

class ConvertListToStringUseCase @Inject constructor() {
    operator fun invoke(list: List<String>): String {
        return list.joinToString("$")
    }
}