package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rawanalduhyshi.bookexchange.databinding.FragmentAddBookBinding
import com.rawanalduhyshi.bookexchange.databinding.FragmentRequestBookBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory
import com.rawanalduhyshi.bookexchange.viewmodels.RequestBookRoomViewModel


class RequestBookFragment : Fragment() {
    lateinit var binding: FragmentRequestBookBinding
    val db = Firebase.firestore
    private val requestBookViewModel: RequestBookRoomViewModel by activityViewModels {
        BookViewModelFactory(
            (requireActivity().application as BookApplication).database.bookDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestBookBinding.inflate(inflater)
//        binding.uploadBtn.setOnClickListener {
//            // selectImage()
//        }

        binding.requestBook.setOnClickListener {
            // uploadImage()
            addNewRequest()
        }

//
//    private fun isEntryValid(): Boolean {
//        return requestBookViewModel.isRequestEntryValid(
//            binding.requestBookName.text.toString(),
//            binding.requestBookDescription.text.toString()
////            (FirebaseAuth.getInstance().currentUser?: "") as String
//        )
//    }

//    private fun requestNewBook() {
//        if (isEntryValid()) {
//            viewModel.addNewItem(
//                binding.bookName.text.toString(),
//                binding.bookDescri.text.toString()
////                (FirebaseAuth.getInstance().currentUser?: "") as String
//            )
//        }
//    }
return binding.root
    }
    private fun isEntryValid(): Boolean {
        return requestBookViewModel.isEntryValid(
            binding.requestBookName.text.toString(),
            binding.requestBookAuthor.text.toString()) }

    private fun addNewRequest() {
        if (isEntryValid()) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val AddedBook = hashMapOf(
                "name" to binding.requestBookName.text.toString(),
                "description" to binding.requestBookDescription.text.toString(),
                "author" to binding.requestBookAuthor.text.toString(),
                "bookId" to Math.random().toString(),
                "userId" to FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            val documentReference: DocumentReference = db.collection("books-requested").document(userId)
            db.collection("books-requested")
                .add(AddedBook)
                .addOnSuccessListener { documentReference ->

                }

                .addOnFailureListener { e ->

                }

        }
    }

    }


