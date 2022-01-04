package com.rawanalduhyshi.bookexchange

import android.app.Application
import com.rawanalduhyshi.bookexchange.data.BookRoomDatabase

class BookApplication : Application() {
    val database: BookRoomDatabase by lazy { BookRoomDatabase.getDatabase(this) }
}
