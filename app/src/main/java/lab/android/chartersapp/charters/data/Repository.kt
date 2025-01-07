package lab.android.chartersapp.charters.data

import android.util.Log
import retrofit2.Response
import javax.inject.Inject

class BoatRepository @Inject constructor(private val apiService: OfferApiService) {
    suspend fun fetchBoats(): List<Boat> {
        val response = apiService.getBoats()
        if (response.isSuccessful) {
            response.body()?.let {
                // Return the list of boats
                return it.boats
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch boats")
    }
}