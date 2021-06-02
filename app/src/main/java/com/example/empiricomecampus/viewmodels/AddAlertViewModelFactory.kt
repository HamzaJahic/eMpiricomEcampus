package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddAlertViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAlertViewModel::class.java)) {
            return AddAlertViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}