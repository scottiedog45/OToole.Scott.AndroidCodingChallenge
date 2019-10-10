package otoole.scott.androidtest

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import otoole.scott.androidtest.NetworkService.NetworkService
import retrofit2.HttpException
import java.io.IOException

class MainActivityViewModel (private val repo : NetworkService) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::MainActivityViewModel)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }

    var bookList = liveData(Dispatchers.IO) {
        try {
            val books = repo.getBookList()
            emit(books)
        } catch (e : HttpException) {
            handleError(e)
        } catch (e: IOException) {
            handleError(e)
        }
    }
}