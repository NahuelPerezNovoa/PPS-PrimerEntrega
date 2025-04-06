package com.example.pps_primerentrega.utils

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pps_primerentrega.presentantion.viewmodels.SessionViewModel

class SessionViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            return SessionViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}