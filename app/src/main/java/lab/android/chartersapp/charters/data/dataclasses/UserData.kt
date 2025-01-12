package lab.android.chartersapp.charters.data.dataclasses

data class AuthResponse(
    val status: String,
    val message: String?
)

data class UserData(
    val status: String,
    val username: String,
    val email: String
)