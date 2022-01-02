package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rawanalduhyshi.bookexchange.databinding.FragmentBookDetailsBinding


class BookDetailsFragment : Fragment() {
    private val bookDetailsViewModel: BookViewModel by activityViewModels()
    var book = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            book = it?.getString("id") ?: ""
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBookDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.detailsViewModel = bookDetailsViewModel
        val requestbtn = binding.requestBtn
        requestbtn.setOnClickListener {
            save()

        }
        return binding?.root

    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookDetailsViewModel.booksInfo(book)
    }

    fun save() {
//        var storageRef = images.
//        val mountainsRef = storageRef.child("mountains.jpg")
        val db = Firebase.firestore
        val user = hashMapOf(
            "first" to "BookRequests",
            "last" to "email"
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
        Toast.makeText(activity, "your request successful, wait for awoner", Toast.LENGTH_SHORT)
            .show()

    }
}