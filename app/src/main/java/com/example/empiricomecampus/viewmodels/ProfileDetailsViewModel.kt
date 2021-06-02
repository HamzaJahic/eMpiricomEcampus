package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.firebase.UsersFirebase
import com.example.empiricomecampus.models.Student
import kotlinx.coroutines.launch

class ProfileDetailsViewModel(private val student: Student) : ViewModel() {


    val studentHolder = student

    private val _navigateToProfiles = MutableLiveData<Boolean?>()
    val navigateToProfiles: LiveData<Boolean?>
        get() = _navigateToProfiles

    private val _showAlertDialog = MutableLiveData<Boolean?>()
    val showAlertDialog: LiveData<Boolean?>
        get() = _showAlertDialog

    fun deleteEntry() {
        val entryId = student.id.toString()
        viewModelScope.launch {
            UsersFirebase.databaseReference.child(entryId).removeValue()
            navigateToProfiles()
        }

    }

    fun showAlertDialog() {
        _showAlertDialog.value = true
        doneShow()
    }

    private fun doneShow() {
        _showAlertDialog.value = null
    }

    private fun navigateToProfiles() {
        _navigateToProfiles.value = true
        doneNavigate()
    }

    private fun doneNavigate() {
        _navigateToProfiles.value = null
    }

}