package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rawanalduhyshi.bookexchange.databinding.FragmentAddBookBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory


class AddBookFragment : Fragment() {
    lateinit var binding: FragmentAddBookBinding

    val db = Firebase.firestore

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
        binding = FragmentAddBookBinding.inflate(inflater)
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
//            binding.bookAuthor.setText("")

        })
        return binding?.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.bookName.text.toString(),
            binding.bookDescri.text.toString()
//            binding.bookAuthor.text.toString()
//            (FirebaseAuth.getInstance().currentUser?: "") as String
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val AddedBook = hashMapOf(
                "name" to binding.bookName.text.toString(),
                "description" to binding.bookDescri.text.toString(),
                "author" to binding.bookAuthor.text.toString(),
                "bookId" to Math.random().toString(),
                "userId" to FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            val documentReference: DocumentReference = db.collection("books").document(userId)
            db.collection("books")
                .add(AddedBook)
                .addOnSuccessListener { documentReference ->

                }

                .addOnFailureListener { e ->

                }

            FirebaseAuth.getInstance().currentUser?.let {
                viewModel.addNewItem(
                    binding.bookName.text.toString(),
                    binding.bookDescri.text.toString()

//                    it.uid,

                )
            }

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