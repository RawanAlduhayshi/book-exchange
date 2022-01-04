package com.rawanalduhyshi.bookexchange

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rawanalduhyshi.bookexchange.adapters.BooksAddAdapter
import com.rawanalduhyshi.bookexchange.databinding.FragmentListBookAddedBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory


class ListBookAddedFragment : Fragment() {
    private val bookviewModel: BookRoomViewModel by activityViewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).database.bookDao()
        )
    }
    val adapter = BooksAddAdapter()
    private var _binding: FragmentListBookAddedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBookAddedBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val adapter = BooksAddListAdapter()
//        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
//
//        binding.recyclerView.adapter =adapter

        bookviewModel.allItems.observe(this.viewLifecycleOwner) { books ->
            books.let {
                adapter.submitList(it)
            }

        }
    }
}


