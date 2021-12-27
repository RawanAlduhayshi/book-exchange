package com.rawanalduhyshi.bookexchange


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rawanalduhyshi.bookexchange.databinding.BookItemBinding
import com.rawanalduhyshi.bookexchange.network.BooksItem
import com.rawanalduhyshi.bookexchange.network.VolumeInfo
import androidx.recyclerview.widget.ListAdapter as ListAdapter


class BookGridAdapter:
    ListAdapter<BooksItem, BookGridAdapter.BookInfoViewHolder>(DiffCallback) {

    class BookInfoViewHolder(private var binding:
                              BookItemBinding
    ): RecyclerView.ViewHolder(binding.root){

//        val card: CardView = binding.cerdView

        fun bind(booksItems:BooksItem) {
            binding.bookUrl = booksItems
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookGridAdapter.BookInfoViewHolder {
        return BookInfoViewHolder(BookItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: BookInfoViewHolder, position: Int) {
        val resultsItems = getItem(position)
        holder.bind(resultsItems)

        holder.itemView.setOnClickListener {
            Log.e("TAG","id view :${position}")
//            val action = loginFragmentDirections.loginFragmentToListFragment(position)
//            holder.itemView.findNavController().navigate(action)

        }
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BooksItem>() {
            override fun areItemsTheSame(oldItem:BooksItem, newItem: BooksItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem:BooksItem, newItem: BooksItem): Boolean {
                return oldItem.kind == newItem.kind
            }
        }
    }
}