package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.empiricomecampus.firebase.UsersFirebase
import com.example.empiricomecampus.utils.Globals.Companion.USER_COURSE
import com.example.empiricomecampus.utils.Globals.Companion.USER_ID
import com.example.empiricomecampus.utils.Globals.Companion.USER_LASTNAME
import com.example.empiricomecampus.utils.Globals.Companion.USER_NAME
import com.example.empiricomecampus.utils.Globals.Companion.USER_SEMESTER
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SettingsViewModel : ViewModel() {

    private val REQUESTCODE = 438

    private val _navigateToChangePass = MutableLiveData<Boolean?>()
    val navigateToChangePass: LiveData<Boolean?>
        get() = _navigateToChangePass

    private val _nameLastName = MutableLiveData<String?>()
    val nameLastName: LiveData<String?>
        get() = _nameLastName

    private val _semester = MutableLiveData<String?>()
    val semester: LiveData<String?>
        get() = _semester

    private val _course = MutableLiveData<String?>()
    val course: LiveData<String?>
        get() = _course

    private val _navigateToChangeImg = MutableLiveData<Int?>()
    val navigateToChangeImg: LiveData<Int?>
        get() = _navigateToChangeImg

    private val _logOut = MutableLiveData<Boolean?>()
    val logOut: LiveData<Boolean?>
        get() = _logOut

    private val _imgUri = MutableLiveData<String?>()
    val imgUri: LiveData<String?>
        get() = _imgUri

    init {
        _nameLastName.value =
            "${USER_NAME.value} ${USER_LASTNAME.value}"
        _semester.value = "Semestar ${USER_SEMESTER.value}"
        _course.value = USER_COURSE.value
        getImg()

    }

    fun startNavigateToChangeImg() {
        _navigateToChangeImg.value = REQUESTCODE
        doneNavigatingToChangeImg()
    }


    private fun doneNavigatingToChangeImg() {
        _navigateToChangeImg.value = null
    }

    fun startNavigateToChangePass() {
        _navigateToChangePass.value = true
        doneNavigatingToChangePass()
    }

    private fun doneNavigatingToChangePass() {
        _navigateToChangePass.value = null
    }

    fun logOut() {
        _logOut.value = true
        doneLogOut()
    }

    private fun doneLogOut() {
        _logOut.value = null
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


