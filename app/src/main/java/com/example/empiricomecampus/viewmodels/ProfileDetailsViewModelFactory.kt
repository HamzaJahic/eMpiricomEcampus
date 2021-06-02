package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.empiricomecampus.models.Student

class ProfileDetailsViewModelFactory(private val student: Student) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileDetailsViewModel::class.java)) {
            return ProfileDetailsViewModel(student) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}