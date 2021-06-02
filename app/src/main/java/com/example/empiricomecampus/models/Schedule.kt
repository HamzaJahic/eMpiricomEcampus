package com.example.empiricomecampus.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val id: String?,
    val day: String?,
    val semester: String?,
    val course: String?,
    val subject: String?,
    val startTime: String?,
    val endTime: String?,
    val type: String?,
    val attendance: String?,
    val sorting: String?
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "", "", "", "")

}


