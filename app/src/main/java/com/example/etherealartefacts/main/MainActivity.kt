package com.example.etherealartefacts.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.etherealartefacts.ui.theme.EtherealArtefactsTheme
import androidx.navigation.compose.rememberNavController
import com.example.etherealartefacts.ui.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EtherealArtefactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold {
                        val navController = rememberNavController()
                        val navigateUp = navController::navigateUp
                        val navHostEngine = rememberNavHostEngine()
                        DestinationsNavHost(
                            startRoute = NavGraphs.login,
                            modifier = Modifier.padding(it),
                            engine = navHostEngine,
                            navController = navController,
                            dependenciesContainerBuilder = {
                                dependency(navigateUp)
                            },
                            navGraph = NavGraphs.root,
                        )
                    }
                }
            }
        }
    }
}
