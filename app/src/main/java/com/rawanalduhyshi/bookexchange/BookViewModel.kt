package com.rawanalduhyshi.bookexchange


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rawanalduhyshi.bookexchange.network.BookApi
import com.rawanalduhyshi.bookexchange.network.BooksItem
import kotlinx.coroutines.launch

enum class BookApiStatus {LOADING, ERROR, DONE}
class BookViewModel: ViewModel() {
    private val _status = MutableLiveData<BookApiStatus>()
    val status: LiveData<BookApiStatus> = _status
    private val _bookInfo = MutableLiveData<List<BooksItem?>>()
    val bookInfo: LiveData<List<BooksItem?>> = _bookInfo

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

//    fun booksInfo(position:Int){
//        val item= _bookInfo.value?.get(position)
//        Log.e("TAG","idMovie:${item?.id}")
//        Log.e("TAG","id view:${position}")
//        _movieName.value = item?.originalTitle
//        moviePoster.value=item?.posterPath
//        overView.value = item?.overview
//        rating.value = item?.voteAverage
//
//        Log.e("TAG","movie picture:${moviePoster}")


//    }
}