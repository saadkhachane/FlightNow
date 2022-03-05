package com.xardev.flightnow.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.repositories.MainRepository
import com.xardev.flightnow.utils.TrampolineSchedulers
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.lang.Exception


@RunWith(JUnit4::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repo: MainRepository

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var stationsObserver: Observer<List<Station>>

    @Mock
    lateinit var flightsObserver: Observer<List<Flight>>

    @Mock
    lateinit var errorObserver: Observer<Throwable?>


    private val stations = emptyList<Station>()
    private val flights = emptyList<Flight>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = MainViewModel(repo, TrampolineSchedulers())

        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.stations.observeForever(stationsObserver)
        viewModel.flights.observeForever(flightsObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @Test
    fun `getStations() with stations updates loading to True & False`() {

        // Arrange
        doReturn(Single.just(stations))
            .`when`(viewModel.repo)
            .getStations()

        // Act
        viewModel.getStations()

        // assert
        verify(loadingObserver, atLeastOnce()).onChanged(true)
        verify(loadingObserver, atLeastOnce()).onChanged(false)

    }

    @Test
    fun `getStations() with exception updates loading to True & False`() {

        // Arrange
        doReturn(Single.error<Throwable>(Exception()))
            .`when`(viewModel.repo)
            .getStations()

        // Act
        viewModel.getStations()

        // assert
        verify(loadingObserver, atLeastOnce()).onChanged(true)
        verify(loadingObserver, atLeastOnce()).onChanged(false)

    }

    @Test
    fun `getStations() with stations returns stations`() {

        // Arrange
        doReturn(Single.just(stations))
            .`when`(viewModel.repo)
            .getStations()

        // Act
        viewModel.getStations()

        // assert
        verify(stationsObserver, atLeastOnce()).onChanged(stations)

    }

    @Test
    fun `getStations() with exception returns exception`() {

        // Arrange
        val exception = IOException("network issue!")

        doReturn(Single.error<Throwable>(exception))
            .`when`(viewModel.repo)
            .getStations()


        // Act
        viewModel.getStations()

        // assert
        verify(errorObserver, atLeastOnce()).onChanged(exception)

    }

    @Test
    fun `getFlights() with flights updates loading to True & False`() {

        // Arrange
        doReturn(Single.just(flights))
            .`when`(viewModel.repo)
            .getFlights(HashMap())

        // Act
        viewModel.getFlights(HashMap())

        // assert
        verify(loadingObserver, atLeastOnce()).onChanged(true)
        verify(loadingObserver, atLeastOnce()).onChanged(false)

    }

    @Test
    fun `getFlights() with exception updates loading to True & False`() {

        // Arrange
        doReturn(Single.error<Throwable>(Exception()))
            .`when`(viewModel.repo)
            .getFlights(HashMap())

        // Act
        viewModel.getFlights(HashMap())

        // assert
        verify(loadingObserver, atLeastOnce()).onChanged(true)
        verify(loadingObserver, atLeastOnce()).onChanged(false)

    }

    @Test
    fun `getFlights() with flights returns flights`() {

        // Arrange
        doReturn(Single.just(flights))
            .`when`(viewModel.repo)
            .getFlights(HashMap())

        // Act
        viewModel.getFlights(HashMap())

        // assert
        verify(flightsObserver).onChanged(flights)

    }

    @Test
    fun `getFlights() with exception returns exception`() {

        // Arrange
        val exception = IOException("network issue!")

        doReturn(Single.error<Throwable>(exception))
            .`when`(viewModel.repo)
            .getFlights(HashMap())

        // Act
        viewModel.getFlights(HashMap())

        // assert
        verify(errorObserver).onChanged(exception)

    }


}