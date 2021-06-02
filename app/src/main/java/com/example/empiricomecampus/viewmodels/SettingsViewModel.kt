package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.empiricomecampus.firebase.UsersFirebase
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

    private val _semestar = MutableLiveData<String?>()
    val semestar: LiveData<String?>
        get() = _semestar

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
            "${MainActivityViewModel.name.value} ${MainActivityViewModel.lastName.value}"
        _semestar.value = "Semestar ${MainActivityViewModel.semester.value}"

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

        val img = UsersFirebase.databaseReference.child(MainActivityViewModel.id.value.toString())

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


