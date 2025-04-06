package com.example.pps_primerentrega.presentantion.viewmodels
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionViewModel(val activity: Activity): ViewModel() {

    var user : MutableLiveData<FirebaseUser?>
    private set

    var error = MutableLiveData("")

    private val auth = Firebase.auth

    init {
        user = MutableLiveData(auth.currentUser)
        Log.wtf("SessionViewModel AUTH", "User: ${auth.currentUser}")
    }

    fun signUp(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.wtf("SessionViewModel", "createUserWithEmail:success")
                    user.value = auth.currentUser
                } else {
                    Log.wtf("SessionViewModel", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
    }

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.wtf("SessionViewModel", "signInWithEmail:success")
                    user.value = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.wtf("SessionViewModel", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
    }

    suspend fun logOut(){
        auth.signOut()
        withContext(Dispatchers.Main){
            user.value = null
        }
    }
}