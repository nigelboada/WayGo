package com.example.waygo.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.waygo.SecondActivity
import androidx.core.net.toUri

@Composable
fun IntentButtonsLayout() {
    val context = LocalContext.current // Get the context
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = {
/* Acción del botón 1 */
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("KEY_NAME", "AppMobile1")
            context.startActivity(intent)
        }) {
            Text("Open Second Activity")
        }
        Button(
            onClick = {
                Intent(Intent.ACTION_MAIN).also {
                    it.`package`="com.google.android.youtube"
                    try {
                        context.startActivity(it)
                    } catch (e: ActivityNotFoundException){
                        e.printStackTrace()
                    }
                }
            }
        ) {
            Text(text = "YouTube")
        }
        Button(
            onClick = {
                val intent=Intent(Intent.ACTION_SEND).apply {
                    type="text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayListOf("youremailid@gmail.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "This is the subject of the mail")
                    putExtra(Intent.EXTRA_TEXT, "This is the text part of the mail")
                }
                context.startActivity(intent)
            }
        ) {
            Text(text = "Email")
        }
        Button(
            onClick = {
                val uri = "https://www.campusigualada.udl.cat".toUri()
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }

        ) {
            Text(text = "Open Browser")
        }
    }
}
