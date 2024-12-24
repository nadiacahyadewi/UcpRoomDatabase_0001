package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
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
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.ui.viewmodel.jadwal.HomeJadViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeJadView(
    viewModel: HomeJadViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddJadwal: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onBack: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "HEALTHY GO - Yuk Sehat Bersama",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Image(
                            painter = painterResource(id = R.drawable.dokter),
                            contentDescription = "Doctor Hero Image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
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
                            Color(0xFFE3F2FD),
                            Color(0xFFBBDEFB)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Doctor Hero Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            var searchText by remember { mutableStateOf("") }
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Cari Jadwal...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onAddJadwal,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Tambah Jadwal", color = Color.White)
                }
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Kembali", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                homeUiState.listJad.isEmpty() -> {
                    Text(
                        text = "Tidak ada data Jadwal.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    ListJadwal(
                        listJad = homeUiState.listJad,
                        modifier = Modifier.fillMaxSize(),
                        onDetailClick = onDetailClick
                    )
                }
            }
        }
    }
}

@Composable
fun ListJadwal(
    listJad: List<Jadwal>,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        listJad.forEach { jad ->
            Card(
                onClick = { onDetailClick(jad.id.toString()) },
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
                            text = jad.namaPasien,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )
                        Text(
                            text = jad.status,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )

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
                                text = jad.tanggalKonsultasi,
                                fontSize = 14.sp
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Call,
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = jad.noHp,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
