package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rawanalduhyshi.bookexchange.adapters.BookGridAdapter
import com.rawanalduhyshi.bookexchange.databinding.FragmentListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private val viewModel:BookViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        var authFirebase = FirebaseAuth.getInstance().currentUser
//        var i = authFirebase?.email
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val binding= FragmentListBinding.inflate(inflater)
        binding?.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding?.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding?.recyclerView?.adapter = BookGridAdapter()
        binding?.btn.setOnClickListener {

          //  Toast.makeText(requireContext(), "i am here", Toast.LENGTH_SHORT).show()
            val action = ListFragmentDirections.actionListFragmentToBookDetailsFragment(3)
            findNavController().navigate(action)

        }
        return binding?.root
    }


}