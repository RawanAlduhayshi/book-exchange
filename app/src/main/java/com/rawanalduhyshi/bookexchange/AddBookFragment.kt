package com.rawanalduhyshi.bookexchange

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.rawanalduhyshi.bookexchange.databinding.FragmentAddBookBinding
import com.rawanalduhyshi.bookexchange.databinding.FragmentListBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class AddBookFragment : Fragment() {
lateinit var binding:FragmentAddBookBinding
lateinit var imageUri: Uri
    private val viewModel: BookRoomViewModel by activityViewModels {
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
      binding= FragmentAddBookBinding.inflate(inflater)
//        binding.uploadBtn.setOnClickListener {
//            // selectImage()
//        }

        binding.upload.setOnClickListener {
            // uploadImage()
            addNewItem()
        }

        viewModel.successfullyAdded.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()
            binding.bookName.setText("")
            binding.bookDescri.setText("")
        })
        return binding?.root
    }
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.bookName.text.toString(),
            binding.bookDescri.text.toString()
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.bookName.text.toString(),
                binding.bookDescri.text.toString()
            )
        }
    }

//    private fun uploadImage() {
//        val progressDialog = ProgressDialog(requireContext())
//        progressDialog.setMessage("uploading")
//        progressDialog.setCancelable(false)
//        progressDialog.show()
//        val formatter = SimpleDateFormat("yyyy_mm_dd_HH_mm_SS", Locale.getDefault())
//        val now = Date()
//        val fileName = formatter.format(now)
//        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
//        storageReference.putFile(imageUri).addOnSuccessListener {
//            binding.imageFirebase.setImageURI(null)
//            Toast.makeText(activity, "successful", Toast.LENGTH_SHORT).show()
//            if (progressDialog.isShowing) progressDialog.dismiss()
//        }.addOnFailureListener {
//            if (progressDialog.isShowing) progressDialog.dismiss()
//            Toast.makeText(activity, "failure", Toast.LENGTH_SHORT).show()
//        }
//    }
//    private fun selectImage() {
//     var intent = Intent()
//        intent.type= "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent,100)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == 100 && requestCode == RESULT_OK){
//           imageUri = data?.data!!
//           binding.imageFirebase.setImageURI(imageUri)
//        }
//    }
}