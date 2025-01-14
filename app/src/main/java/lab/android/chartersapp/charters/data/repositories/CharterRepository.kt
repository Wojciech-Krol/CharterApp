package lab.android.chartersapp.charters.data.repositories

import lab.android.chartersapp.charters.data.ChartersApiService
import lab.android.chartersapp.charters.data.dataclasses.Charter
import java.util.Date
import javax.inject.Inject


class CharterRepository @Inject constructor(private val apiService: ChartersApiService) {
    suspend fun fetchChartersByBoat(boatName: String): List<Charter> {
        val response = apiService.getChartersByBoat(boatName)
        if (response.isSuccessful) {
            response.body()?.let {
                return it.charters
            }
        }

        throw Exception("Failed to fetch charters by boat, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun fetchChartersByUser(): List<Charter> {
        val response = apiService.getChartersByUser()
        if (response.isSuccessful) {
            response.body()?.let {
                return it.charters
            }
        }

        throw Exception("Failed to fetch charters by user, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun addCharter(boatName: String, startDate: Date, endDate: Date): Boolean {
        val response = apiService.addCharter(boatName, startDate.toString(), endDate.toString())
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }

        throw Exception("Failed to add charter, response code: ${response.code()}, message: ${response.message()}")
    }
}