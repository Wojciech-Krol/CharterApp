package lab.android.chartersapp.charters.data.dataclasses

data class BoatsResponse(
    val status: String,
    val boats: List<Boat>
)

data class BoatResponse(
    val status: String,
    val boat: Boat
)

data class Boat(
    val name: String,
    val boatModel: String,
    val productionYear: Int,
    val length: Int,
    val width: Int?,
    val draft: Int,
    val company: String,
    val contactEmail: String?,
    val contactPhone: String?,
    val motherPort: String,
    val beds: Int,
    val pricePerDay: Int,
    val description: String
)

data class BoatPhotoResponse(
    val status: String,
    val photos: List<BoatPhoto>
)

data class BoatPhoto(
    val url: String,
    val boatName: String
)
