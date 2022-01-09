package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.rawanalduhyshi.bookexchange.data.Book
import com.rawanalduhyshi.bookexchange.data.BookInfo
import com.rawanalduhyshi.bookexchange.databinding.FragmentUserProfileBinding
import javax.annotation.Nullable


class UserProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val binding = FragmentUserProfileBinding.inflate(inflater)
        val user_email = binding.userEmail
        val user_name = binding.userName
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        // val documentReference: DocumentReference =
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
                    val user = users.find { it.userId == userId}
                    user_email.text = user?.email
                    user_name.text = user?.fName
                }
            })
        return binding.root

    }

}

data class FirebaseUser(val fName: String = "", val email: String = "", val userId: String = "")
