package lab.android.chartersapp.charters.data

import com.google.gson.annotations.SerializedName

data class BoatResponse(
    val status: String,
    val boats: List<Boat>
)

data class Boat(
    val name: String,
    val boatModel: String,
    val productionYear: Int,
    val length: Int,
    val width: Int,
    val draft: Int,
    val company: String,
    val motherPort: String,
    val beds: Int,
    val pricePerDay: Int,
    val description: String
)
