package com.rawanalduhyshi.bookexchange.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * from book ORDER BY name ASC")
    fun getItems(): Flow<List<Book>>

    @Query("SELECT * from book WHERE id = :id")
    fun getItem(id: Int): Flow<Book>

    @Insert()
    suspend fun insert(book: Book): Long
}