package com.example.empiricomecampus.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.empiricomecampus.firebase.UsersFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Context) : ViewModel() {

    companion object {

        private val _id = MutableLiveData<String>()
        val id: LiveData<String>
            get() = _id
        private val _name = MutableLiveData<String>()
        val name: LiveData<String>
            get() = _name
        private val _lastName = MutableLiveData<String>()
        val lastName: LiveData<String>
            get() = _lastName
        private val _semester = MutableLiveData<String>()
        val semester: LiveData<String>
            get() = _semester
        private val _course = MutableLiveData<String>()
        val course: LiveData<String>
            get() = _course

        var _admin = MutableLiveData<Boolean?>()
    }

    private val _imgUri = MutableLiveData<String?>()
    val imgUri: LiveData<String?>
        get() = _imgUri

    private val idUser =
        context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE).getString("id", null)
    val admin =
        context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE).getBoolean("admin", false)


    init {
        _id.value = idUser
        _admin.value = admin
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
                    _name.value = snapshot.child("name").value.toString()
                    _lastName.value = snapshot.child("lastName").value.toString()
                    _semester.value = snapshot.child("semester").value.toString()
                    _course.value = snapshot.child("course").value.toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            )
        }

    }


    private fun adminProfile() {
        _name.value = "Admin"
        _lastName.value = "Admin"
        _semester.value = "I"
        _course.value = "ADMIN"
    }


    private fun getImg() {

        val img = UsersFirebase.databaseReference.child(id.value.toString())

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