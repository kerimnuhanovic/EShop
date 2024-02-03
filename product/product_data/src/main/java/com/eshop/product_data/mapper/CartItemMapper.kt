package com.eshop.product_data.mapper

import com.eshop.product_data.remote.dto.CartItemDto
import com.eshop.product_domain.model.CartItem

fun CartItemDto.toCartItem() = CartItem(this._id, this.customerId, this.productId)