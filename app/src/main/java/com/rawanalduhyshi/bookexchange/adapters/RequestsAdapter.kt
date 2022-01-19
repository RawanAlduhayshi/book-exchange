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

class RequestsAdapter(private val requestsList: MutableList<BookInfo?>) :
    RecyclerView.Adapter<RequestsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.book_added_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val url = requestsList[position]?.imageUrl
        Glide.with(holder.itemView.context).load(url)
            .into(holder.itemView.findViewById(R.id.book_added_item))
        holder.image.setOnClickListener {
            val bookId = requestsList[position]?.bookId
            val action =
                ListFragmentDirections.actionListFragmentToRequestDetailsFragment(bookId ?: "")
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return requestsList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.book_added_item)

    }
}