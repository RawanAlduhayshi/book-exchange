package com.rawanalduhyshi.bookexchange


import android.app.ProgressDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.rawanalduhyshi.bookexchange.databinding.FragmentUserProfileBinding
import com.rawanalduhyshi.bookexchange.viewmodels.BookViewModel
import java.util.*


class UserProfileFragment : Fragment() {
    lateinit var imageUri: Uri
    val db = Firebase.firestore
    private val viewModel: BookViewModel by activityViewModels()
    lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentUserProfileBinding.inflate(inflater)
        val user_email = binding.userEmail
        val user_name = binding.userName
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val db = FirebaseFirestore.getInstance()
        val users = mutableListOf<FirebaseUser>()
        db.collection("users").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        users.add(dc.document.toObject(FirebaseUser::class.java))
                    }
                }
                val user = users.find { it.userId == userId }
                user_email.text = user?.email
                user_name.text = user?.fName
            }
//                val sharedPref = activity?.getSharedPreferences(
//                    getString(R.string.book_describtion), Context.MODE_PRIVATE)
        })
        return binding.root

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
            binding.userImage.setImageBitmap(bitmap)
        }
    }

    private fun addNewItem(imageTask: String) {

        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val AddedBook = hashMapOf(
            "imageUrl" to imageTask

        )
        val documentReference: DocumentReference = db.collection("books").document(userId)
        db.collection("users")
            .add(AddedBook)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(requireContext(), "Successfully book Added", Toast.LENGTH_SHORT)
                    .show()


            }

            .addOnFailureListener { e ->

            }

        FirebaseAuth.getInstance().currentUser?.let {

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

}

data class FirebaseUser(val fName: String = "", val email: String = "", val userId: String = "")
