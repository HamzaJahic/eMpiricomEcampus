package com.example.empiricomecampus.viewmodels

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empiricomecampus.firebase.AlertsFirebase
import com.example.empiricomecampus.models.Alert
import com.example.empiricomecampus.notifications.PushNotification
import com.example.empiricomecampus.notifications.RetrofitInstance
import com.example.empiricomecampus.utils.Constants.TOPIC
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val TAG = "Notification"

class AddAlertViewModel : ViewModel() {


    val text = MutableLiveData<String>()

    private val _type = MutableLiveData<String>()
    val type: LiveData<String>
        get() = _type


    //Varijabla za navigaciju nazad na glavni fragment
    private val _navigateToAlerts = MutableLiveData<Boolean?>()
    val navigateToAlerts: LiveData<Boolean?>
        get() = _navigateToAlerts


    //Listener za tip
    val typeListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _type.value = parent?.getItemAtPosition(position) as String
        }
    }

    //upload podataka
    fun uploadData() {

        val databaseRefrence = AlertsFirebase.databaseReference
        val entryID = databaseRefrence.push().key.toString()

        val entry = Alert(
            entryID,
            _type.value,
            text.value,
        )

        viewModelScope.launch {
            AlertsFirebase.uploadData(entryID, entry)
            PushNotification(TOPIC).also { sendNotification(it) }
        }


    }

    //funkcije za navigaciju
    fun navigateToAlerts() {
        _navigateToAlerts.value = true
        doneNavigating()
    }

    fun doneNavigating() {
        _navigateToAlerts.value = false
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api?.postNotification(notification)
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "Response: ${Gson().toJson(response)}")
                    } else {
                        Log.e(TAG, response.errorBody().toString())
                    }
                }

            } catch (e: Exception) {

            }
        }
}