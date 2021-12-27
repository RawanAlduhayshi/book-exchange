package com.rawanalduhyshi.bookexchange

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data:List<BooksItem>?){
    val adapter = recyclerView.adapter as BookAdapter
//    adapter.submitList(data)
}