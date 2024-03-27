package com.example.project_android.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.convertDateFormat(): String? =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)?.let { date ->
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }