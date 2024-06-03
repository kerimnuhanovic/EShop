package com.eshop.signup_data.repository

import com.eshop.core.data.mapper.toAccessToken
import com.eshop.core.domain.models.AccessToken
import com.eshop.core.util.Result
import com.eshop.core.util.handleSignupApiFailure
import com.eshop.signup_data.remote.SignupApi
import com.eshop.signup_domain.models.RegistrationData
import com.eshop.signup_domain.repository.SignupRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class SignupRepositoryImpl @Inject constructor(
    private val signupApi: SignupApi
) : SignupRepository {
    override suspend fun register(data: RegistrationData): Result<AccessToken> {
        val name = data.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val surname = data.surname.toRequestBody("text/plain".toMediaTypeOrNull())
        val username = data.username.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = data.email.toRequestBody("text/plain".toMediaTypeOrNull())
        val password = data.password.toRequestBody("text/plain".toMediaTypeOrNull())
        val userType = data.userType.toRequestBody("text/plain".toMediaTypeOrNull())
        val shopCategories = data.shopCategories.toRequestBody("text/plain".toMediaTypeOrNull())
        val shopLocations = data.shopLocations.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageFile = data.profileImage
        val imageFileBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImage =
            MultipartBody.Part.createFormData("profileImage", imageFile.name, imageFileBody)
        return try {
            Result.Success(
                signupApi.registerUser(
                    name, surname, username, email, userType, password, shopCategories, shopLocations, profileImage
                ).toAccessToken()
            )
        } catch (ex: Exception) {
            println("Exception: ${ex}")
            handleSignupApiFailure(ex)
        }
    }
}