package lab.android.chartersapp.charters.data

import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("company") val company: String,
    @SerializedName("year") val year: Int,
    @SerializedName("length") val length: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("passengers") val passengers: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("location") val location: String
)