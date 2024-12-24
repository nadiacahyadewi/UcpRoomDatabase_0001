package com.example.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

object DestinasiHomeJadwal : AlamatNavigasi {
    override val route = "home_jad"
}

object DestinasiDetail : AlamatNavigasi {
    override val route = "detail"
    const val ID = "id"
    val routeWithArg = "$route/{$ID}"
}

object DestinasiUpdate : AlamatNavigasi {
    override val route = "update"
    const val ID = "id"
    val routeWithArg = "$route/{$ID}"
}

object DestinasiInsertJadwal : AlamatNavigasi{
    override val route: String = "insert_jad"
}