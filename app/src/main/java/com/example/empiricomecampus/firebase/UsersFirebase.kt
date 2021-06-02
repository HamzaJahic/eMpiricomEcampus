package com.example.empiricomecampus.firebase


import com.example.empiricomecampus.models.Student
import com.example.empiricomecampus.utils.Globals.Companion.USER_ID
import com.example.empiricomecampus.viewmodels.MainActivityViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsersFirebase {
    companion object {
        val databaseReference: DatabaseReference = Firebase.database.reference.child("Users")
        fun uploadData(key: String, entry: Student) {
            databaseReference.child(key).setValue(entry)

        }

        fun changePass(currentPass: String, newPass: String, newPassAgain: String) {
            val user = databaseReference.child(USER_ID.value.toString())

            user.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.let {
                        if (currentPass == it.child("password").value
                            || newPass == newPassAgain
                        ) {
                            user.child("password").setValue(newPass)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        }

    }

}