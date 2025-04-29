package com.ghn.composelistkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ghn.composelistkit.ui.bar.TopBar
import com.ghn.composelistkit.ui.nav.AppNavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        val currentBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = currentBackStackEntry?.destination?.route

                        if (currentDestination != "ui/list" && currentDestination != null) {
                            TopBar(title = "返回", onBackClick = {
                                navController.popBackStack()
                            })
                        }
                    },
                    content = { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(WindowInsets.systemBars.asPaddingValues())
                        ) {
                            AppNavGraph(navController)
                        }
                    }
                )

            }
        }
    }
}


