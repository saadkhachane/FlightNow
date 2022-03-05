package com.xardev.flightnow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.repositories.MainRepository
import com.xardev.flightnow.utils.BaseSchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val repo: MainRepository,
    private val schedulers: BaseSchedulers
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _stations = MutableLiveData<List<Station>>(emptyList())
    val stations: LiveData<List<Station>> = _stations

    private val _flights = MutableLiveData<List<Flight>>(emptyList())
    val flights: LiveData<List<Flight>> = _flights

    val searchParams = MutableLiveData<HashMap<String, String>?>(null)

    fun getStations() {

        repo.getStations()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                disposables.add(it)
                _isLoading.postValue(true)
            }
            .doOnSuccess {
                _stations.postValue(it)
            }
            .onErrorReturn {
                _error.postValue(it)
                return@onErrorReturn emptyList()
            }.doFinally {
                _isLoading.postValue(false)
            }.subscribe()

    }

    fun getFlights(paramsMap: Map<String, String>) {

        repo.getFlights(paramsMap)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                disposables.add(it)
                _isLoading.postValue(true)
            }
            .doOnSuccess {
                _flights.postValue(it)
            }.onErrorReturn {
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