package com.example.ucp2.data.database

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal

//mendefinisikan database dengan tabel Dokter dan Jadwal
@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class DatabaseJd : RoomDatabase() {

    //mendefinisikan fungsi untuk mengakses data Dokter dan Jadwal
    abstract fun DokterDao(): DokterDao
    abstract fun JadwalDao(): JadwalDao

    companion object{
        @Volatile
        private var Instance: DatabaseJd? = null

        fun getDatabase(context: Context): DatabaseJd{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseJd::class.java, //class database
                    "DatabaseJd" //Nama Database
                )
                        .build().also { Instance = it }
            })
        }
    }
}