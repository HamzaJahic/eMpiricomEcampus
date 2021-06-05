package com.example.empiricomecampus.firebase

import com.example.empiricomecampus.models.Schedule
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object ScheduleFirebase {

    val databaseReference: DatabaseReference = Firebase.database.reference.child("Schedule")

    fun uploadData(key: String, entry: Schedule) {
        databaseReference.child(entry.course!!).child(entry.semester!!).child(key)
            .setValue(entry)
        databaseReference.child("ADMIN").child(entry.semester).child(key).setValue(entry)

    }
}
