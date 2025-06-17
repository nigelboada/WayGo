package com.example.waygo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.waygo.BuildConfig

@Composable
fun RoomImageCarouselWithControls(
    images: List<String>,
    onDismiss: () -> Unit
) {
    val base = BuildConfig.HOTELS_API_URL.trimEnd('/')
    var currentIndex by remember { mutableIntStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        title = { Text("Room Images") },
        text = {
            Column {
                if (images.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(base + images[currentIndex]),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (currentIndex > 0) {
                            TextButton(onClick = { currentIndex-- }) {
                                Text("Previous")
                            }
                        }
                        if (currentIndex < images.size - 1) {
                            TextButton(onClick = { currentIndex++ }) {
                                Text("Next")
                            }
                        }
                    }
                } else {
                    Text("No images available.")
                }
            }
        }
    )
}
