package com.xardev.flightnow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo : MainRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Throwable?>()
    val error : LiveData<Throwable?> = _error

    private val _stations = MutableLiveData<List<Station>>(null)
    val stations : LiveData<List<Station>> = _stations

    private val _flights = MutableLiveData<List<Flight>>(null)
    val flights : LiveData<List<Flight>> = _flights

    val searchParams = MutableLiveData<HashMap<String, String>>()

    init {
        val params = HashMap<String, String>()
        params["dateout"] = ""
        params["origin"] = ""
        params["destination"] = ""
        params["adt"] = "1"
        params["chd"] = "0"
        params["teen"] = "0"
        params["inf"] = "0"
        params["ToUs"] = "AGREED"

        searchParams.postValue(
            params
        )

    }

    fun getStations() {

       repo.getStations()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .doOnSubscribe {
               disposables.add(it)
               _isLoading.postValue(true)
           }
           .doOnSuccess {
               _error.postValue(null)
               _stations.postValue(it)
           }.onErrorReturn {
               if (it is IOException)
                   _error.postValue(it)
               return@onErrorReturn emptyList<Station>()
           }.doFinally {
               _isLoading.postValue(false)
           }.subscribe()

    }

    fun getFlights(paramsMap: Map<String, String>) {

        repo.getFlights(paramsMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                disposables.add(it)
                _isLoading.postValue(true)
            }
            .doOnSuccess {
                _flights.postValue(it)
            }.onErrorReturn {
                if (it is IOException)
                _error.postValue(it)
                return@onErrorReturn emptyList<Flight>()
            }.doFinally {
                _isLoading.postValue(false)
            }.subscribe()

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}