//package com.rawanalduhyshi.bookexchange
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.ListAdapter
//import androidx.recyclerview.widget.DiffUtil
////import com.rawanalduhyshi.bookexchange.databinding.BookItemBinding
//
//class BookAdapter:ListAdapter<BooksItem,BookAdapter.BookInfoViewHolder>(DiffCallback) {
//
////
////    override fun onCreateViewHolder(
////        parent: ViewGroup,
////        viewType: Int
////    ): BookAdapter.BookInfoViewHolder {
////
////    }
//
//    class BookInfoViewHolder {
//        fun bind(resultsItems: BooksItem) {
////            binding.movieInformtion = resultsItems
////            binding.executePendingBindings()
//        }
//
//    }
//
//
//    companion object DiffCallback : DiffUtil.ItemCallback<BooksItem>() {
//        override fun areItemsTheSame(oldItem: BooksItem, newItem: BooksItem): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: BooksItem, newItem: BooksItem): Boolean {
////            return oldItem.posterPath == newItem.posterPath
//            return true
//        }
//
//    }
//}