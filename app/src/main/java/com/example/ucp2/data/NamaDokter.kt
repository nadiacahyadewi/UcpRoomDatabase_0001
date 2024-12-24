package com.example.ucp2.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.viewmodel.HomeDokViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel

object NamaDokter {
    @Composable
    fun options(
        HomeDokterViewModel: HomeDokViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val dataNama by HomeDokterViewModel.homeUiState.collectAsState()
        val namaDokter = dataNama.listDok.map { it.nama }
        return namaDokter
    }
}