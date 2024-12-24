package com.example.ucp2.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDok
import com.example.ucp2.repository.RepositoryJad
import com.example.ucp2.ui.navigation.DestinasiUpdate
import com.example.ucp2.ui.viewmodel.toDetailUiEvent
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJad: RepositoryJad,
) : ViewModel() {

    var updateUIState by mutableStateOf(JadUIState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID])


    init {
        viewModelScope.launch {
                updateUIState = repositoryJad.getJad(_id)
                    .filterNotNull()
                    .first()
                    .toUiStateJad()
        }
    }


    fun updateState(jadwalEvent: JadwalEvent) {
        updateUIState = updateUIState.copy(
            jadwalEvent = jadwalEvent)
    }

    fun validateField(): Boolean {
        val event = updateUIState.jadwalEvent
        val errorState = FormErrorState(
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "No Telepon tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.jadwalEvent

        if (validateField()) {
            viewModelScope.launch {
                try {
                    repositoryJad.updateJad(currentEvent.toJadwalEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMassage = "Data berhasil diupdate",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMassage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMassage = "Validasi gagal, harap perbaiki data"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMassage = null)
    }
}

fun Jadwal.toUiStateJad(): JadUIState = JadUIState(
    jadwalEvent = this.toDetailUiEvent()
)
