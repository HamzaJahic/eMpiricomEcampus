package com.example.empiricomecampus.utils

import androidx.lifecycle.MutableLiveData

object Globals {
    val USER_ID = MutableLiveData<String>()

    val USER_NAME = MutableLiveData<String>()

    val USER_LASTNAME = MutableLiveData<String>()

    val USER_SEMESTER = MutableLiveData<String>()

    val USER_COURSE = MutableLiveData<String>()

    var ADMIN = MutableLiveData<Boolean?>()
}
