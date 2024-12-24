package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.JdApp
import com.example.ucp2.ui.viewmodel.jadwal.HomeJadViewModel
import com.example.ucp2.ui.viewmodel.jadwal.JadwalViewModel
import com.example.ucp2.ui.viewmodel.jadwal.UpdateJadViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                JdApp().containerApp.repositoryDok
            )
        }
        initializer {
            HomeDokViewModel(
                JdApp().containerApp.repositoryDok
            )
        }
        initializer {
            JadwalViewModel(
                JdApp().containerApp.repositoryJad
            )
        }

        initializer {
            HomeJadViewModel(
                JdApp().containerApp.repositoryJad
            )
        }

        initializer {
            DetailJadViewModel(
                createSavedStateHandle(),
                JdApp().containerApp.repositoryJad
            )
        }

        initializer {
            UpdateJadViewModel(
                createSavedStateHandle(),
                JdApp().containerApp.repositoryJad
            )
        }


    }
}

fun CreationExtras.JdApp(): JdApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JdApp)