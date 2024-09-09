package com.cloffygames.techcareertodoapptask.uix.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.uix.viewmodel.AddTaskViewModel
import com.cloffygames.techcareertodoapptask.uix.viewmodel.DetailViewModel
import com.cloffygames.techcareertodoapptask.uix.viewmodel.HomeViewModel
import com.google.gson.Gson

/**
 * Transitions fonksiyonu, uygulamanın ekranlar arası geçişlerini ve navigasyon işlemlerini yönetir.
 * Bu fonksiyon, NavHost kullanarak ekranlar arasında gezinmeyi sağlar.
 *
 * @param homeViewModel Ana ekranın ViewModel'ı.
 * @param addTaskViewModel Görev ekleme ekranının ViewModel'ı.
 * @param detailViewModel Görev detay ekranının ViewModel'ı.
 */
@Composable
fun Transitions(
    homeViewModel: HomeViewModel,
    addTaskViewModel: AddTaskViewModel,
    detailViewModel: DetailViewModel
) {
    // NavController, ekranlar arasında gezinmeyi kontrol eder.
    val navController = rememberNavController()

    // NavHost, uygulamanın ekranlarını yönetir ve hangi ekranın görüntüleneceğini belirler.
    NavHost(navController = navController, startDestination = "homeScreen") {
        // Ana ekran için composable tanımı.
        composable("homeScreen") {
            HomeScreen(navController, homeViewModel)
        }
        // Görev ekleme ekranı için composable tanımı.
        composable("addTaskScreen") {
            AddTaskScreen(navController, addTaskViewModel)
        }
        // Görev detay ekranı için composable tanımı.
        composable(
            "detailScreen/{task}",
            arguments = listOf(
                navArgument("task") {
                    type = NavType.StringType // "task" argümanı String olarak tanımlanır.
                }
            )
        ) {
            // Görevin JSON formatındaki String argümanı alınır ve Task nesnesine dönüştürülür.
            val json = it.arguments?.getString("task")
            val taskObject = Gson().fromJson(json, Task::class.java)

            // Detay ekranı, taskObject ile birlikte çağrılır.
            DetailScreen(navController, detailViewModel, taskObject)
        }
    }
}
