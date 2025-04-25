package com.ghn.composelistkit.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ghn.composelistkit.ui.RefreshableDetailScreen
import com.ghn.composelistkit.ui.SampleListScreen
import com.ghn.composelistkit.ui.SimpleDetailScreen

/**
 * @author 浩楠
 *
 * @date 2025/4/25-22:17
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "ui/list") {
        composable("ui/list") {
            SampleListScreen(navController)
        }

        composable("ui/SimpleDetailScreen") {
            SimpleDetailScreen()
        }

        composable("ui/RefreshableDetailScreen") {
            RefreshableDetailScreen()
        }
    }
}

