package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.NamaDokter

import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.jadwal.JadwalEvent
import com.example.ucp2.ui.viewmodel.jadwal.JadwalViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.jadwal.FormErrorState
import com.example.ucp2.ui.viewmodel.jadwal.JadUIState
import com.example.ucp2.ui.widget.DynamicSelectedTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InsertJadwalView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMassage) {
        uiState.snackBarMassage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Jadwal"
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(0.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            InsertBodyJadwal(
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.saveData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() //Navigasi di main thread
                            }
                        }
                    }
                },
                uiState = uiState
            )
        }
    }
}

@Composable
fun InsertBodyJadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JadUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0D61C4),
                contentColor = Color.White
            )
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaPasien = it))
            },
            label = { Text("Nama Pasien") },
            isError = errorState.namaPasien != null,
            placeholder = { Text("Masukkan nama pasien") },
        )
        Text(
            text = errorState.namaPasien ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.noHp,
            onValueChange = {
                onValueChange(jadwalEvent.copy(noHp = it))
            },
            label = { Text("No HP") },
            isError = errorState.noHp != null,
            placeholder = { Text("Masukkan nomor HP") },
        )
        Text(
            text = errorState.noHp ?: "",
            color = Color.Red
        )

        DynamicSelectedTextField(
            selectedValue = jadwalEvent.namaDokter,
            options = NamaDokter.options(),
            label = "Nama Dokter",
            onValueChangedEvent = { selectedDokter ->
                onValueChange(jadwalEvent.copy(namaDokter = selectedDokter))
            }
        )
        Text(
            text = errorState.namaDokter ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.tanggalKonsultasi,
            onValueChange = {
                onValueChange(jadwalEvent.copy(tanggalKonsultasi = it))
            },
            label = { Text("Tanggal Konsultasi") },
            isError = errorState.tanggalKonsultasi != null,
            placeholder = { Text("Masukkan tanggal konsultasi") },
        )
        Text(
            text = errorState.tanggalKonsultasi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.status,
            onValueChange = {
                onValueChange(jadwalEvent.copy(status = it))
            },
            label = { Text("Status") },
            isError = errorState.status != null,
            placeholder = { Text("Masukkan status jadwal") },
        )
        Text(
            text = errorState.status ?: "",
            color = Color.Red
        )
    }
}