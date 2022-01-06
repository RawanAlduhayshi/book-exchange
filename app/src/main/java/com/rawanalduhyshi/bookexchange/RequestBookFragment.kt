package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rawanalduhyshi.bookexchange.databinding.FragmentRequestBookBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory
import com.rawanalduhyshi.bookexchange.viewmodels.RequestBookRoomViewModel


class RequestBookFragment : Fragment() {
    lateinit var binding:FragmentRequestBookBinding
    private val requestBookViewModel: RequestBookRoomViewModel by activityViewModels {
        BookViewModelFactory(
            (requireActivity().application as BookApplication).database.bookDao()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//          binding= FragmentRequestBookBinding.inflate(inflater)
//          binding.requestBook.setOnEditorActionListener{
//               requestNewBook()
//        }
//        return binding.root
//    }

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

}