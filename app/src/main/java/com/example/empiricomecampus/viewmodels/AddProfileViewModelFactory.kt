package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddProfileViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProfileViewModel::class.java)) {
            return AddProfileViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}