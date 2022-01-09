package com.rawanalduhyshi.bookexchange.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rawanalduhyshi.bookexchange.ListFragmentDirections
import com.rawanalduhyshi.bookexchange.databinding.BookItemBinding
import com.rawanalduhyshi.bookexchange.data.BooksItem
import androidx.recyclerview.widget.ListAdapter as ListAdapter


class BookGridAdapter:
    ListAdapter<BooksItem, BookGridAdapter.BookInfoViewHolder>(DiffCallback) {

    class BookInfoViewHolder(private var binding:
                              BookItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        val card: CardView = binding.bookCard
        val bookImage: ImageView = binding.bookImage

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

        holder.card.setOnClickListener {

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