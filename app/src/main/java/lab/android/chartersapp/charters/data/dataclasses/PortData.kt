package lab.android.chartersapp.charters.data.dataclasses

import com.google.gson.annotations.SerializedName

data class PortsResponse(
    val status: String,
    val ports: List<Port>
)

data class PortResponse(
    val status: String,
    val port: Port
)

data class Port(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String,
    @SerializedName("address") val address: String,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("website") val website: String?,
    @SerializedName("places") val places: Int?,
    @SerializedName("description") val description: String,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("latitude") val latitude: Double
)