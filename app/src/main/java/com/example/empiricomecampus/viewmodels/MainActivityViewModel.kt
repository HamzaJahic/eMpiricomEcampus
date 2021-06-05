package com.example.empiricomecampus.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.firebase.UsersFirebase
import com.example.empiricomecampus.utils.Globals.ADMIN
import com.example.empiricomecampus.utils.Globals.USER_COURSE
import com.example.empiricomecampus.utils.Globals.USER_ID
import com.example.empiricomecampus.utils.Globals.USER_LASTNAME
import com.example.empiricomecampus.utils.Globals.USER_NAME
import com.example.empiricomecampus.utils.Globals.USER_SEMESTER
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Context) : ViewModel() {

    private val _imgUri = MutableLiveData<String?>()
    val imgUri: LiveData<String?>
        get() = _imgUri

    private val idUser =
        context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE).getString("id", null)
    val admin =
        context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE).getBoolean("admin", false)


    init {
        USER_ID.value = idUser
        ADMIN.value = admin
        if (admin) {
            adminProfile()
        } else retrieveUserInfo(idUser!!)

        getImg()
    }

    //povlacanje informacija o korisniku
    private fun retrieveUserInfo(idUser: String) {

        val userRefrence = UsersFirebase.databaseReference.child(idUser)

        viewModelScope.launch {
            userRefrence.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    USER_NAME.value = snapshot.child("name").value.toString()
                    USER_LASTNAME.value = snapshot.child("lastName").value.toString()
                    USER_SEMESTER.value = snapshot.child("semester").value.toString()
                    USER_COURSE.value = snapshot.child("course").value.toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            )
        }

    }

    private fun adminProfile() {
        USER_NAME.value = "Admin"
        USER_LASTNAME.value = "Admin"
        USER_SEMESTER.value = "I"
        USER_COURSE.value = "ADMIN"
    }


    private fun getImg() {

        val img = UsersFirebase.databaseReference.child(USER_ID.value.toString())

        img.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.let {
                    _imgUri.value = it.child("img").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }


}