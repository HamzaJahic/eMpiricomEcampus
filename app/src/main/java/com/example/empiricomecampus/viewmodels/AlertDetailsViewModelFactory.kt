package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.empiricomecampus.models.Alert

class AlertDetailsViewModelFactory(private val alert: Alert) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlertDetailsViewModel::class.java)) {
            return AlertDetailsViewModel(alert) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}