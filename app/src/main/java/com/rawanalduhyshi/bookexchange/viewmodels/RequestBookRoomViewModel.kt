package com.rawanalduhyshi.bookexchange.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rawanalduhyshi.bookexchange.SingleLiveEvent
import com.rawanalduhyshi.bookexchange.data.Book
import com.rawanalduhyshi.bookexchange.data.BookDao
import kotlinx.coroutines.launch
import java.util.*

class RequestBookRoomViewModel(private val bookDao: BookDao) : ViewModel(){
    val allIBooksRequested: LiveData<List<Book>> = bookDao.getItems().asLiveData()
    val successfullyAdded = SingleLiveEvent<Void>()


    private fun requestNewBook(bookName: String, description: String){
      val newBook = RequestNewBookEntery(bookName,description)
        requestBook(newBook)

    }
    private fun requestBook(book: Book){
        viewModelScope.launch {
            bookDao.insert(book)
            successfullyAdded.call()
        }
    }
    fun isEntryValid(bookName: String,description:String): Boolean {
        if (bookName.isBlank() || description.isBlank())  {
            return false
        }
        return true
    }


    private fun RequestNewBookEntery(bookName: String, description: String):Book {
        return Book(
            id= Calendar.getInstance().time.time,
            name= bookName,
            description = description)
    }



}