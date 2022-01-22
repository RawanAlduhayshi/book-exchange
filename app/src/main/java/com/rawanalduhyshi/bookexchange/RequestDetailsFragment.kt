package com.rawanalduhyshi.bookexchange

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.rawanalduhyshi.bookexchange.data.BookInfo
import com.rawanalduhyshi.bookexchange.databinding.FragmentRequestDetailsBinding


class RequestDetailsFragment : Fragment() {

    var book:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            book = it?.getString("id") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRequestDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        var name =  binding.nameBook
        var author = binding.author
        var description = binding.description
        val bookId =book

        val db = FirebaseFirestore.getInstance()
        val books = mutableListOf<BookInfo>()
        db.collection("requests").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        books.add(dc.document.toObject(BookInfo::class.java))
                    }
                }
                val book = books.find { it.bookId == bookId}
                name.text= book?.name
                author.text= book?.author
                description.text= book?.description
            }



       })
//        val sharedPref: SharedPreferences? = activity?.getPreferences(
//            Context.MODE_PRIVATE)
//        var edt: SharedPreferences.Editor? = sharedPref?.edit()
//        edt?.putString("user_id", FirebaseAuth.getInstance().currentUser?.uid.toString())
//        edt?.putString("email", FirebaseAuth.getInstance().currentUser?.email.toString())
//        edt?.putString("full_name", FirebaseAuth.getInstance().currentUser?.displayName.toString())
//        edt?.commit()

        return binding.root
    }
    }


