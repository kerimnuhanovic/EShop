package com.eshop.productoverview_data.repository

import com.eshop.core.util.handleApiError
import com.eshop.productoverview_data.mapper.toProduct
import com.eshop.productoverview_data.remote.ProductApi
import com.eshop.productoverview_domain.model.Product
import com.eshop.productoverview_domain.repository.ProductRepository
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.productoverview_domain.model.ProductAdditionData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
): ProductRepository {
    override suspend fun addProduct(productData: ProductAdditionData): Result<Product> {
        return try {
            val title = productData.title.toRequestBody("text/plain".toMediaTypeOrNull())
            val description = productData.description.toRequestBody("text/plain".toMediaTypeOrNull())
            val category = productData.category.toRequestBody("text/plain".toMediaTypeOrNull())
            val price = productData.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val images = mutableListOf<MultipartBody.Part>()
            productData.images.forEach { imageFile ->
                val image = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("productImages[]", imageFile.name, image)
                images.add(imagePart)
            }
            val result = productApi.addProduct(title, description, category, price, images)
            Result.Success(result.toProduct())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}
