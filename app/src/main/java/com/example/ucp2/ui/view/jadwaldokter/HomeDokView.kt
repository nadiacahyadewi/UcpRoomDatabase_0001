package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.data.Spesialis
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.ui.viewmodel.HomeDokViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDokView(
    viewModel: HomeDokViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDokter: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onJadwal: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp), // Padding di kanan untuk memberikan ruang ekstra
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Teks di sebelah kiri
                        Text(
                            text = "HEALTHY GO - Yuk Sehat Bersama",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        // Gambar di sebelah kanan dengan padding
                        Image(
                            painter = painterResource(id = R.drawable.dokter),
                            contentDescription = "Doctor Hero Image",
                            modifier = Modifier
                                .size(50.dp) // Ukuran gambar
                                .clip(CircleShape) // Membuat gambar bulat
                                .border(1.dp, Color.White, CircleShape)


                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF4CAF50))
            )
        },

    ) { innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE3F2FD), // Light Blue
                            Color(0xFFBBDEFB)  // Sky Blue
                        )
                    )
                )
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Image
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Doctor Hero Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            var searchText by remember { mutableStateOf("") }
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Cari dokter...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onAddDokter,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Tambah Dokter", color = Color.White)
                }
                Button(
                    onClick = onJadwal,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Lihat Jadwal", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Content Area
            when {
                homeUiState.isLoading -> {
                    CircularProgressIndicator()
                }
                homeUiState.isError -> {
                    Text(
                        text = homeUiState.errorMessage ?: "Terjadi kesalahan",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
                homeUiState.listDok.isEmpty() -> {
                    Text(
                        text = "Tidak ada data dokter.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    ListDokter(
                        listDok = homeUiState.listDok,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ListDokter(
    listDok: List<Dokter>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        listDok.forEach { dok ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column {
                        Text(
                            text = dok.nama,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )
                        Text(
                            text = dok.spesialis,
                            fontSize = 16.sp,
                            color = when (dok.spesialis) {
                                "Umum" -> Color.Blue
                                "Anak" -> Color.Magenta
                                "Bedah" -> Color.Gray
                                "THT" -> Color.Green
                                "Mata" -> Color.Yellow
                                "Kulit" -> Color.Cyan
                                "Psikologi" -> Color.Red
                                else -> Color.Black
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = dok.klinik,
                                fontSize = 14.sp,

                                )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = dok.jamKerja,
                                fontSize = 14.sp,

                                )
                        }

                    }

                }
            }
        }
    }
}
