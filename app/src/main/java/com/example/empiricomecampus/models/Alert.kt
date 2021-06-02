package com.example.empiricomecampus.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alert(
    val id: String?,
    val type: String?,
    val text: String?
) : Parcelable {
    constructor() : this("", "", "")
}
