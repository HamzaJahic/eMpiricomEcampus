package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddScheduleViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddScheduleViewModel::class.java)) {
            return AddScheduleViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}