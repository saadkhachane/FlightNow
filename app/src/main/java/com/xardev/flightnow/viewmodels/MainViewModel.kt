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
import kotlinx.coroutines.delay
import java.io.IOException

private const val TAG = "here"

@HiltViewModel
class MainViewModel @Inject constructor(
    val repo : MainRepositoryImpl
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error : LiveData<String?> = _error

    private val _stations = MutableLiveData<List<Station>>(null)
    val stations : LiveData<List<Station>> = _stations

    private val _flights = MutableLiveData<List<Flight>>(null)
    val flights : LiveData<List<Flight>> = _flights

    val search_params = MutableLiveData<HashMap<String, String>>()

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

        search_params.postValue(
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
                   _error.postValue("Please check your internet connexion and try again.")
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
                _error.postValue("Please check your internet connexion and try again.")
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