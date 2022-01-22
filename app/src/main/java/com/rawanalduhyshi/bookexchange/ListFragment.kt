package com.rawanalduhyshi.bookexchange


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView


import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.rawanalduhyshi.bookexchange.adapters.BookGridAdapter
import com.rawanalduhyshi.bookexchange.adapters.BooksAddedAdapter

import com.rawanalduhyshi.bookexchange.adapters.RequestsAdapter
import com.rawanalduhyshi.bookexchange.data.BookInfo
import com.rawanalduhyshi.bookexchange.databinding.FragmentListBinding


import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModel


class ListFragment : Fragment() {
//    val imageRef = FirebaseFirestore.getInstance()

    lateinit var login: TextView
    private var bookInfoList = mutableListOf<BookInfo?>()
    private var bookInfoRequest = mutableListOf<BookInfo?>()
    lateinit var binding: FragmentListBinding
    lateinit var loginButton: ImageView
    private val bookViewModel: BookViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        login = binding.login
        loginButton = binding.loginIcon
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = bookViewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding.recyclerView.adapter = BookGridAdapter()

        eventChangeListener()
        eventRequestChangeListener()
        return binding.root
    }

    private fun eventChangeListener() {
        val db = FirebaseFirestore.getInstance()
        db.collection("books").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                val adapter = BooksAddedAdapter(bookInfoList)
                binding.bookAddedRecyclerview.adapter = adapter
            }
        })
    }

    private fun eventRequestChangeListener() {
        val db = FirebaseFirestore.getInstance()
        db.collection("requests").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        bookInfoRequest.add(dc.document.toObject(BookInfo::class.java))

                    }

                }
                val adapter = RequestsAdapter(bookInfoRequest)
                binding.bookRequestedRecyclerview.adapter = adapter
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null) {

            login.text = "SignOut"
            loginButton.setOnClickListener {
                AuthUI.getInstance().signOut(requireContext())
                    .addOnCompleteListener { requireActivity().finish() }
            }
        } else {
            login.text = "login/sign Up"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemTitle = item.title
        if (itemTitle == "Added Books") {
            val action = ListFragmentDirections.actionListFragmentToListBookAddedFragment()
            findNavController().navigate(action)
            return true
        } else {
            if (itemTitle == "Requested Books") {

                val action = ListFragmentDirections.actionListFragmentToMyRequestListFragment()
                findNavController().navigate(action)
                return true
            } else {
                return false
            }

            return false
        }


    }

}