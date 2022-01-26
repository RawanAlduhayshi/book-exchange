package com.rawanalduhyshi.bookexchange.adapters
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView

import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.rawanalduhyshi.bookexchange.FirebaseUser
import com.rawanalduhyshi.bookexchange.ListFragmentDirections
import com.rawanalduhyshi.bookexchange.R
import com.rawanalduhyshi.bookexchange.databinding.BookItemBinding
import com.rawanalduhyshi.bookexchange.data.BooksItem
import com.rawanalduhyshi.bookexchange.databinding.BookAddedItemBinding
import androidx.recyclerview.widget.ListAdapter as ListAdapter


class BookGridAdapter:
    ListAdapter<BooksItem, BookGridAdapter.BookInfoViewHolder>(DiffCallback) {

    class BookInfoViewHolder(private var binding:
                              BookItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        val image: CardView = itemView.findViewById(R.id.bookCard)

        fun bind(booksItems: BooksItem) {
            binding.bookUrl = booksItems
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookInfoViewHolder {
        return BookInfoViewHolder(BookItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: BookInfoViewHolder, position: Int) {
        val resultsItems = getItem(position)
        holder.bind(resultsItems)

        holder.image.setOnClickListener {

          val action = ListFragmentDirections.actionListFragmentToBookDetailsFragment(resultsItems?.id ?: "")
            holder.itemView.findNavController().navigate(action)


        }
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BooksItem>() {
            override fun areItemsTheSame(oldItem: BooksItem, newItem: BooksItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: BooksItem, newItem: BooksItem): Boolean {
                return oldItem.kind == newItem.kind
            }
        }
    }
}


