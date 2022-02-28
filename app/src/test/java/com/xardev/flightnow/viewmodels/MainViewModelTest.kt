package com.xardev.flightnow.viewmodels

import com.xardev.flightnow.data.remote.ApiService
import com.xardev.flightnow.repositories.MainRepository
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest {

    @Mock
    lateinit var repo: MainRepository

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getStations(){

        assertTrue(true)
    }


}