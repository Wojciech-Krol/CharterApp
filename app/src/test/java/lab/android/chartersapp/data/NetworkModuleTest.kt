package lab.android.chartersapp.charters.data.repositories

import kotlinx.coroutines.runBlocking
import lab.android.chartersapp.charters.data.AuthApiService
import lab.android.chartersapp.charters.data.BoatsApiService
import lab.android.chartersapp.charters.data.ChartersApiService
import lab.android.chartersapp.charters.data.NetworkModule
import lab.android.chartersapp.charters.data.dataclasses.AuthResponse
import mil.nga.crs.common.DateTime
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthRepositoryTest {
    private lateinit var authApiService: AuthApiService
    private lateinit var authRepository: AuthRepository
    private lateinit var networkModule: NetworkModule

    @Before
    fun setUp() {
        networkModule = NetworkModule()
        authApiService = networkModule.provideAuthApiService(
            networkModule.provideRetrofit(networkModule.provideOkHttpClient())
        )
        authRepository = AuthRepository(authApiService)
    }

    @Test
    fun login() {
        runBlocking {
            val username = "user1"
            val password = "password1"

            val response = authRepository.login(username, password)

            assert(response)
        }
    }

    @Test
    fun loginWithWrongCredentials() {
        runBlocking {
            val username = "user1"
            val password = "password2"

            val response = authRepository.login(username, password)

            assert(!response)
        }
    }

    @Test
    fun logout() {
        runBlocking {
            val response = authRepository.logout()

            assert(response)
        }
    }
}

class BoatsRepositoryTest {
    private lateinit var boatsApiService: BoatsApiService
    private lateinit var boatsRepository: BoatRepository
    private lateinit var networkModule: NetworkModule

    @Before
    fun setUp() {
        networkModule = NetworkModule()
        boatsApiService = networkModule.provideApiService(
            networkModule.provideRetrofit(networkModule.provideOkHttpClient())
        )
        boatsRepository = BoatRepository(boatsApiService)
    }

    @Test
    fun getBoats() {
        runBlocking {
            val response = boatsRepository.fetchBoats()

            assert(response.isNotEmpty())
        }
    }

    @Test
    fun getBoatsByPort() {
        runBlocking {
            val portName = "Port Węgorzewo"
            val response = boatsRepository.fetchBoatsByPort(portName)

            val count = response.count { it.motherPort == portName }

            assert(count == response.size)
            assert(count == 2)
        }
    }

    @Test
    fun getBoatsByCompany() {
        runBlocking {
            val companyName = "Olecko Boats"
            val response = boatsRepository.fetchBoatsByCompany(companyName)

            val count = response.count { it.company == companyName }

            assert(count == response.size)
            assert(count == 6)
        }
    }

    @Test
    fun getBoatDetails() {
        runBlocking {
            val boatName = "Mazury Cruiser 9"
            val boatModel = "Cruiser 30"
            val productionYear = 2018
            val length = 30
            val width = 10
            val draft = 5
            val company = "Mazury Boats"
            val contactEmail = "info@mazuryboats.pl"
            val contactPhone = "123123123"
            val motherPort = "Port Sztynort"
            val beds = 6
            val pricePerDay = 500
            val response = boatsRepository.fetchBoatDetails(boatName)

            assert(response.name == boatName)
            assert(response.boatModel == boatModel)
            assert(response.productionYear == productionYear)
            assert(response.length == length)
            assert(response.width == width)
            assert(response.draft == draft)
            assert(response.company == company)
            assert(response.contactEmail == contactEmail)
            assert(response.contactPhone == contactPhone)
            assert(response.motherPort == motherPort)
            assert(response.beds == beds)
            assert(response.pricePerDay == pricePerDay)
        }
    }
}

class CharterRepositoryTest {
    private lateinit var apiService: ChartersApiService
    private lateinit var repository: CharterRepository
    private lateinit var networkModule: NetworkModule

    @Before
    fun setUp() {
        networkModule = NetworkModule()
        apiService = networkModule.provideChartersApiService(
            networkModule.provideRetrofit(networkModule.provideOkHttpClient())
        )
        repository = CharterRepository(apiService)
    }

    @Test
    fun fetchChartersByBoat() {
        runBlocking {
            val boatName = "Olecko Cruiser 6"
            val response = repository.fetchChartersByBoat(boatName)

            assert(response.isNotEmpty())
        }
    }

    @Test
    fun fetchChartersByBoat2() {
        runBlocking {
            val boatName = "Mazury Cruiser 8"
            val response = repository.fetchChartersByBoat(boatName)

            assert(!response.isNotEmpty())
        }
    }

    @Test
    fun fetchChartersByUser() {
        runBlocking {
            val userName = "user1"
            val response = repository.fetchChartersByUser(userName)

            var count = response.size

            assert(response.isNotEmpty())
            assert(count == 11)
        }
    }

    @Test
    fun addCharter() {
        runBlocking {
            val boatName = "Mazury Cruiser 9"
            val startDate = "2021-06-10"
            val endDate = "2021-06-11"
            val userName = "user2"

            val response = repository.addCharter(boatName, startDate, endDate, userName)

            assert(response)
        }
    }

    @Test
    fun addCharter2() {
        runBlocking {
            val boatName = "Mazury Cruiser 9"
            val startDate = "2021-06-01"
            val endDate = "2021-06-07"
            val userName = "user1"

            try {
                repository.addCharter(boatName, startDate, endDate, userName)

                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }
}