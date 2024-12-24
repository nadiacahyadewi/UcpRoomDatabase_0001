package com.example.ucp2.ui.viewmodel.jadwal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.LocalRepositoryJad
import com.example.ucp2.repository.RepositoryJad
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//data class variabel yang mentimpan
// data input form
data class JadwalEvent(
    val id: Int = 0,
    val namaDokter: String = "",
    val namaPasien: String = "" ,
    val noHp: String = "",
    val tanggalKonsultasi: String = "",
    val status: String = ""
)

// menyimpan input form ke dalam entity
fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    id = id,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status
)

data class FormErrorState(
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null
){
    fun isValid(): Boolean {
        return namaDokter == null
                && namaPasien == null
                && noHp == null
                && tanggalKonsultasi == null
                && status == null
    }
}

data class JadUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val listNamaDokter: List<Dokter> = emptyList(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMassage: String? = null,
)

class JadwalViewModel(
    private val repositoryJad: RepositoryJad
) : ViewModel(){
    var uiState by mutableStateOf(JadUIState())


    //Memperbarui state berdasarkan input pengguna
    fun updateState(jadwalEvent: JadwalEvent) {
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent,
        )
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiState.jadwalEvent
        val errorState = FormErrorState(
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "No Handphone tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "tanggal Konsultasi tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return  errorState.isValid()
    }

    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJad.insertJad(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMassage = "Data berhasil disimpan",
                        jadwalEvent = JadwalEvent(), //reset input form
                        isEntryValid = FormErrorState() //reset error state
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

    //reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMassage = null)
    }
}