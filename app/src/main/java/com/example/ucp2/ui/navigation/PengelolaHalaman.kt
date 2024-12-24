package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dokter.DestinasiInsert
import com.example.ucp2.ui.view.dokter.HomeDokView
import com.example.ucp2.ui.view.dokter.InsertDokView
import com.example.ucp2.ui.view.jadwal.DetailJadView
import com.example.ucp2.ui.view.jadwal.HomeJadView
import com.example.ucp2.ui.view.jadwal.InsertJadwalView
import com.example.ucp2.ui.view.jadwal.UpdateJadView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ){
        composable(
            route = DestinasiHome.route
        ) {
            HomeDokView(
                onDetailClick = {},
                onAddDokter = {
                    navController.navigate(DestinasiInsert.route)
                },
                onJadwal = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsert.route
        ){
            InsertDokView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            route = DestinasiHomeJadwal.route
        ) {
            HomeJadView(
                onDetailClick = {idJadwal ->
                    navController.navigate("${DestinasiDetail.route}/$idJadwal")
                    println("PengelolaHalaman: idJadwal = $idJadwal")
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertJadwal.route
        ){
            InsertJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
        composable (
            DestinasiDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.ID) {
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiDetail.ID)

            id?.let { id ->
                DetailJadView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdate.routeWithArg,
            arguments = listOf(
                navArgument (DestinasiUpdate.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            UpdateJadView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }

}