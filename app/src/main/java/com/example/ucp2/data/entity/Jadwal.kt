package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "jadwal",
    foreignKeys = [
        ForeignKey(
            entity = Dokter::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Jadwal(
    @PrimaryKey
    val id: String,
    val namaDokter: String,
    val namaPasien: String,
    val noHp: String,
    val tanggalKonsultasi: String,
    val Status: String
)
