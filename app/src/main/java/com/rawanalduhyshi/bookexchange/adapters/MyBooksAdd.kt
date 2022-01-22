package com.rawanalduhyshi.bookexchange.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rawanalduhyshi.bookexchange.R
import com.rawanalduhyshi.bookexchange.data.BookInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MyBooksAdd(
    private val booksList: MutableList<BookInfo?>,
    val onRemoveClicked: (BookInfo) -> Unit
) :
    RecyclerView.Adapter<MyBooksAdd.MyViewHolder>() {
    private val booksCollectionRef = Firebase.firestore.collection("books")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBooksAdd.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.book_list_book, parent, false)
        return MyBooksAdd.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyBooksAdd.MyViewHolder, position: Int) {
        val book: BookInfo? = booksList[position]
        holder.book_name.text = book?.name
        holder.book_author.text = book?.author
        holder.delete.setOnClickListener {

            Log.e("TAG", "onBindViewHolder: 123")
            if (book != null) {
                Log.e("TAG", "onBindViewHolder: 1231234")
                deleteBook(book = book)
            }
        }


    }

    private fun deleteBook(book: BookInfo) {

        CoroutineScope(Dispatchers.IO).launch {

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
                        withContext(Dispatchers.Main) {
//                        Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val book_name: TextView = itemView.findViewById(R.id.item_book_name)
        val book_author: TextView = itemView.findViewById(R.id.bookAuthor)
        val delete: ImageView = itemView.findViewById(R.id.delete)
    }
}