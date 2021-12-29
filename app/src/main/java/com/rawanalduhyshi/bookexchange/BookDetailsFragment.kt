package com.rawanalduhyshi.bookexchange

import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rawanalduhyshi.bookexchange.databinding.FragmentBookDetailsBinding


class BookDetailsFragment : Fragment() {
    private val bookDetailsViewModel:BookViewModel by activityViewModels()
      var book =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate: i am here", )
        arguments.let {
            book =it!!.getInt("id")

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  binding = FragmentBookDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.detailsViewModel = bookDetailsViewModel

        return binding?.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookDetailsViewModel.booksInfo(book)

    }

}