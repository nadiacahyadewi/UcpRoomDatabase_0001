package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJad
import kotlinx.coroutines.launch

data class JadwalEvent(
    val id: String = "",
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tanggalKonsultasi: String = "",
    val status: String = ""
)

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    id = id,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status
)

data class FormErrorState2(
    val id: String? = null,
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null
){
    fun isValid(): Boolean {
        return id == null
                && namaDokter == null
                && namaPasien == null
                && noHp == null
                && tanggalKonsultasi == null
                && status == null
    }
}

data class JdwlUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorState2 = FormErrorState2(),
    val snackBarMassage: String? = null,
)

class JadwalViewModel(
    private val repositoryJdwl: RepositoryJad
) : ViewModel(){
    var uiState by mutableStateOf(JdwlUIState())

    fun updateState(jadwalEvent: JadwalEvent) {
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    fun validateFields(): Boolean{
        val event = uiState.jadwalEvent
        val errorState = FormErrorState2(
            id = if (event.id.isNotEmpty()) null else "ID tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter harus dipilih",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor Telfon tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //simpan data ke repo
    fun saveData() {
        val currentEvent = uiState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJdwl.insertJad(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMassage = "Data berhasil disimpan",
                        jadwalEvent = JadwalEvent(), // reset input
                        isEntryValid = FormErrorState2()
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

    //reset pesean snackbar
    fun resetSnackbarMessage(){
        uiState = uiState.copy(snackBarMassage = null)
    }
}