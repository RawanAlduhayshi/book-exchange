package com.rawanalduhyshi.bookexchange

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rawanalduhyshi.bookexchange.network.BooksItem






@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data:List<BooksItem>?){
   val adapter = recyclerView.adapter as BookGridAdapter
   adapter.submitList(data)
}

@BindingAdapter("imagUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }}

    @BindingAdapter("BookApiStatus")
    fun bindStatus(statusImageView: ImageView,
                   status: BookApiStatus?){
        when (status){
            BookApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }
            BookApiStatus.DONE ->{
                statusImageView.visibility = View.VISIBLE


            }
            BookApiStatus.ERROR ->{
                statusImageView.visibility= View.GONE
                statusImageView.setImageResource(R.drawable.wifi_dawn)
            }
        }
}