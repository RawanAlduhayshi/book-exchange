package com.rawanalduhyshi.bookexchange


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rawanalduhyshi.bookexchange.network.BookApi
import com.rawanalduhyshi.bookexchange.data.BooksItem
import kotlinx.coroutines.launch

enum class BookApiStatus {LOADING, ERROR, DONE}
class BookViewModel: ViewModel() {
    private val _status = MutableLiveData<BookApiStatus>()
    val status: LiveData<BookApiStatus> = _status
    private val _bookInfo = MutableLiveData<List<BooksItem?>>()
    val bookInfo: LiveData<List<BooksItem?>> = _bookInfo
    val bookName = MutableLiveData<String>()
    val bookDescribtion = MutableLiveData<String>()
    val bookSubtitle = MutableLiveData<String>()
    val bookImage =MutableLiveData<String>()



    init {
        getBooksInfo()
    }
    private fun getBooksInfo() {
        viewModelScope.launch {
            _status.value = BookApiStatus.LOADING
            try {
                _bookInfo.value = BookApi.retrofitServer.getBooksInfo().items
                _status.value = BookApiStatus.DONE
            } catch (e: Exception) {
                _status.value = BookApiStatus.ERROR
                _bookInfo.value = listOf()
            }
        }
    }

    fun booksInfo(position:Int){
        val item= _bookInfo.value?.get(position)
       bookName.value = item?.volumeInfo?.title
        bookDescribtion.value=item?.volumeInfo?.description
       bookSubtitle.value = item?.volumeInfo?.subtitle
        bookImage.value = item?.volumeInfo?.imageLinks?.thumbnail
//        rating.value = item?.voteAverage



    }
}