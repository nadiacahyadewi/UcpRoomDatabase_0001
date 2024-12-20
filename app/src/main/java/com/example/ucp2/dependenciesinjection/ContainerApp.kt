package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.DatabaseJd
import com.example.ucp2.repository.LocalRepositoryDok
import com.example.ucp2.repository.LocalRepositoryJad
import com.example.ucp2.repository.RepositoryDok
import com.example.ucp2.repository.RepositoryJad

interface InterfaceContainerApp{
    val repositoryDok: RepositoryDok
    val repositoryJad: RepositoryJad
}
class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDok: RepositoryDok by lazy {
        LocalRepositoryDok(DatabaseJd.getDatabase(context).DokterDao())
    }

    override val repositoryJad: RepositoryJad by lazy {
        LocalRepositoryJad(DatabaseJd.getDatabase(context).JadwalDao())
    }
}