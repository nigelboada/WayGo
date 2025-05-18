package com.example.waygo.ui.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.waygo.ui.components.ReservationRow
import com.example.waygo.ui.viewmodel.ReservationsAllViewModel
import com.example.waygo.domain.model.Reservation
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun AllReservationsScreen(groups: Map<String, List<Reservation>>, vm: ReservationsAllViewModel = hiltViewModel()) {

    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()   // respeta notch y barra de navegación
            .padding(16.dp)
    ) {
        /* ---------- título global ---------- */
        item {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {


                Text(
                    text = "All Reservations",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.statusBarsPadding().padding(start = 16.dp, bottom = 12.dp)
                )

                /*  refresh icon  */
                IconButton(onClick = { vm.load() }) {       // ← recarga la lista
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload"
                    )
                }

            }
        }

        /* ---------- grupos ---------- */
        groups.forEach { (gid, list) ->
            item {
                Text(
                    text = "Group $gid",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(list) { res ->
                Log.d("viewmodel", res.toString())
                ReservationRow(
                    res = res,
                    onCancel = {

                        Toast.makeText(context, "Esta pantalla es exclusiva del profesor", Toast.LENGTH_SHORT).show()
                        //vm.cancel(res)
                    }
                )
            }

        }
    }
}