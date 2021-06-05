package com.example.empiricomecampus.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.empiricomecampus.firebase.AlertsFirebase
import com.example.empiricomecampus.models.Alert
import com.firebase.ui.database.FirebaseRecyclerOptions


class AlertsViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {

    private val _navigateToAddAlert = MutableLiveData<Boolean?>()
    val navigateToAddAlert: LiveData<Boolean?>
        get() = _navigateToAddAlert

    private val _navigateToAlertDetails = MutableLiveData<Alert?>()
    val navigateToAlertDetails: LiveData<Alert?>
        get() = _navigateToAlertDetails

    val options = FirebaseRecyclerOptions.Builder<Alert>()
        .setQuery(AlertsFirebase.databaseReference.orderByChild("sorting"), Alert::class.java)
        .setLifecycleOwner(lifecycleOwner)
        .build()

    fun startNavigateToAlertDetails(alert: Alert) {
        _navigateToAlertDetails.value = alert
        doneNavigatingToAlertDetails()
    }

    fun doneNavigatingToAlertDetails() {
        _navigateToAlertDetails.value = null
    }

    fun startNavigateToAddAlert() {
        _navigateToAddAlert.value = true
        doneNavigating()
    }

    private fun doneNavigating() {
        _navigateToAddAlert.value = null
    }


}


