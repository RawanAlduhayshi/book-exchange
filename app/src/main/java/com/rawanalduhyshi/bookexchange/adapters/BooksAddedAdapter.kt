package com.rawanalduhyshi.bookexchange.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rawanalduhyshi.bookexchange.ListFragmentDirections
import com.rawanalduhyshi.bookexchange.R
import com.rawanalduhyshi.bookexchange.data.BookInfo


class BooksAddedAdapter(private val booksList: MutableList<BookInfo?>) :
    RecyclerView.Adapter<BooksAddedAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.book_added_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val url = booksList[position]?.imageUrl
        Glide.with(holder.itemView.context).load(url)
            .into(holder.itemView.findViewById(R.id.book_added_item))
        holder.image.setOnClickListener {
            val bookId = booksList[position]?.bookId
            val action =
                ListFragmentDirections.actionListFragmentToBookAddedDetailsFragment(bookId ?: "")
            holder.itemView.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return booksList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.book_added_item)

    }
}