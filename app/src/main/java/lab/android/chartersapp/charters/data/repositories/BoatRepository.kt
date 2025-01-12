package lab.android.chartersapp.charters.data.repositories

import lab.android.chartersapp.charters.data.BoatsApiService
import lab.android.chartersapp.charters.data.dataclasses.Boat
import javax.inject.Inject

class BoatRepository @Inject constructor(private val apiService: BoatsApiService) {
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

    suspend fun fetchBoatByName(name: String): Boat? {
        val response = apiService.getBoats() // Assuming you have the method to fetch all boats
        if (response.isSuccessful) {
            response.body()?.let {
                // Find the boat by its name
                return it.boats.find { boat -> boat.name == name }
            }
        }
        // If the response is not successful, handle the error
        throw Exception("Failed to fetch boat by name")
    }

}