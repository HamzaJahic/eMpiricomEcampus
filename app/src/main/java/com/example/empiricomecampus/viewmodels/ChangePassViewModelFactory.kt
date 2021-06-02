package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChangePassViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePassViewModel::class.java)) {
            return ChangePassViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}