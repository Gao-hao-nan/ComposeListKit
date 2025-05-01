package com.ghn.composelistkit.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

/**
 * @author 浩楠
 *
 * @date 2025/4/30-22:07
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun AutoNavHost(
    navController: NavHostController,
    routeMap: Map<String, @Composable () -> Unit>,
    startDestination: String = "ui/list",
    listScreen: @Composable (NavController, List<String>) -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(startDestination) {
            listScreen(navController, routeMap.keys.toList())
        }

        routeMap.forEach { (title, screen) ->
            composable(title) {
                screen()
            }
        }
    }
}
