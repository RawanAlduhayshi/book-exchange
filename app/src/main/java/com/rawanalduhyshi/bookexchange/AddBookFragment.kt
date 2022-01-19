package com.rawanalduhyshi.bookexchange

import android.app.ProgressDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.firebase.storage.FirebaseStorage
import com.rawanalduhyshi.bookexchange.databinding.FragmentAddBookBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory
import java.util.*


class AddBookFragment : Fragment() {
    lateinit var binding: FragmentAddBookBinding
    lateinit var imageUri: Uri
    private val db = Firebase.firestore

    private val viewModel: BookRoomViewModel by activityViewModels {
        BookViewModelFactory(
            (requireActivity().application as BookApplication).database.bookDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBookBinding.inflate(inflater)

        binding.choose.setOnClickListener {
            selectImage()
        }
        binding.upload.setOnClickListener {
            uploadImage()


        }

//        viewModel.successfullyAdded.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            Toast.makeText(requireContext(), "Successfully book Added", Toast.LENGTH_SHORT).show()
//            binding.bookName.setText("")
//            binding.bookDescribe.setText("")
//             binding.bookAuthor.setText("")
//
//        })
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.bookName.text.toString(),
            binding.bookDescribtion.text.toString()


        )
    }

    private fun addNewItem(imageTask: String) {
        if (isEntryValid()) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val AddedBook = hashMapOf(
                "name" to binding.bookName.text.toString(),
                "description" to binding.bookDescribtion.text.toString(),
                "author" to binding.bookAuthor.toString(),
                "bookId" to Math.random().toString(),
                "userId" to FirebaseAuth.getInstance().currentUser?.uid.toString(),
                "imageUrl" to imageTask

            )
            val documentReference: DocumentReference = db.collection("books").document(userId)
            db.collection("books")
                .add(AddedBook)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Successfully book Added", Toast.LENGTH_SHORT)
                        .show()
                    binding.bookName.setText("")
                    binding.bookDescribtion.setText("")
                    binding.bookAuthor.setText("")

                }

                .addOnFailureListener {

                }

            FirebaseAuth.getInstance().currentUser?.let {
                viewModel.addNewItem(
                    binding.bookName.text.toString(),
                    binding.bookDescribtion.text.toString()


                )
            }

        }
    }


    private fun uploadImage() {
        if (imageUri != null) {
            var pd = ProgressDialog(requireContext())
            pd.setTitle("uploading")
            pd.show()
            var formatter = SimpleDateFormat("yyyy_mm_dd_hh_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)


            var storageReference = FirebaseStorage.getInstance().reference.child("images/$fileName")
            storageReference.putFile(imageUri)
                .addOnSuccessListener { imageTask ->
                    imageTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { imageDownloadTask ->

                        addNewItem(imageDownloadTask.result.toString())

                    }
                    pd.dismiss()
                    Toast.makeText(activity, "File uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { p0 ->
                    pd.dismiss()
                    Toast.makeText(activity, p0.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { p0 ->
                    var progress: Double = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                    pd.setMessage("uploaded $progress.toInt())*")
                }

        }
    }

    private fun selectImage() {
        var intent = Intent()
        intent.type = ("image/*")
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "choose book image"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && data != null) {
            imageUri = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)
            binding.chooseBookImage.setImageBitmap(bitmap)
        }
    }
}