package lab.android.chartersapp.charters.data.dataclasses

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class AuthResponse(
    val status: String,
    val message: String?
)

data class UserData(
    val status: String,
    val username: String,
    val email: String
)