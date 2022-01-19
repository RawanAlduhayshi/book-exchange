package com.rawanalduhyshi.bookexchange.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rawanalduhyshi.bookexchange.data.BooksItem
import com.rawanalduhyshi.bookexchange.network.BookApi
import kotlinx.coroutines.launch

enum class BookApiStatus { LOADING, ERROR, DONE }

class BookViewModel : ViewModel() {
    private val _status = MutableLiveData<BookApiStatus>()
    val status: LiveData<BookApiStatus> = _status
    private val _bookInfo = MutableLiveData<List<BooksItem?>>()
    val bookInfo: LiveData<List<BooksItem?>> = _bookInfo
    val bookName = MutableLiveData<String>()
    val bookDescribtion = MutableLiveData<String>()
    val bookSubtitle = MutableLiveData<String>()
    val bookImage = MutableLiveData<String>()


    init {
        getBooksInfo()
    }

    private fun getBooksInfo() {
        viewModelScope.launch {
            _status.value = BookApiStatus.LOADING
            try {
                _bookInfo.value = BookApi.retrofitServer.getBooksInfo(1).items!!
                _status.value = BookApiStatus.DONE
            } catch (e: Exception) {
                _status.value = BookApiStatus.ERROR
                _bookInfo.value = listOf()
            }
        }
    }

    fun booksInfo(id: String) {
        viewModelScope.launch {
            val item = BookApi.retrofitServer.getBooksWithVolumeId(id)

            bookName.value = item.volumeInfo?.title ?: "Empty Title"
            bookSubtitle.value = item.volumeInfo?.subtitle ?: "Empty Subtitle"
            bookDescribtion.value = item.volumeInfo?.description ?: "Empty Description"
            try {

                bookImage.value = item.volumeInfo?.imageLinks?.thumbnail ?: "Empty Image"
            } catch (e: Exception) {
                Log.e("TAG", error(e))
            }
        }


    }

}