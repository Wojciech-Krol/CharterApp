package lab.android.chartersapp.charters.data.dataclasses

data class PortsResponse(
    val status: String,
    val ports: List<Port>
)

data class Port(
    val name: String,
    val country: String,
    val city: String,
    val address: String,
    val contactPhone: String?,
    val contactEmail: String?,
    val website: String?,
    val places: Int?,
    val description: String,
    val longitude: Double,
    val latitude: Double
)