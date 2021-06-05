package com.example.empiricomecampus.firebase


import com.example.empiricomecampus.models.Alert
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object AlertsFirebase {

    val databaseReference: DatabaseReference = Firebase.database.reference.child("Alerts")

    fun uploadData(key: String, entry: Alert) {
        databaseReference.child(key).setValue(entry)

    }

}
