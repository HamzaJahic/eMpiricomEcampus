package com.example.empiricomecampus.notifications

data class PushNotification(
    val to: String?
) {
    constructor() : this("")
}
