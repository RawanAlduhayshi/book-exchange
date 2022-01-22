package com.rawanalduhyshi.bookexchange.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView


import com.rawanalduhyshi.bookexchange.R
import com.rawanalduhyshi.bookexchange.data.BookInfo

class MyRequestAdapter(private val booksList: MutableList<BookInfo?>) :
    RecyclerView.Adapter<MyRequestAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRequestAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.book_list_book, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyRequestAdapter.MyViewHolder, position: Int) {
        val book: BookInfo? = booksList[position]
        holder.book_name.text = book?.name
        holder.book_author.text = book?.author
//       holder.delete.

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