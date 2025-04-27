package com.ghn.composelistkit.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ghn.composelistkit.ComposeListKit

/**
 * @author 浩楠
 *
 * @date 2025/4/25-22:37
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun SampleListScreen(navController: NavHostController) {
    val items = remember {
        listOf("普通列表", "刷新加载更多","状态页面","添加头和尾")
    }
    ComposeListKit(items = items) { item ->
        Text(
            text = item,
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    when (item) {
                        "普通列表" -> navController.navigate("ui/SimpleDetailScreen")
                        "刷新加载更多" -> navController.navigate("ui/RefreshableDetailScreen")
                        "状态页面" -> navController.navigate("ui/StateScreen")
                        "添加头和尾" ->navController.navigate("ui/SampleHeaderFooterScreen")
                    }
                }
                .padding(16.dp)
        )
    }
}
