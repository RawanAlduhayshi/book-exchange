package com.rawanalduhyshi.bookexchange.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rawanalduhyshi.bookexchange.R
import com.rawanalduhyshi.bookexchange.data.Book
import com.rawanalduhyshi.bookexchange.data.BookInfo
import com.rawanalduhyshi.bookexchange.data.BooksItem
import com.rawanalduhyshi.bookexchange.databinding.BookListBookBinding


class BooksAddAdapter: ListAdapter<BookInfo, BooksAddAdapter.BooksViewHolder>(DiffCallback) {
    class BooksViewHolder(private var binding:
                          BookListBookBinding
    ): RecyclerView.ViewHolder(binding.root){

//        val nameBook:TextView = binding.itemBookName
//        val nameDescription: TextView= binding.itemBookDescription

        fun bind(booksItem: BookInfo) {
            binding.bookInfo = booksItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksAddAdapter.BooksViewHolder {
        return BooksViewHolder(
            BookListBookBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    override fun onBindViewHolder(holder:BooksAddAdapter.BooksViewHolder, position: Int) {
        val current = getItem(position)
        Log.e("TAG","books addgggged")
        holder.bind(current)


    }
     companion object DiffCallback  : DiffUtil.ItemCallback<BookInfo>(){

            override fun areItemsTheSame(oldItem:BookInfo, newItem: BookInfo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }







