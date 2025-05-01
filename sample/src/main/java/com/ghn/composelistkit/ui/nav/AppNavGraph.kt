package com.ghn.composelistkit.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

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
 * @Description: TODO 路由管理
 */
@Composable
fun AppNavGraph(navController: NavHostController) {
    AutoNavHost(
        navController = navController,
        routeMap = RouteRegistry.routeMap,
        startDestination = "ui/list",
        listScreen = { controller, titles ->
            AutoNavListScreen(controller, titles)
        }
    )
}

