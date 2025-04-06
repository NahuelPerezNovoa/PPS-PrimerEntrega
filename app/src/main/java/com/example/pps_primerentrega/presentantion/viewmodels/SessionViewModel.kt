package com.example.pps_primerentrega.presentantion.viewmodels
import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionViewModel(val activity: Activity): ViewModel() {

    private val USER_PREFERENCES_FILE_NAME = "user_preferences"
    private val USER_PREFERENCES_EMAIL_KEY = "user_email"
    private val sharedPref = activity.getSharedPreferences(USER_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    var user : MutableLiveData<String?>
    private set

    init {
        val email = sharedPref.getString(USER_PREFERENCES_EMAIL_KEY, "")
        user = MutableLiveData(email)
    }


    suspend fun login(email: String, password: String){
        withContext(Dispatchers.Main){
            setUser(email)
        }
    }

    suspend fun logOut(){
        setUser(null)
    }

    private suspend fun getUser(){
        val email = sharedPref.getString(USER_PREFERENCES_EMAIL_KEY, "")
        withContext(Dispatchers.Main){
            user.value = email
        }
    }

    private suspend fun setUser(email: String?){
        with (sharedPref.edit()) {
            putString(USER_PREFERENCES_EMAIL_KEY, email)
            apply()
        }
        withContext(Dispatchers.Main){
            user.value = email
        }
    }
}