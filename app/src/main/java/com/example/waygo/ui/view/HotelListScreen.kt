import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waygo.ui.viewmodel.HotelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelListScreen(viewModel: HotelViewModel) {
    val hotels = viewModel.hotels

    LaunchedEffect(Unit) {
        viewModel.fetchHotels()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Llistat d'Hotels") })
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            if (hotels.isEmpty()) {
                // Mostrar un missatge mentre es carrega o si no hi ha hotels
                Text(
                    text = "Carregant hotels o no n'hi ha cap disponible.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(hotels) { hotel ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = hotel.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Ubicació: ${hotel.location}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Preu: ${hotel.pricePerNight}€", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
