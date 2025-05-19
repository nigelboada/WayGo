package com.example.waygo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.waygo.ui.theme.WayGoTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val name = intent.getStringExtra("KEY_NAME") ?: "No Name Provided"
        setContent {
            WayGoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Welcome to Second Activity")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Received: $name") // Display received value
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            Toast.makeText(this@SecondActivity.applicationContext, "Button clicked!", Toast.LENGTH_SHORT).show()
                        }
                        ) {
                            Text("A Toast")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { finish() }) {
                            Text("Go Back")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WayGoTheme {
        Greeting("Android")
    }
}