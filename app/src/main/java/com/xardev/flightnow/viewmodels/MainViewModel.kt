package com.xardev.flightnow.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.repositories.MainRepositoryImpl
import com.xardev.flightnow.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "here"
class MainViewModel @Inject constructor(
    val repo : MainRepositoryImpl
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _stations = MutableLiveData<List<Station>>(emptyList())
    val stations : LiveData<List<Station>> = _stations

    private val _flights = MutableLiveData<List<Flight>>(emptyList())
    val flights : LiveData<List<Flight>> = _flights

    fun getStations() {

       repo.getStations()
           .doOnSubscribe {
               disposables.add(it)
               _isLoading.postValue(true)
           }
           .doOnSuccess {
               Log.d(TAG, "Stations: ${it}")
           }.doOnError{
               Log.d(TAG, "Error: ${it.message}")
           }.doFinally {
               _isLoading.postValue(false)
           }.subscribe()

    }

    fun getFlights(paramsMap: Map<String, String>) {

        repo.getFlights(paramsMap)
            .doOnSubscribe {
                disposables.add(it)
                _isLoading.postValue(true)
            }
            .doOnSuccess {
                Log.d(TAG, "Flights: ${it}")
            }.doOnError{
                Log.d(TAG, "Error: ${it.message}")
            }.doFinally {
                _isLoading.postValue(false)
            }.subscribe()

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}