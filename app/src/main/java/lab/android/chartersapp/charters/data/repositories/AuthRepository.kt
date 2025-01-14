package lab.android.chartersapp.charters.data.repositories

import android.util.Log
import lab.android.chartersapp.charters.data.AuthApiService
import lab.android.chartersapp.charters.data.dataclasses.LoginRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: AuthApiService) {
    suspend fun login(username: String, password: String): Boolean {
        val usernameBody = username.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val response = apiService.login(usernameBody, passwordBody)
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "success") {
                    return true
                }
                else {
                    return false
                }
            }
        }

        throw Exception("Failed to login, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun logout(): Boolean {
        val response = apiService.logout()
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "success") {
                    return true
                }
            }
        }

        throw Exception("Failed to logout, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun register(username: String, password: String, email: String): Boolean {
        val usernameBody = username.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val response = apiService.register(usernameBody, passwordBody)
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "success") {
                    return true
                }
            }
        }

        throw Exception("Failed to register, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun getUser(): Pair<String, String> {
        val response = apiService.getUser()
        if (response.isSuccessful) {
            response.body()?.let {
                return Pair(it.username, it.email)
            }
        }
        throw Exception("Failed to get user, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun updatePassword(oldPassword: String, newPassword: String): Boolean {
        val oldPasswordBody = oldPassword.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val newPasswordBody = newPassword.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val response = apiService.changePassword(oldPasswordBody, newPasswordBody)
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "success") {
                    return true
                }
            }
        }

        throw Exception("Failed to update password, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun updateEmail(newEmail: String): Boolean {
        val newEmailBody = newEmail.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val response = apiService.changeEmail(newEmailBody)
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "success") {
                    return true
                }
            }
        }

        throw Exception("Failed to update email, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun getSession(): Boolean {
        val response = apiService.getSession()
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "success") {
                    return true
                }
            }
        }

        throw Exception("Failed to get session, response code: ${response.code()}, message: ${response.message()}")
    }
}