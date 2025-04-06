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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.pps_primerentrega.presentantion.ui.theme.PPSPrimerEntregaTheme
import com.example.pps_primerentrega.presentantion.viewmodels.SessionViewModel
import com.example.pps_primerentrega.utils.SessionViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class MainActivity : ComponentActivity() {

    private lateinit var viewModel: SessionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = SessionViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[SessionViewModel::class.java]
        viewModel.user.observe(this){
            Log.wtf("MainActivity", "User: $it")
            if(it == null){
                this.startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
            }
        }
        enableEdgeToEdge()
        setContent {
            PPSPrimerEntregaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = viewModel.user.value?.email ?: "Usuario",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.logOut()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, name: String, callback: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize().padding(21.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido $name",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )

        Button(onClick = {
            callback()
        }) {
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PPSPrimerEntregaTheme {
        Greeting(name="Android"){
            Log.wtf("Hola","Hola")
        }
    }
}