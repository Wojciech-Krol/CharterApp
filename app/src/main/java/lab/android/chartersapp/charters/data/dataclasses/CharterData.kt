package lab.android.chartersapp.charters.data.dataclasses

data class ChartersResponse(
    val status: String,
    val charters: List<Charter>
)

data class Charter(
    val boat: String?,
    val startDate: String,
    val endDate: String,
    val price: Int?,
    val user: String?
)

data class AddCharterResponse(
    val status: String,
    val message: String?
)