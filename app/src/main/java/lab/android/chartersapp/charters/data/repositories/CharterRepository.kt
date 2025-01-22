package lab.android.chartersapp.charters.data.repositories

import android.util.Log
import lab.android.chartersapp.charters.data.ChartersApiService
import lab.android.chartersapp.charters.data.dataclasses.Charter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
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

    suspend fun fetchChartersByUser(userName:String): List<Charter> {
        val response = apiService.getChartersByUser(userName)
        if (response.isSuccessful) {
            response.body()?.let {
                return it.charters
            }
        }

        throw Exception("Failed to fetch charters by user, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun addCharter(boatName: String, startDate: String, endDate: String, userName: String): Boolean {

        val boatNameBody = boatName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val startDateBody = startDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val endDateBody = endDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val userNameBody = userName.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val response = apiService.addCharter(boatNameBody, startDateBody, endDateBody, userNameBody)
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "success") {
                    return true
                }
            }
        }

        throw Exception("Failed to add charter, response code: ${response.code()}, message: ${response.body()?.message ?: ""}")
    }
}