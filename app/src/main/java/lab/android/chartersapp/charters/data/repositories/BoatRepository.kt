package lab.android.chartersapp.charters.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
        throw Exception("Failed to fetch, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun fetchBoatsByPort(portName: String): List<Boat> {
        val response = apiService.getBoatsByPort(portName)
        if (response.isSuccessful) {
            response.body()?.let {
                // Return the list of boats
                return it.boats
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch boats by port, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun fetchBoatsByCompany(companyName: String): List<Boat> {
        val response = apiService.getBoatsByCompany(companyName)
        if (response.isSuccessful) {
            response.body()?.let {
                // Return the list of boats
                return it.boats
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch boats by company, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun fetchBoatDetails(boatName: String): Boat {
        val response = apiService.getBoatDetails(boatName)
        if (response.isSuccessful) {
            response.body()?.let {
                // Return the boat details
                return it
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch boat details, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun fetchBoatPhotos(boatName: String): List<String> {
        val response = apiService.getBoatPhotos(boatName)
        if (response.isSuccessful) {
            response.body()?.let {
                // Return the list of photo URLs
                return it.map { photo -> photo.url }
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch boat photos, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun fetchPhoto(imgName: String): Bitmap? {
        val response = apiService.getPhoto(imgName)
        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                val inputStream = responseBody.byteStream()
                return BitmapFactory.decodeStream(inputStream)
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch photo, response code: ${response.code()}, message: ${response.message()}")
    }
}