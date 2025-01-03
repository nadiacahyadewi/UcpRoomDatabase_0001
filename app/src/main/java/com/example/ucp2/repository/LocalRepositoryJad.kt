package com.example.ucp2.repository

import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJad(
    private val jadwalDao: JadwalDao
): RepositoryJad {

    override suspend fun insertJad(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }

    override fun getAllJad(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }

    // Di LocalRepositoryJad
    override fun getJad(id: Int): Flow<Jadwal> {
        return jadwalDao.getJadwal(id)
    }

    override suspend fun deleteJad(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }

    override suspend fun updateJad(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }

}