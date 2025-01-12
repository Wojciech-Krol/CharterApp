package lab.android.chartersapp.charters.data.repositories

import lab.android.chartersapp.charters.data.PortsApiService
import lab.android.chartersapp.charters.data.dataclasses.Port
import javax.inject.Inject

class PortRepository @Inject constructor(private val apiService: PortsApiService) {
    suspend fun fetchPorts(): List<Port> {
        val response = apiService.getPorts()
        if (response.isSuccessful) {
            response.body()?.let {
                return it.ports
            }
        }

        // If the response is not successful, handle the error
        throw Exception("Failed to fetch ports, response code: ${response.code()}, message: ${response.message()}")
    }

    suspend fun fetchPortByName(name: String): Port {
        val response = apiService.getPortDetails(name)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }
        // If the response is not successful, handle the error
        throw Exception("Failed to fetch port by name, response code: ${response.code()}, message: ${response.message()}")
    }
}