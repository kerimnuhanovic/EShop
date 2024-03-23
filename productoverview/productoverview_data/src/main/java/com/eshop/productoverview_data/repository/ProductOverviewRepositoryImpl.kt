package com.eshop.productoverview_data.repository

import com.eshop.core.util.handleApiError
import com.eshop.core.data.mapper.toProduct
import com.eshop.productoverview_data.remote.ProductOverviewApi
import com.eshop.core.domain.models.Product
import com.eshop.productoverview_domain.repository.ProductOverviewRepository
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.productoverview_domain.model.ProductAdditionData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProductOverviewRepositoryImpl @Inject constructor(
    private val productOverviewApi: ProductOverviewApi
): ProductOverviewRepository {
    override suspend fun addProduct(productData: ProductAdditionData): Result<Product> {
        return try {
            val title = productData.title.toRequestBody("text/plain".toMediaTypeOrNull())
            val description = productData.description.toRequestBody("text/plain".toMediaTypeOrNull())
            val category = productData.categories.toRequestBody("text/plain".toMediaTypeOrNull())
            val price = productData.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val images = mutableListOf<MultipartBody.Part>()
            productData.images.forEach { imageFile ->
                val image = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("productImages[]", imageFile.name, image)
                images.add(imagePart)
            }
            val result = productOverviewApi.addProduct(title, description, category, price, images)
            Result.Success(result.toProduct())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun fetchPopularProducts(): Result<List<Product>> {
        return try {
            val result = productOverviewApi.fetchPopularProduct()
            Result.Success(result.map { productDto ->
                productDto.toProduct()
            })
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun fetchAllProducts(offset: Int, searchQuery: String?): Result<List<Product>> {
        return try {
            val result = productOverviewApi.fetchAllProducts(offset, searchQuery)
            Result.Success(result.map { productDto ->
                productDto.toProduct()
            })
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}
