package com.example.project_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {
    protected val mSnackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = mSnackBarText
}