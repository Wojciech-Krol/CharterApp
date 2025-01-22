package lab.android.chartersapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Boat
import lab.android.chartersapp.charters.data.dataclasses.Charter
import lab.android.chartersapp.charters.data.repositories.BoatRepository
import lab.android.chartersapp.charters.data.repositories.CharterRepository
import lab.android.chartersapp.charters.presentation.searchBar.BoatViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class BoatViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val boatRepository = mock(BoatRepository::class.java)
    private val charterRepository = mock(CharterRepository::class.java)
    private val viewModel = BoatViewModel(boatRepository, charterRepository)

    @Test
    fun testGetBoatPhotos() = runBlockingTest {
        val boatName = "Test Boat"
        val photoUrls = listOf("url1", "url2")
        `when`(boatRepository.fetchBoatPhotos(boatName)).thenReturn(photoUrls)

        val observer = mock(Observer::class.java) as Observer<ApiState<List<String>>>
        viewModel.boatPhotos.observeForever(observer)

        viewModel.getBoatPhotos(boatName)

        verify(observer).onChanged(ApiState.Loading)
        verify(observer).onChanged(ApiState.Success(photoUrls))
    }

    @Test
    fun testGetBoats() = runBlockingTest {
        val boats = listOf(
            Boat(name = "Boat 1", boatModel = "Model A", productionYear = 2020, company = "Company A", motherPort = "Port A", description = "Description A", length = 10, width = 3, draft = 1, pricePerDay = 100, beds = 4, contactEmail = "email1@example.com", contactPhone = "123456789"),
            Boat(name = "Boat 2", boatModel = "Model B", productionYear = 2021, company = "Company B", motherPort = "Port B", description = "Description B", length = 12, width = 4, draft = 2, pricePerDay = 150, beds = 6, contactEmail = "email2@example.com", contactPhone = "987654321")
        )
        `when`(boatRepository.fetchBoats()).thenReturn(boats)

        val observer = mock(Observer::class.java) as Observer<ApiState<List<Boat>>>
        viewModel.boats.observeForever(observer)

        viewModel.getBoats()

        verify(observer).onChanged(ApiState.Loading)
        verify(observer).onChanged(ApiState.Success(boats))
    }

    @Test
    fun testGetCharters() = runBlockingTest {
        val boatName = "Test Boat"
        val charters = listOf(
            Charter(boat = boatName, startDate = "2023-01-01", endDate = "2023-01-07", price = 700, user= "user1"),
            Charter(boat = boatName, startDate = "2023-02-01", endDate = "2023-02-07", price = 800, user= "user2")
        )
        `when`(charterRepository.fetchChartersByBoat(boatName)).thenReturn(charters)

        val observer = mock(Observer::class.java) as Observer<ApiState<List<Charter>>>
        viewModel.charters.observeForever(observer)

        viewModel.getCharters(boatName)

        verify(observer).onChanged(ApiState.Loading)
        verify(observer).onChanged(ApiState.Success(charters))
    }

    @Test
    fun testGetBoatsByPort() = runBlockingTest {
        val port = "Test Port"
        val boats = listOf(
            Boat(name = "Boat 1", boatModel = "Model A", productionYear = 2020, company = "Company A", motherPort = port, description = "Description A", length = 10, width = 3, draft = 1, pricePerDay = 100, beds = 4, contactEmail = "email1@example.com", contactPhone = "123456789"),
            Boat(name = "Boat 2", boatModel = "Model B", productionYear = 2021, company = "Company B", motherPort = port, description = "Description B", length = 12, width = 4, draft = 2, pricePerDay = 150, beds = 6, contactEmail = "email2@example.com", contactPhone = "987654321")
        )
        `when`(boatRepository.fetchBoatsByPort(port)).thenReturn(boats)

        val observer = mock(Observer::class.java) as Observer<ApiState<List<Boat>>>
        viewModel.boats.observeForever(observer)

        viewModel.getBoatsByPort(port)

        verify(observer).onChanged(ApiState.Loading)
        verify(observer).onChanged(ApiState.Success(boats))
    }
}