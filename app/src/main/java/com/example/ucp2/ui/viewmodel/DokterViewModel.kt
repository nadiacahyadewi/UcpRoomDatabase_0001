package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDok
import kotlinx.coroutines.launch

data class DokterEvent(
    val id: String = "",
    val nama: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val noHp: String = "",
    val jamKerja: String = ""
)

fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    id = id,
    nama = nama,
    spesialis = spesialis,
    klinik = klinik,
    noHp = noHp,
    jamKerja = jamKerja
)

data class FormErrorState(
    val id: String? = null,
    val nama: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val noHp: String? = null,
    val jamKerja: String? = null,
){
    fun isValid(): Boolean {
        return id == null
                && nama == null
                && spesialis == null
                && klinik == null
                && noHp == null
                && jamKerja == null
    }
}

data class DokUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMassage: String? = null,
)

class DokterViewModel(
    private val repositoryDok: RepositoryDok
) : ViewModel(){

    var uiState by mutableStateOf(DokUIState())

    //Memperbarui state berdasarkan input
    fun updateState(dokterEvent: DokterEvent) {
        uiState = uiState.copy(
            dokterEvent = dokterEvent,
        )
    }

    //validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiState.dokterEvent
        val errorState = FormErrorState(
            id = if (event.id.isNotEmpty()) null else "ID tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            spesialis = if (event.spesialis.isNotEmpty()) null else "Spesialis tidak boleh kosong",
            klinik = if (event.klinik.isNotEmpty()) null else "Klinik tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor Telfon tidak boleh kosong",
            jamKerja = if (event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //simpan data ke repo
    fun saveData() {
        val currentEvent = uiState.dokterEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDok.insertDok(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMassage = "Data Berhasil disimpan",
                        dokterEvent = DokterEvent(), //reset input
                        isEntryValid = FormErrorState(), //reset eror state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMassage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMassage = "Input tidak valid. Periksa kembali data anda."
            )
        }
    }

    //reset snackbar setelah di tampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMassage = null)
    }
}