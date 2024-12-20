package com.example.ucp2.repository

import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJad {
    suspend fun insertJad(jadwal: Jadwal)

    fun getAllJad(): Flow<List<Jadwal>>

    fun getJad(id: String): Flow<Jadwal>

    suspend fun deleteJad(mahasiswa: Jadwal)

    suspend fun updateJad(mahasiswa: Jadwal)
}