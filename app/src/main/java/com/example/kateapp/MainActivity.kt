package com.example.kateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kateapp.ui.theme.KateAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // Чтобы работал hilt
class MainActivity : ComponentActivity() {

    // контролле с методами для навигации
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // Здесь мы начинаем писать всю разметку
            KateAppTheme {
                navController = rememberNavController() // Создаем контроллер
                SetupNavGraph(navController) // Создаем навигационный граф с экранами
            }
        }
    }
}
