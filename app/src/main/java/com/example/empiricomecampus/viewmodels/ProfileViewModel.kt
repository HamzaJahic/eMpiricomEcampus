package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.empiricomecampus.firebase.UsersFirebase
import com.example.empiricomecampus.models.Student

import com.firebase.ui.database.FirebaseRecyclerOptions

class ProfileViewModel : ViewModel() {

    private val _navigateToAddProfile = MutableLiveData<Boolean?>()
    val navigateToAddProfile: LiveData<Boolean?>
        get() = _navigateToAddProfile

    private val _navigateToProfileDetails = MutableLiveData<Student?>()
    val navigateToProfileDetails: LiveData<Student?>
        get() = _navigateToProfileDetails

    fun getOptions(lifecycleOwner: LifecycleOwner): FirebaseRecyclerOptions<Student> {
        return FirebaseRecyclerOptions.Builder<Student>()
            .setQuery(UsersFirebase.databaseReference, Student::class.java)
            .setLifecycleOwner(lifecycleOwner)
            .build()

    }

    fun startNavigateToProfileDetails(student: Student) {
        _navigateToProfileDetails.value = student
        doneNavigatingDetails()
    }

    private fun doneNavigatingDetails() {
        _navigateToProfileDetails.value = null
    }

    fun startNavigateToAddProfile() {
        _navigateToAddProfile.value = true
        doneNavigating()
    }

    private fun doneNavigating() {
        _navigateToAddProfile.value = null
    }
}