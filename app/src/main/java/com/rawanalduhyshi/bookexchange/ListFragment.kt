package com.rawanalduhyshi.bookexchange

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.rawanalduhyshi.bookexchange.adapters.BookGridAdapter
import com.rawanalduhyshi.bookexchange.databinding.FragmentAddBookBinding
import com.rawanalduhyshi.bookexchange.databinding.FragmentListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to

 */
class ListFragment : Fragment() {
    lateinit var login: TextView
    lateinit var binding: FragmentListBinding
    lateinit var loginButton: ImageView
    private val viewModel: BookViewModel by activityViewModels()
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
        binding?.lifecycleOwner =viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        binding?.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding?.recyclerView?.adapter = BookGridAdapter()
//        binding?.bookCard.setOnClickListener {
//
//          //  Toast.makeText(requireContext(), "i am here", Toast.LENGTH_SHORT).show()
//            val action = ListFragmentDirections.actionListFragmentToBookDetailsFragment(3)
//            findNavController().navigate(action)
//
//        }
        return binding?.root
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
//        val action = ListFragmentDirections.actionListFragmentToMainActivity()
//        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemId = item.itemId
        val action = ListFragmentDirections.actionListFragmentToListBookAddedFragment()
        findNavController().navigate(action)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()


    }

}