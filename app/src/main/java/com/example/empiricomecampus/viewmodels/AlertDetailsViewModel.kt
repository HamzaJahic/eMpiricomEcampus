package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.empiricomecampus.firebase.AlertsFirebase
import com.example.empiricomecampus.models.Alert

class AlertDetailsViewModel(alert: Alert) : ViewModel() {

    private val _alert = MutableLiveData<Alert?>()
    val alert: LiveData<Alert?>
        get() = _alert

    private val _navigateToAlerts = MutableLiveData<Boolean?>()
    val navigateToAlerts: LiveData<Boolean?>
        get() = _navigateToAlerts

    private val _showAlertDialog = MutableLiveData<Boolean?>()
    val showAlertDialog: LiveData<Boolean?>
        get() = _showAlertDialog

    init {
        _alert.value = alert
    }

    fun deleteEntry() {
        val entryId = _alert.value?.id.toString()
        AlertsFirebase.databaseReference.child(entryId).removeValue()
        navigateToAlerts()
    }

    fun showAlertDialog() {
        _showAlertDialog.value = true
        doneShow()
    }

    fun doneShow() {
        _showAlertDialog.value = null
    }

    private fun navigateToAlerts() {
        _navigateToAlerts.value = true
        doneNavigate()
    }

    private fun doneNavigate() {
        _navigateToAlerts.value = null
    }
}