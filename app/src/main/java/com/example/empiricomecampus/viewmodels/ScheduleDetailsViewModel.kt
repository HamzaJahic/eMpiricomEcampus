package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.firebase.ScheduleFirebase
import com.example.empiricomecampus.models.Schedule
import kotlinx.coroutines.launch

class ScheduleDetailsViewModel(val schedule: Schedule) : ViewModel() {

    val scheduleHolder = schedule
    var time = String()
    var type = String()

    private val _navigateToSchedule = MutableLiveData<Boolean?>()
    val navigateToSchedule: LiveData<Boolean?>
        get() = _navigateToSchedule

    private val _showAlertDialog = MutableLiveData<Boolean?>()
    val showAlertDialog: LiveData<Boolean?>
        get() = _showAlertDialog

    init {
        time = "${schedule.startTime} - ${schedule.endTime}"
        type = when (schedule.type) {
            "P" -> "Predavanje"
            "AV" -> "Auditorne Vježbe"
            "LV" -> "Laboratorijske vježbe"
            "ZPI" -> "ZAVRŠNI PISMENI ISPIT"
            "ZUI" -> "ZAVRŠNI PISMENI ISPIT"
            "T-I" -> "TEST I"
            "T-II" -> "TEST II"
            else -> "Predavanje"

        }
    }

    fun deleteEntry() {
        val entryId = schedule.id.toString()
        viewModelScope.launch {
            ScheduleFirebase.databaseReference.child(entryId).removeValue()
            navigateToschedule()
        }

    }

    fun showAlertDialog() {
        _showAlertDialog.value = true
        doneShow()
    }

    fun doneShow() {
        _showAlertDialog.value = null
    }

    fun navigateToschedule() {
        _navigateToSchedule.value = true
        doneNavigate()
    }

    fun doneNavigate() {
        _navigateToSchedule.value = null
    }

}