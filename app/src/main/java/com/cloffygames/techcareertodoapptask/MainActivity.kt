package com.cloffygames.techcareertodoapptask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.cloffygames.techcareertodoapptask.ui.theme.TechCareerToDoAppTaskTheme
import com.cloffygames.techcareertodoapptask.uix.view.Transitions
import com.cloffygames.techcareertodoapptask.uix.viewmodel.AddTaskViewModel
import com.cloffygames.techcareertodoapptask.uix.viewmodel.DetailViewModel
import com.cloffygames.techcareertodoapptask.uix.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homeViewModel : HomeViewModel by viewModels()
    val addTaskViewModel : AddTaskViewModel by viewModels()
    val detailViewModel : DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechCareerToDoAppTaskTheme {
                Transitions(homeViewModel, addTaskViewModel, detailViewModel)
            }
        }
    }
}