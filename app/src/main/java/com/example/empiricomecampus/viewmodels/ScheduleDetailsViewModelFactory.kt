package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.empiricomecampus.models.Schedule

class ScheduleDetailsViewModelFactory(private val schedule: Schedule) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleDetailsViewModel::class.java)) {
            return ScheduleDetailsViewModel(schedule) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}