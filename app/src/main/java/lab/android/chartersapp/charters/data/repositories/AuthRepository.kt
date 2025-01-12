package lab.android.chartersapp.charters.data.repositories

import lab.android.chartersapp.charters.data.AuthApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: AuthApiService) {
    suspend fun login(username: String, password: String): Boolean {
        val response = apiService.login(username, password)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }

        throw Exception("Failed to login, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun logout(): Boolean {
        val response = apiService.logout()
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }

        throw Exception("Failed to logout, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun register(username: String, password: String, email: String): Boolean {
        val response = apiService.register(username, password, email)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
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
        val response = apiService.changePassword(oldPassword, newPassword)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }

        throw Exception("Failed to update password, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun updateEmail(newEmail: String): Boolean {
        val response = apiService.changeEmail(newEmail)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }

        throw Exception("Failed to update email, response code: ${response.code()}, message: ${response.message()}")
    }
}