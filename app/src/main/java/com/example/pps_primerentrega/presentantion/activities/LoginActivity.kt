package com.example.pps_primerentrega.presentantion.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.pps_primerentrega.presentantion.ui.theme.PPSPrimerEntregaTheme
import com.example.pps_primerentrega.presentantion.viewmodels.SessionViewModel
import com.example.pps_primerentrega.utils.SessionViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    private lateinit var viewModel: SessionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = SessionViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[SessionViewModel::class.java]
        viewModel.user.observe(this){
            Log.wtf("LoginActivity", "User: $it")
            if(!it.isNullOrEmpty()){
                this.startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }
        }
        enableEdgeToEdge()
        setContent {
            PPSPrimerEntregaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginView (
                        modifier = Modifier.padding(innerPadding),
                        callback = { email:String, password:String ->
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.login(email, password)
                            }
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun LoginView(modifier: Modifier = Modifier, callback: (String, String) -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
     Column(
         modifier = modifier.fillMaxSize(),
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center
     ) {
         Card(modifier = modifier) {
             Column(
                 modifier = modifier.padding(horizontal = 21.dp),
                 horizontalAlignment = Alignment.CenterHorizontally,
                 verticalArrangement = Arrangement.spacedBy(16.dp)
             ) {
                 Text(
                     text = "BIENVENIDO",
                     modifier = modifier.padding(bottom = 16.dp),
                     style = MaterialTheme.typography.titleLarge
                 )
                 OutlinedTextField(
                     value = email,
                     onValueChange = { email = it },
                     label = { Text("Email") },
                 )
                 OutlinedTextField(
                     value = password,
                     onValueChange = { password = it },
                     label = { Text("Password") }
                 )
                 Button(onClick = {
                     callback(email, password)
                 }) {
                     Text("Login")
                 }
             }
         }
     }
}