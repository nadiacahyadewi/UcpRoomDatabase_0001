package com.example.ucp2.repository

import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDok(
    private val dokterDao: DokterDao
): RepositoryDok {

    override suspend fun insertDok(dokter: Dokter){
        dokterDao.insertDokter(dokter)
    }

    override fun getAllDok(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }
}