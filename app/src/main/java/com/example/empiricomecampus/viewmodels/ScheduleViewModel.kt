package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.empiricomecampus.firebase.ScheduleFirebase
import com.example.empiricomecampus.models.Schedule
import com.firebase.ui.database.FirebaseRecyclerOptions

class ScheduleViewModel(private val lifecycleOwner: LifecycleOwner) : ViewModel() {

    private val _navigateToAddSchedule = MutableLiveData<Boolean?>()
    val navigateToAddSchedule: LiveData<Boolean?>
        get() = _navigateToAddSchedule

    private val _navigateToScheduleDetails = MutableLiveData<Schedule?>()
    val navigateToScheduleDetails: LiveData<Schedule?>
        get() = _navigateToScheduleDetails


    fun setOptions(course: String?, semestar: String?): FirebaseRecyclerOptions<Schedule> {
        return FirebaseRecyclerOptions.Builder<Schedule>()
            .setQuery(
                ScheduleFirebase.databaseReference.child(course!!).child(semestar!!)
                    .orderByChild("sorting"), Schedule::class.java
            )
            .setLifecycleOwner(lifecycleOwner)
            .build()
    }

    fun startNavigateToScheduleDetails(schedule: Schedule) {
        _navigateToScheduleDetails.value = schedule
    }

    fun doneNavigateToScheduleDetails() {
        _navigateToScheduleDetails.value = null
    }


    fun startNavigateToAddSchedule() {
        _navigateToAddSchedule.value = true
    }

    fun doneNavigating() {
        _navigateToAddSchedule.value = null
    }
}