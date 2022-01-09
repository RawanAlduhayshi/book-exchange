package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.rawanalduhyshi.bookexchange.adapters.MyBooksAdd
import com.rawanalduhyshi.bookexchange.data.BookInfo
import com.rawanalduhyshi.bookexchange.databinding.FragmentListBookAddedBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory


class ListBookAddedFragment : Fragment() {

    lateinit var adapter: MyBooksAdd
    var books: BookInfo? = null
    private var bookInfoList = mutableListOf<BookInfo?>()
    private val bookviewModel: BookRoomViewModel by activityViewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).database.bookDao()
        )
    }

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

    private fun eventChangeListener() {
        val db = FirebaseFirestore.getInstance()
        db.collection("books").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        bookInfoList.add(dc.document.toObject(BookInfo::class.java))

                    }

                }
                val adapter = MyBooksAdd(bookInfoList.filter { it?.userId == FirebaseAuth.getInstance().currentUser?.uid }.toMutableList())
                binding.recyclerView.adapter = adapter
            }
        })
    }

}

//
//
//        binding.recyclerView.adapter =adapter


//        val userId:String, val bookId:String,
//        val description: String
//        documentReference.get().addOnSuccessListener {documentSnapchot ->
//         var i =0
//          while(documentSnapchot!= null){
//            books?.name = documentSnapchot?.data?.get("name").toString()
//              books?.author = documentSnapchot?.data?.get("author").toString()
//              books?.description = documentSnapchot?.data?.get("description").toString()
//              books?.bookId = documentSnapchot?.data?.get("bookId").toString()
//
//
//
//
//        bookInfoList.observe(this.viewLifecycleOwner) { books ->
//            books.let {
//                adapter.submitList(it)
//            }
//
//        }



