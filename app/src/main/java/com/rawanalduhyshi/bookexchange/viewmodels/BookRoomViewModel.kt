package com.rawanalduhyshi.bookexchange.viewmodels

import androidx.lifecycle.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rawanalduhyshi.bookexchange.SingleLiveEvent
import com.rawanalduhyshi.bookexchange.data.Book
import com.rawanalduhyshi.bookexchange.data.BookDao
import com.rawanalduhyshi.bookexchange.data.BookInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class BookRoomViewModel(private val bookDao: BookDao) : ViewModel() {
    val allItems: LiveData<List<Book>> = bookDao.getItems().asLiveData()
    private val booksCollectionRef = Firebase.firestore.collection("books")
    private val successfullyAdded = SingleLiveEvent<Void>()

    fun addNewItem(itemName: String, description: String) {
        val newBook = addNewItemEntry(itemName, description)
        insertItem(newBook)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(book: Book) {
        viewModelScope.launch {
            bookDao.insert(book)
            successfullyAdded.call()
        }
    }

    fun isEntryValid(bookName: String, description: String,author:String,image:String): Boolean {
        if (bookName.isBlank() || description.isBlank()||author.isBlank()||image.isBlank()) {
            return false
        }
        return true
    }

    private fun addNewItemEntry(bookName: String, description: String): Book {
        return Book(
            id = Calendar.getInstance().time.time,
            name = bookName,
            description = description
        )
    }

    private fun deleteBook(book: BookInfo) = CoroutineScope(Dispatchers.IO).launch {

        val bookQuery = booksCollectionRef.whereEqualTo("name", book.name)
            .whereEqualTo("author", book.author).get().await()
        if (bookQuery.documents.isNotEmpty()) {
            for (document in bookQuery) {
                try {
                    booksCollectionRef.document(document.id).delete().await()
                    /*personCollectionRef.document(document.id).update(mapOf(
                       "firstName" to FieldValue.delete()
                   )).await()*/
                } catch (e: Exception) {

                }
            }
        } else {

        }

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