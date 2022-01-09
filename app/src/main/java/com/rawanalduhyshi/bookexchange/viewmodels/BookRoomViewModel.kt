package com.rawanalduhyshi.bookexchange.viewmodels

import androidx.lifecycle.*

import com.rawanalduhyshi.bookexchange.SingleLiveEvent

import com.rawanalduhyshi.bookexchange.data.Book
import com.rawanalduhyshi.bookexchange.data.BookDao
import com.rawanalduhyshi.bookexchange.data.BookInfo
import kotlinx.coroutines.launch
import java.util.*

class BookRoomViewModel(private val bookDao: BookDao) : ViewModel()  {
    val allItems: LiveData<List<Book>> = bookDao.getItems().asLiveData()

    val successfullyAdded = SingleLiveEvent<Void>()

    fun addNewItem(itemName: String, description: String) {
        val newBook = addNewItemEntry(itemName, description)
        insertItem(newBook)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(book: Book){
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
    private fun addNewItemEntry(bookName: String, description: String):Book {
        return Book(
            id= Calendar.getInstance().time.time,
            name= bookName,
            description = description)
    }

}

class BookViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookRoomViewModel(bookDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}