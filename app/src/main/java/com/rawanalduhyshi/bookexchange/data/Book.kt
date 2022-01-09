package com.rawanalduhyshi.bookexchange.data

import android.app.ActivityManager
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val description: String



    )

