package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rawanalduhyshi.bookexchange.adapters.MyRequestAdapter
import com.rawanalduhyshi.bookexchange.data.BookInfo
import com.rawanalduhyshi.bookexchange.databinding.BookListBookBinding
import com.rawanalduhyshi.bookexchange.databinding.FragmentListBookAddedBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MyRequestListFragment : Fragment() {

    private val booksCollectionRef = Firebase.firestore.collection("books")
    var books: BookInfo? = null
    private var bookInfoList = mutableListOf<BookInfo?>()
    private var itembinding: BookListBookBinding? = null

    private var _binding: FragmentListBookAddedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBookAddedBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        eventChangeListener()

        return binding.root
    }

    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        itembinding?.delete?.setOnClickListener {

//            bookInfoList.filter {
//                val bookQuery = booksCollectionRef.whereEqualTo(it.bookId,)
//            }


//            deleteBook()
        }
    }

    private fun eventChangeListener() {
        val db = FirebaseFirestore.getInstance()
        db.collection("requests").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        bookInfoList.add(dc.document.toObject(BookInfo::class.java))

                    }

                }
                val adapter =
                    MyRequestAdapter(bookInfoList.filter { it?.userId == companionUser.userId }
                        .toMutableList())
                binding.recyclerView.adapter = adapter
            }


        })
    }

    private fun deleteBook(book: BookInfo) = CoroutineScope(Dispatchers.IO).launch {

        val bookQuery = booksCollectionRef.whereEqualTo("name", book.name)
            .whereEqualTo("author", book.author).get().await()
        if (bookQuery.documents.isNotEmpty()) {
            for (document in bookQuery) {
                try {
                    booksCollectionRef.document(document.id).delete().await()
                    /*personCollectionRef.document(document.id).update(mapOf(
                       "firstName" to FieldValue.delete()
                   )).await()*/
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "No persons matched the query.", Toast.LENGTH_LONG).show()
            }
        }

    }
}