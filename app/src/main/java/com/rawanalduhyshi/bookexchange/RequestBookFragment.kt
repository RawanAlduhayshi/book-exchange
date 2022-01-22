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
import com.rawanalduhyshi.bookexchange.databinding.FragmentRequestBookBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookRoomViewModel
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModelFactory
import java.util.*


class RequestBookFragment : Fragment() {
    private val requestViewModel: BookRoomViewModel by activityViewModels {
        BookViewModelFactory(
            (requireActivity().application as BookApplication).database.bookDao()
        )
    }
    lateinit var binding: FragmentRequestBookBinding
    lateinit var imageUri: Uri
    private val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestBookBinding.inflate(inflater)
        binding.select.setOnClickListener {
            selectImage()
        }



        binding.requestBook.setOnClickListener {
            uploadImage()
        }


        return binding.root
    }


    private fun addNewRequest(imageUrlTask: String) {

        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val AddedBook = hashMapOf(
            "name" to binding.requestBookName.text.toString(),
            "description" to binding.requestBookDescription.text.toString(),
            "author" to binding.requestBookAuthor.text.toString(),
            "bookId" to Math.random().toString(),
            "userId" to userId,
            "imageUrl" to imageUrlTask
        )
        val documentReference: DocumentReference =
            db.collection("requests").document(userId)
        db.collection("requests")
            .add(AddedBook)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(requireContext(), "Successfully book Added", Toast.LENGTH_SHORT)
                    .show()
                binding.requestBookName.setText("")
                binding.requestBookDescription.setText("")
                binding.requestBookAuthor.setText("")

            }

            .addOnFailureListener {

            }
        FirebaseAuth.getInstance().currentUser?.let {
            requestViewModel.addNewItem(
                binding.requestBookName.text.toString(),
                binding.requestBookDescription.text.toString()


            )
        }


    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = ("image/*")
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "choose book image"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && data != null) {

            imageUri = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)
            binding.image.setImageBitmap(bitmap)
        }
    }


    private fun uploadImage() {

        if (imageUri != null) {
            var pd = ProgressDialog(requireContext())
            pd.setTitle("uploading")
            pd.show()
            var formatter = SimpleDateFormat("yyyy_mm_dd_hh_mm", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)

            var storageReference = FirebaseStorage.getInstance().reference.child("books/$fileName")
            storageReference.putFile(imageUri)
                .addOnSuccessListener { imageTask ->
                    imageTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { imageDownloadTask ->
                        addNewRequest(imageDownloadTask.result.toString())

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
}




