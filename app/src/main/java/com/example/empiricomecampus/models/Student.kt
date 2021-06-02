package com.example.empiricomecampus.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Student(
    var id: String?,
    val username: String?,
    val password: String?,
    var name: String?,
    var lastName: String?,
    var img: String?,
    var course: String?,
    var semester: String?
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "", "")
}

